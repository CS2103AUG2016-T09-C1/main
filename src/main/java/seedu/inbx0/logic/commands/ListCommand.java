package seedu.inbx0.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.Date;

//@@author A0139579J
/**
 * Lists all tasks in the tasks to the user.
 * or tasks that are overdue
 * or tasks associated with the date
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": \n 1. Lists all tasks associated with the date and "
            + "displays them as a list with index numbers.\n"
            + "Without any parameters, it will display all undone tasks.\n"
            + "Parameters: [DATE]\n"
            + "Example: " + COMMAND_WORD + " today\n"
            + "2. Lists all tasks that are due to a certain date \n"
            + "Parameters: due DATE\n"
            + "Example: " + COMMAND_WORD + " due next week\n"
            + "3. Lists all tasks that are overdue \n"
            + "Example: " + COMMAND_WORD + " overdue\n";
    
    private final String checkDate;
    private final String preposition;
    private static final Pattern TASKS_DUE_UNTIL_DATE_LIST_FORMAT = Pattern.compile(" due (?<date>[^$]+)");
    
    /**
     * ListCommand constructor to display all incomplete tasks
     */
    public ListCommand() {
        this.checkDate = "";
        this.preposition = "";
    }

    public ListCommand(String arguments) throws IllegalValueException {
        final Matcher matcher = TASKS_DUE_UNTIL_DATE_LIST_FORMAT.matcher(arguments);
        if(matcher.matches()) {
            this.preposition = "due";
            Date checkDate = new Date(matcher.group("date"));
            this.checkDate = checkDate.value;
            
        }
        else if(arguments.trim().equals("overdue")) {
            this.checkDate = "";
            this.preposition = "overdue";
        }
        else {
            Date checkDate = new Date (arguments.trim());
            this.checkDate = checkDate.value;
            this.preposition = "";
        }
    }

    @Override
    public CommandResult execute() {
        if(checkDate.equals("") && preposition.equals("")) {
            model.updateFilteredTaskListByCompleteness(false);
            indicateCloseAllTitledpanesEvent();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredTaskList(checkDate, preposition);
            indicateCloseAllTitledpanesEvent();
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        }       
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
