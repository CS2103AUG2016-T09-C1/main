package seedu.inbx0.logic.commands;

import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.model.TaskList;
import seedu.inbx0.model.task.ReadOnlyTask;

/**
 * Clears the tasklist or the shown list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clr";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": clears all of the tasks shown in current list\n"
            + COMMAND_WORD + " all: clears the entire tasklist in Inbx_0 \n"
            + "Example: " + COMMAND_WORD;
    
    public static final String MESSAGE_SUCCESS = "Tasklist has been cleared!";
    
    public final String toClear;
    
    public ClearCommand(String toClear) {
        this.toClear = toClear;
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        
        if(toClear.length() == 0) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();    
            model.removeAllTasksFromShownList(lastShownList);
            indicateCloseReminderListEvent();
        } else {
            model.resetData(TaskList.getEmptyTaskList());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
}
