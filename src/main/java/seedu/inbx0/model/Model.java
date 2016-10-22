package seedu.inbx0.model;

import java.util.List;

import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
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

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatTaskList();
    
    /** Returns the filtered overdue task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredOverdueTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to filter overdue tasks */
    void updateFilteredOverdueTaskList();

    /** Updates the filter of the filtered task list to filter float tasks*/
    void updateFilteredFloatTaskList();
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    // void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given date and based on preposition*/
    void updateFilteredTaskList(String date, String preposition);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(boolean logicRelation, List<String> keywords);
    
    /** Sorts the task list by the given type in the given order*/
    void sortTaskList(String type, boolean defaultOrder);
    
    /** Checks expiry of tasks */
    void checkExpiry(Date currentDate, String currentTime);
    
    /** Checks expiry of reminders */
    void checkReminders();

    
}
