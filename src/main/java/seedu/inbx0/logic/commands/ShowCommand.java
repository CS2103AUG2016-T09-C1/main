package seedu.inbx0.logic.commands;


import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.events.ui.ShowFilteredListRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
//@@author A0148044J
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show a filtered list by the specific filter condition\n"
            + "Show tasks start on a specific day within one week: "
    		+ "today, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday\n"
    		+ "Show tasks belongs to a specific category: Event, Deadline, Floating\n"
    		+ "Show tasks with a specific importance: Red, Yellow, Green\n"
    		+ "Show tasks with its state: Complete, Incomplete, Expired, Unexpired\n"
    		+ "You may use the first three letters as shortcut\n"
    		+ "Example: " + COMMAND_WORD + " today";

    public static final String SHOWING_FILTERED_LIST_MESSAGE = "Show a filtered list";
    
    private String filterCondition;
    
    public ShowCommand (String filterCondition) {
        this.filterCondition = filterCondition;
    }
    
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowFilteredListRequestEvent(filterCondition));
        return new CommandResult(SHOWING_FILTERED_LIST_MESSAGE + " with keyword " + filterCondition);
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
