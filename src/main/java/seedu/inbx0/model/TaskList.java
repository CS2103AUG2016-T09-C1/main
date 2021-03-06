package seedu.inbx0.model;

import javafx.collections.ObservableList;
import seedu.inbx0.model.history.HistoryState;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.UniqueTaskList;
import seedu.inbx0.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList, HistoryState<TaskList> {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private final UniqueReminderList reminders;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
        reminders = new UniqueReminderList();
    }

    public TaskList() {}

    /**
     * Tasks and Tags are copied into this task manager
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList(), toBeCopied.getUniqueReminderList());
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public TaskList(UniqueTaskList tasks, UniqueTagList tags, UniqueReminderList reminders) {
        resetData(tasks.getInternalList(), tags.getInternalList(), reminders.getInternalList());
    }

    public static ReadOnlyTaskList getEmptyTaskList() {
        return new TaskList();
    }
    
    //@@author A0139481Y
    /* Returns a copy of the current Task List */
    @Override
    public TaskList copyCurrentState() {
        return new TaskList(this);
    }
    //@@author

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }
    
    public void setReminders(Collection<ReminderTask> reminders) {
        this.reminders.getInternalList().setAll(reminders);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags, Collection<ReminderTask> newReminders) {
        setTasks(newTasks.stream().map(t -> {
            try {
                return new Task(t);
            } catch (IllegalValueException e) {
              System.out.println(Task.MESSAGE_TIME_CONSTRAINTS);
            }
            return null;
        }).collect(Collectors.toList()));
        setTags(newTags);
        setReminders(newReminders);
    }

    public void resetData(ReadOnlyTaskList newData) {
        resetData(newData.getTaskList(), newData.getTagList(), newData.getReminderList());
    }

//// task-level operations

    /**
     * Adds a task to the tasklist.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }
    
   
    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean removeAllTasksFromShownList(UnmodifiableObservableList<ReadOnlyTask> list) {
        return tasks.removeAll(list);     
    }
    
    public boolean editTask(ReadOnlyTask key, Task t) throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException {
        if (tasks.edit(key, t)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean markTaskComplete(ReadOnlyTask key, Task t) throws TaskNotFoundException {
        if (tasks.markComplete(key, t)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
        
    }
    
    /**
     * Checks tasks' expiry 
     * and updates the boolean isExpired accordingly if found expired.
     */
    public boolean checkExpiry(Date currentDate, String currentTime) {
        return (tasks.checkExpiry(currentDate, currentTime));
    }
    
    /**
     * Checks reminders' expiry 
     * and removes them if it has already been activated.
     */
    public boolean checkReminders() {
        return (tasks.checkReminders());
    }
    
//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
    
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }
    
    @Override
    public List<ReminderTask> getReminderList() {
        return Collections.unmodifiableList(reminders.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }
    
    @Override
    public UniqueReminderList getUniqueReminderList() {
        return this.reminders;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.tasks.equals(((TaskList) other).tasks)
                && this.tags.equals(((TaskList) other).tags)
                && this.reminders.equals(((TaskList) other).reminders));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags, reminders);
    }
}
