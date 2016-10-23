package seedu.inbx0.logic;

import java.util.Stack;

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
    
    /**
     * Executes internal command and returns the result..
     * @return the result of the command execution.
     */
    CommandResult removeReminders();

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered list of overdue tasks */
    ObservableList<ReadOnlyTask> getFilteredOverdueTaskList();
        
    /** Returns the filtered list of overdue tasks */
    ObservableList<ReadOnlyTask> getFilteredBeforedueTaskList();
    
    /** Returns the filtered list of float tasks */
    ObservableList<ReadOnlyTask> getFilteredFloatTaskList();
    
    /** Returns the filtered list of event or deadline */
    ObservableList<ReadOnlyTask> getFilteredNormalTaskList();
    
    /** Returns the filtered list of event or deadline */
    ObservableList<ReadOnlyTask> getFilteredEventTaskList();
    
    /** Returns the filtered list of deadline */
    ObservableList<ReadOnlyTask> getFilteredDeadlineTaskList();
    
    /** Returns the filtered list of done tasks */
    ObservableList<ReadOnlyTask> getFilteredDoneTaskList();
    
    /** Returns the filtered list of uncompleted tasks */
    ObservableList<ReadOnlyTask> getFilteredToDoTaskList();
    
    /** Returns the filtered list of tasks according to a specific date */
    ObservableList<ReadOnlyTask> getFilteredDayTaskList(String day);
    
    /** Returns the filtered list of tasks according to importance */
    ObservableList<ReadOnlyTask> getFilteredImportanceTaskList(String importance);
    
    /** Returns the command text of the previous command */
    Stack<String> getPreviousCommandText();
    
    /** Returns the command text of the next command */
    Stack<String> getNextCommandText();
    
    /** Pop the command text of the previous command */
    String popPreviousCommandText();

    /** Pop the command text of the next command */
    String popNextCommandText();
    
    /** Push the command text to the previousCommand stack*/
    void setPreviousCommandText(String commandText);
    
    /** Push the command text to the nextCommand stack*/
    void setNextCommandText(String commandText);
}
