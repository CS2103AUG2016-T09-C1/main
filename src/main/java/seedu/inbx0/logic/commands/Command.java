package seedu.inbx0.logic.commands;

import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.events.model.ShowNormalTaskListEvent;
import seedu.inbx0.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.inbx0.model.Model;

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

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    /**
     * Raises an event to indicate task list need to update
     */
    protected void indicateShowNormalTaskListEvent() {
        EventsCenter.getInstance().post(new ShowNormalTaskListEvent());
    }
}
