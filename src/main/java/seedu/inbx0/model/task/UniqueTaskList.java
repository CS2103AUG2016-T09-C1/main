package seedu.inbx0.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.exceptions.DuplicateDataException;
import seedu.inbx0.commons.util.CollectionUtil;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task>{
    
    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    /**
     * Sorts the task list according to the sorting type and order 
     * @param order 
     */
    public void sortUniqueTaskList(String type, boolean defaultOrder) {
        if("Name".equals(type)) {
        	if(defaultOrder) {
        		FXCollections.sort(internalList, new ASCNameComparator());
        	} else {
        		FXCollections.sort(internalList, new DESCNameComparator());
        	}
        } else if("Start Time".equals(type)) {
        	if(defaultOrder) {
        		FXCollections.sort(internalList, new ASCStartTimeComparator());
        	} else {
        		FXCollections.sort(internalList, new DESCStartTimeComparator());
        	}
        } else if("End Time".equals(type)) {
        	if(defaultOrder) {
        		FXCollections.sort(internalList, new ASCEndTimeComparator());
        	} else {
        		FXCollections.sort(internalList, new DESCEndTimeComparator());
        	}        
        } else if("Importance".equals(type)) {
        	if(defaultOrder) {
        		FXCollections.sort(internalList, new DESCImportanceComparator());
        	} else {
        		FXCollections.sort(internalList, new ASCImportanceComparator());
        	}
        }
    }
    
    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    /**
     * Removes the all the tasks from the shown list.
     *
     */
    public boolean removeAll(UnmodifiableObservableList<ReadOnlyTask> list) {
        boolean changed = false;
        ListIterator<ReadOnlyTask> check = list.listIterator(list.size());
        while(check.hasPrevious()) {
            ReadOnlyTask toRemove = check.previous();
            if(internalList.contains(toRemove)) {
                internalList.remove(toRemove);
                changed = true;
            }
        }
        return changed;
    }
    
    //@@author A0139579J
    /**
     * Edits the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws DuplicateTaskException if the task that was edited is a duplicate of an existing task in the list
     */    
    public boolean edit(ReadOnlyTask toEdit, Task toEditWith) throws TaskNotFoundException, DuplicateTaskException {
        assert toEdit != null;
        int editTaskIndex = internalList.indexOf(toEdit);
        if (editTaskIndex < 0) {
            throw new TaskNotFoundException();
        }
        
        if (contains(toEditWith) && (internalList.indexOf(toEditWith) != editTaskIndex |
              ((toEdit.getName() == toEditWith.getName()) && 
               (toEdit.getStartDate().equals(toEditWith.getStartDate())) &&
               (toEdit.getStartTime().equals(toEditWith.getStartTime())) &&
               (toEdit.getEndDate().equals(toEditWith.getEndDate())) &&
               (toEdit.getEndTime().equals(toEditWith.getEndTime())) &&                
               (toEdit.getLevel().equals(toEditWith.getLevel())) &&
               (toEdit.getTags().equals(toEditWith.getTags()))))) {         
                throw new DuplicateTaskException();
        }       
        
        boolean taskEdited = false;
        final Task taskFoundAndEdited = internalList.set(editTaskIndex, toEditWith);  
        
        if(taskFoundAndEdited != null) 
            taskEdited = true;
        
        return taskEdited;
    }
    
    //@@author A0139579J
    /**
     * Marks the equivalent task from the list as completed.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean markComplete(ReadOnlyTask toMarkComplete, Task completedTask) throws TaskNotFoundException {
        assert toMarkComplete != null;
        int markCompletedTaskIndex = internalList.indexOf(toMarkComplete);
        if (markCompletedTaskIndex < 0) {
            throw new TaskNotFoundException();
        }
        
        boolean taskMarkedCompleted = false;
        final Task taskFoundAndMarkedCompleted = internalList.set(markCompletedTaskIndex, completedTask);  
        
        if(taskFoundAndMarkedCompleted != null) 
            taskMarkedCompleted = true;
        
        return taskMarkedCompleted;
    }
    
    //@@author A0139579J
    /**
     * Checks expiry and sets the boolean isExpired accordingly for the tasks in the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */    
    public boolean checkExpiry(Date currentDate, String currentTime) {
        if(internalList.isEmpty()) {
            return false;
        }
        
        boolean changed = false;
        for(Task t : internalList) { 
            if(t.getEndDate().value.length() != 0 && t.getEndTime().value.length() != 0) {        
                if ((t.getEndDate().getYear() < currentDate.getYear()) | 
                    ((t.getEndDate().getYear() == currentDate.getYear()) && (t.getEndDate().getMonth() < currentDate.getMonth())) | 
                    ((t.getEndDate().getYear() == currentDate.getYear()) && (t.getEndDate().getMonth() == currentDate.getMonth()) && (t.getEndDate().getDay() < currentDate.getDay())) |
                    (t.getEndDate().value.equals(currentDate.value) && (Integer.parseInt(t.getEndTime().value.replaceAll("\\D+","")) <= Integer.parseInt(currentTime.replaceAll("\\D+",""))))) {          
                    if(!t.getIsExpired()) {
                        t.setExpired(true);
                        changed = true;
                    }
                }
            }
            else if (t.getEndDate().value.length() != 0 && t.getEndTime().value.length() == 0) {
                    if ((t.getEndDate().getYear() < currentDate.getYear()) |
                       ((t.getEndDate().getYear() == currentDate.getYear()) && (t.getEndDate().getMonth() < currentDate.getMonth())) |
                       ((t.getEndDate().getYear() == currentDate.getYear()) && (t.getEndDate().getMonth() == currentDate.getMonth())
                       && (t.getEndDate().getDay() < currentDate.getDay()))) {
                        if(!t.getIsExpired()) {
                            t.setExpired(true);
                            changed = true;
                        }             
                    }
                }  
            }  
        return changed;
    }
    
    //@@author A0139579J
    /**
     * Checks reminders and remove them if they have been activated.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean checkReminders() {
        if(internalList.isEmpty()) {
            return false;
        }
        
        boolean changed = false;
        for(Task t : internalList) { 
            UniqueReminderList reminders = t.getReminders();
            Iterator<ReminderTask> check = reminders.iterator();
            while(check.hasNext()) {
                if(!check.next().getIsAlive()) {
                    check.remove();
                    changed = true;
                }
            }
          t.setReminders(reminders);
        }
        return changed;
    }
    
    //@@author
    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    
    //@@author A0148044J
    /**
     * A comparator use to compare the name of tasks, in ascending order
     */
    class ASCNameComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            String name = task.getName().getName().toLowerCase();
            String nameToCompare = taskToCompare.getName().getName().toLowerCase();
            return name.compareTo(nameToCompare);
        }
    }
    
    /**
     * A comparator use to compare the name of tasks, in descending order
     */
    class DESCNameComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            ASCNameComparator comparator = new ASCNameComparator();
            return - comparator.compare(task, taskToCompare);
        }
    }

    /**
     * A comparator use to compare the start time of tasks in ascending order
     * float task is considered as appear lastly in the natural order
     */
    class ASCStartTimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            String startDate = task.getStartDate().getDateYYYYMMDDFormat();
            String startTime = task.getStartTime().getTime();
            String startDateToCompare = taskToCompare.getStartDate().getDateYYYYMMDDFormat();
            String startTimeToCompare = taskToCompare.getStartTime().getTime();
            if("".equals(startDate) || "".equals(startDateToCompare)) {
                return (0 - startDate.compareTo(startDateToCompare));
            }
            if(startDate.equals(startDateToCompare)) {
                if("".equals(startTime) || "".equals(startTimeToCompare)) {
                    return (0 - startTime.compareTo(startTimeToCompare));
                }
                return startTime.compareTo(startTimeToCompare);
            }
            return startDate.compareTo(startDateToCompare);
        }
    }
    
    /**
     * A comparator use to compare the start time of tasks in descending order
     * float task is considered as appear lastly in the natural order
     */
    class DESCStartTimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            ASCStartTimeComparator comparator = new ASCStartTimeComparator();
            return - comparator.compare(task, taskToCompare);
        }
    }
    
    /**
     * A comparator use to compare the end time of tasks in ascending order
     * float task is considered as appear lastly in the natural order
     */
    class ASCEndTimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            String endDate = task.getEndDate().getDateYYYYMMDDFormat();
            String endTime = task.getEndTime().getTime();
            String endDateToCompare = taskToCompare.getEndDate().getDateYYYYMMDDFormat();
            String endTimeToCompare = taskToCompare.getEndTime().getTime();
            if("".equals(endDate) || "".equals(endDateToCompare)) {
                return (0 - endDate.compareTo(endDateToCompare));
            }
            else if(endDate.equals(endDateToCompare)) {
                if("".equals(endTime) || "".equals(endTimeToCompare)) {
                    return (0 - endTime.compareTo(endTimeToCompare));
                }
                return endTime.compareTo(endTimeToCompare);
            }
            return endDate.compareTo(endDateToCompare);
        }
    }
    
    /**
     * A comparator use to compare the end time of tasks in descending order
     * float task is considered as appear lastly in the natural order
     */
    class DESCEndTimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            ASCEndTimeComparator comparator = new ASCEndTimeComparator();
            return - comparator.compare(task, taskToCompare);
        }
    }
    
    /**
     * A comparator use to compare the importance of tasks in ascending order
     * Importance: Red > Yellow > Green > NULL 
     */
    class ASCImportanceComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            return task.getLevel().getNumberLevel() - taskToCompare.getLevel().getNumberLevel();
        }
    }
    
    /**
     * A comparator use to compare the importance of tasks in descending order
     * Importance: Red > Yellow > Green > NULL 
     */
    class DESCImportanceComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            return -(task.getLevel().getNumberLevel() - taskToCompare.getLevel().getNumberLevel());
        }
    }
    //@@author
}
