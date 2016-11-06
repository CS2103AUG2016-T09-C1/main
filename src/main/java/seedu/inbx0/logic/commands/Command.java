package seedu.inbx0.logic.commands;

import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.events.ui.CloseAllTitledPanesEvent;
import seedu.inbx0.commons.events.storage.StoragePathChangedEvent;
import seedu.inbx0.commons.events.ui.CloseReminderListEvent;
import seedu.inbx0.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.inbx0.commons.events.ui.JumpToListRequestEvent;
import seedu.inbx0.commons.events.ui.JumpToTaskRequestEvent;
import seedu.inbx0.model.Model;
import seedu.inbx0.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }
    
    /**
     * Constructs a feedback message to summarise an operation that displays the number of tasks completed.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks completed
     */
    public static String getMessageForTasksCompletedSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_COMPLETED_OVERVIEW, displaySize);
    }
    
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    
    
    public void setData(Model model) {
        this.model = model;
    }
    
    //@@author A0139481Y
    /**
     * 
     * @return true if this command can be undone.
     */
    public abstract boolean canUndo();
    //@@author

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    /**
     * Raises an event to programmably close all titlepanes
     */
    protected void indicateCloseAllTitledpanesEvent() {
        EventsCenter.getInstance().post(new CloseAllTitledPanesEvent());
    }

    /** Raises an event to indicate the storage has changed - reused from T09-C2*/
    //@@author A0135797M
    protected void indicateStoragePathChanged(String oldPath, String newPath) {
        EventsCenter.getInstance().post(new StoragePathChangedEvent(oldPath, newPath));
    }
    
    /**
     * Raises an event to indicate to jump to task at the specific index
     */
    protected void indicateJumpToListRequestEvent(int index) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
    }
    
    /**
     * Raises an event to indicate to jump to task 
     */
    protected void indicateJumpToTaskRequestEvent(ReadOnlyTask task) {
        EventsCenter.getInstance().post(new JumpToTaskRequestEvent(task));
    }
    
    /**
     * Raises an event to close the reminder list
     */
    protected void indicateCloseReminderListEvent() {
        EventsCenter.getInstance().post(new CloseReminderListEvent());
    }
}
