package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.MarkCompleteCommand;
import seedu.inbx0.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0139579J
public class DoneCommandTest extends TaskListGuiTest {
    
    @Test
    public void done() throws IllegalArgumentException, IllegalValueException {
        
        //marks the first task in the list as done
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertMarkAsDoneSuccess(targetIndex, currentList);
        
        //marks a task that is already marked as done
        commandBox.runCommand("done " + 1);
        assertResultMessage(MarkCompleteCommand.MESSAGE_TASK_ALREADY_COMPLETED);
        
        //invalid index for marking
        commandBox.runCommand("done " + 13);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //invalid command
        commandBox.runCommand("donee 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * 
     * Runs the Mark Complete command to mark the task at the specified index as done and confirms the result is correct 
     * @param targetIndexOneIndexed e.g. to mark the first task in the list as done, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before being marked as done).     
     */
    private void assertMarkAsDoneSuccess(int targetIndex, final TestTask[] currentList) throws IllegalArgumentException, IllegalValueException {
        TestTask taskToMark = currentList[targetIndex-1]; //-1 because array uses zero indexing
        
        taskToMark.markAsDone();
        commandBox.runCommand("done " + targetIndex);
        
        //confirm the list now contains the original list + the task marked as done
        assertTrue(taskListPanel.isListMatching(currentList));
        
        // find task card of marked task
        TaskCardHandle markedCard = taskListPanel.navigateToTask(taskToMark.getName().fullName);
        // confirm its the correct task
        assertMatching(taskToMark, markedCard);
        // confirm the task is marked
        assertDone(markedCard);
        
        //confirm the result message is correct
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_COMPLETED_OVERVIEW, 1));
    }
}
