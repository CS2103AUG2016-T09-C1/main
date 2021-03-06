package seedu.inbx0.logic.commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Importance;
import seedu.inbx0.model.task.Name;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Time;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139579J
/**
 * Marks a task identified using it's last displayed index from the tasklist as completed.
 */
public class MarkCompleteCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as completed.\n"
            + "Parameters: INDEX (must be a positive integer and can be more than one input given)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "One or more tasks are already completed";
    public static final String MESSAGE_INVALID_ARGUMENTS = "The arguments provided are invalid.";

    public final List<Integer> indexNum;

    public MarkCompleteCommand(Set<Integer> indexNumSet) throws IllegalValueException {
    	validateSet(indexNumSet);
        this.indexNum = new ArrayList<Integer> (indexNumSet);
        sortIndexNumInDescendingOrder();
    }

    /**
     * Checks if the user has input any numerical values
     */
    private void validateSet(Set<Integer> indexNumSet) throws IllegalValueException {
        if(indexNumSet.isEmpty())
            throw new IllegalValueException(MESSAGE_INVALID_ARGUMENTS);        
    }
    
    /**
     * Sorts the set of numbers in descending order in preparation to mark them as complete
     */
    private void sortIndexNumInDescendingOrder() {
    	indexNum.sort(new IndexComparator());
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        for(Integer i : indexNum) {
            if (lastShownList.size() < i) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }
        
        for(Integer i : indexNum) {
            ReadOnlyTask toMarkTaskCompleted = lastShownList.get(i - 1);
            Task completedTask = null;
            
            if(toMarkTaskCompleted.getIsCompleted()) {
                return new CommandResult(MESSAGE_TASK_ALREADY_COMPLETED);
            }
            try {
                completedTask = new Task (
                    new Name(toMarkTaskCompleted.getName().getName()),
                    new Date(toMarkTaskCompleted.getStartDate().getDate()),
                    new Time(toMarkTaskCompleted.getStartTime().getTime()),
                    new Date(toMarkTaskCompleted.getEndDate().getDate()),
                    new Time(toMarkTaskCompleted.getEndTime().getTime()),
                    new Importance(toMarkTaskCompleted.getLevel().getLevel()),
                    toMarkTaskCompleted.getTags(),
                    toMarkTaskCompleted.getReminders()
                    );
            } catch (IllegalValueException e) {
                return new CommandResult(String.format(MESSAGE_INVALID_ARGUMENTS, MESSAGE_USAGE));
            }
            
            completedTask.setCompleted(true);
            
            try {
                model.markTaskComplete(toMarkTaskCompleted, completedTask);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        return new CommandResult(getMessageForTasksCompletedSummary(indexNum.size()));       
    }


    @Override
    public boolean canUndo() {
        return true;
    }
    
    //@@author A0148044J 
    /**
     * A comparator use to compare the index of tasks
     * @return true if first task is smaller than the second task
     */
    class IndexComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer index, Integer indexToCompare) {
            return indexToCompare - index;
        }
    }
    //@@author
}
