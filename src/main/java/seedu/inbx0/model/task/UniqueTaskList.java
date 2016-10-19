package seedu.inbx0.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inbx0.commons.exceptions.DuplicateDataException;
import seedu.inbx0.commons.util.CollectionUtil;

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

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Sorts the task list according to the sorting type and order 
     * @param order 
     */
    public void sortUniqueTaskList(String type, boolean defaultOrder) {
        if("Name".equals(type)) {
            FXCollections.sort(internalList, new NameComparator());
        }
        else if("Start Time".equals(type)) {
            FXCollections.sort(internalList, new StartTimeComparator());
        }
        else if("End Time".equals(type)) {
            FXCollections.sort(internalList, new EndTimeComparator());
        }
        else if("Importance".equals(type)) {
            FXCollections.sort(internalList, new ImportanceComparator());
        }
        if(!defaultOrder) {
            FXCollections.reverse(internalList);
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
        
        if (contains(toEditWith)) {           
            if(internalList.indexOf(toEditWith) != editTaskIndex |
              ((toEdit.getName() == toEditWith.getName()) && 
               (toEdit.getStartDate().equals(toEditWith.getStartDate())) &&
               (toEdit.getStartTime().equals(toEditWith.getStartTime())) &&
               (toEdit.getEndDate().equals(toEditWith.getEndDate())) &&
               (toEdit.getEndTime().equals(toEditWith.getEndTime())) &&                
               (toEdit.getLevel().equals(toEditWith.getLevel())) &&
               (toEdit.getTags().equals(toEditWith.getTags()))))             
                throw new DuplicateTaskException();
        }       
        
        
        boolean taskEdited = false;
        final Task taskFoundAndEdited = internalList.set(editTaskIndex, toEditWith);  
        
        if(taskFoundAndEdited != null) 
            taskEdited = true;
        
        return taskEdited;
    }
    
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
    
    /**
     * A comparator use to compare the name of tasks
     * @author A0148044J
     */
    class NameComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            String name = task.getName().getName();
            String nameToCompare = taskToCompare.getName().getName();
            return name.compareTo(nameToCompare);
        }
    }

    /**
     * A comparator use to compare the start time of tasks
     * float task is considered as appear lastly in the natural order
     */
    class StartTimeComparator implements Comparator<Task> {
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
     * A comparator use to compare the end time of tasks
     * float task is considered as appear lastly in the natural order
     */
    class EndTimeComparator implements Comparator<Task> {
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
     * A comparator use to compare the importance of tasks
     * Importance: Red > Yellow > Green > NULL 
     */
    class ImportanceComparator implements Comparator<Task> {
        @Override
        public int compare(Task task, Task taskToCompare) {
            return taskToCompare.getLevel().getNumberLevel() - task.getLevel().getNumberLevel();
        }
    }
    
}
