package seedu.inbx0.model;

import java.util.List;

import javafx.collections.ObservableList;
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
    
    //** Saves the current state of the TaskList into a temporary history list **/
    void saveTaskListHistory();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Marks the given task complete. */
    void markTaskComplete(ReadOnlyTask target, Task task) throws UniqueTaskList.TaskNotFoundException;
    
    /** Edits the given task. */
    void editTask(ReadOnlyTask target, Task task) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Returns the backing task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getBackingTaskList();

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered task list of events or deadlines as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredNormalTaskList();
    
    /** Returns the filtered float task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatTaskList();
    
    /** Returns the filtered event task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventTaskList();
    
    /** Returns the filtered deadline task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineTaskList();
    
    /** Returns the filtered done task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDoneTaskList();
    
    /** Returns the filtered to do task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredToDoTaskList();
    
    /** Returns the filtered overdue task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredOverdueTaskList();
    
    /** Returns the filtered before due task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredBeforedueTaskList();
    
    /** Returns the filtered task list according to day as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDayTaskList(String day);
    
    /** Returns the filtered task list according to importance as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredImportanceTaskList(String importance);
    
    /** Returns the filtered task list after find command as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListAfterFindCommand(boolean logicRelation,
            List<String> keywords);


    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to filter overdue tasks */
    void updateFilteredOverdueTaskList();
    
    /** Updates the filter of the filtered task list to filter Beforedue tasks */
    void updateFilteredBeforedueTaskList();  
    
    /** Updates the filter of the filtered task list to filter events and deadline */
    void updateFilteredNormalTaskList();

    /** Updates the filter of the filtered task list to filter float tasks*/
    void updateFilteredFloatTaskList();
    
    /** Updates the filter of the filtered event list to filter float tasks*/
    void updateFilteredEventTaskList();
    
    /** Updates the filter of the filtered deadline list to filter float tasks*/
    void updateFilteredDeadlineTaskList();
    
    /** Updates the filter of the filtered task list to filter done tasks*/
    void updateFilteredDoneTaskList();
    
    /** Updates the filter of the filtered task list to filter to do tasks */
    void updateFilteredToDoTaskList();
    
    /** Updates the filter of the filtered task list according to day */
    void updateFilteredDayTaskList(String day);
    
    /** Updates the filter of the filtered task list according to importance */
    void updateFilteredImportanceTaskList(String importance);
       
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
