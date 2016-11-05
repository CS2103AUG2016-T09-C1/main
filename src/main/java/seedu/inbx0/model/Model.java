package seedu.inbx0.model;

import java.util.List;

import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();
    
    //@@author A0139481Y
    //** Saves the current state of the TaskList into a temporary history list **/
    void saveTaskListHistory();
    
    /** Undoes the task lists to a previous state. */
    int undoTaskListHistory(int stepsBack);
    
    /** Redoes the task lists. */
    int redoTaskListHistory(int numToRedo);
    
    //** Clears the redo list whenever a command which cannot be undone or redone is executed. */
    void clearRedoTaskListHistory();
    //@@author

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Marks the given task complete. */
    void markTaskComplete(ReadOnlyTask target, Task task) throws UniqueTaskList.TaskNotFoundException;
    
    /** Edits the given task. */
    void editTask(ReadOnlyTask target, Task task) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    //@@author A0148044J
    /** Returns the filtered task list according to category as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByCategory(String category);
    
    /** Returns the filtered task list according to expiry as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByExpiry(boolean isExpired);
    
    /** Returns the filtered task list according to completeness as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByCompleteness(boolean isCompleted);
    
    /** Returns the filtered task list according to day as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByDay(String day);
    
    /** Returns the filtered task list according to importance as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByImportance(String importance);
    //@@author
    
    /** Returns the filtered overdue task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredOverdueTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to filter overdue tasks */
    void updateFilteredOverdueTaskList();
    
    //@@author A0148044J
    /** Updates the filter of the filtered task list to filter tasks according to expiry*/
    void updateFilteredTaskListByExpiry(boolean isExpired);  
    
    /** Updates the filter of the filtered task list to filter tasks according to category */
    void updateFilteredTaskListByCategory(String category);
    
    /** Updates the filter of the filtered task list to filter tasks according to completeness*/
    void updateFilteredTaskListByCompleteness(boolean isComplete);
    
    /** Updates the filter of the filtered task list according to day */
    void updateFilteredTaskListByDay(String day);
    
    /** Updates the filter of the filtered task list according to importance */
    void updateFilteredTaskListByImportance(String importance);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(boolean logicRelation, List<String> keywords);
    
    /** Sorts the task list by the given type in the given order*/
    void sortTaskList(String type, boolean defaultOrder);
  //@@author
    
    /** Updates the filter of the filtered task list to filter by the given date and based on preposition*/
    void updateFilteredTaskList(String date, String preposition);
    
    /** Checks expiry of tasks */
    void checkExpiry(Date currentDate, String currentTime);
    
    /** Checks expiry of reminders */
    void checkReminders();
    
}
