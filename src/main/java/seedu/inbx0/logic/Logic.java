package seedu.inbx0.logic;

import javafx.collections.ObservableList;
import seedu.inbx0.logic.commands.CommandResult;
import seedu.inbx0.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);
    
    /**
     * Executes internal command and returns the result..
     * @return the result of the command execution.
     */
    CommandResult executeCheckExpiry();

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered list of overdue tasks */
    ObservableList<ReadOnlyTask> getFilteredOverdueTaskList();
    
    /** Returns the filtered list of float tasks */
    ObservableList<ReadOnlyTask> getFilteredFloatTaskList();
    
    /** Returns the filtered list of event or deadline */
    ObservableList<ReadOnlyTask> getFilteredNormalTaskList();
    
    /** Returns the filtered list of done tasks */
    ObservableList<ReadOnlyTask> getFilteredDoneTaskList();
    
    /** Returns the filtered list of uncompleted tasks */
    ObservableList<ReadOnlyTask> getFilteredToDoTaskList();

}
