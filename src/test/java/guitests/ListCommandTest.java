package guitests;

import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.ListCommand;
import seedu.inbx0.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0139579J
public class ListCommandTest extends TaskListGuiTest {
    
    @Test
    public void list() throws IllegalArgumentException, IllegalValueException {
        
        //list all incompleted tasks
        TestTask[] currentList = td.getTypicalTasks();
        assertListSuccess(currentList);
        
        //list tasks that start and end on that date
        assertListDateSuccess(currentList);
        
        
        //invalid command
        commandBox.runCommand("lisst");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    /**
     * Runs the List command and confirms the result is correct 
     * @param currentList A copy of the current list of tasks    
     */
    private void assertListSuccess(final TestTask[] currentList) throws IllegalArgumentException, IllegalValueException {
              
        commandBox.runCommand("list");

        //confirm the list shows all incompleted tasks
        assertTrue(taskListPanel.isListMatching(currentList));        
        //confirm the result message is correct
        assertResultMessage(ListCommand.MESSAGE_SUCCESS);
        
        // Navigate to another list
        commandBox.runCommand("show com");        
        // confirm that it is able to return to the list of all incompleted tasks
        commandBox.runCommand("list");
        assertTrue(taskListPanel.isListMatching(currentList));
        //confirm the result message is correct
        assertResultMessage(ListCommand.MESSAGE_SUCCESS);
    }
    
    /**
     * Runs the List Date command and confirms the result is correct 
     * @param currentList A copy of the current list of tasks.     
     */
    private void assertListDateSuccess(final TestTask[] currentList) throws IllegalArgumentException, IllegalValueException {
              
        commandBox.runCommand("list 06/11/2016");
        
        //Only the first task in the list is on 06/11/2016
        int index = 1;
        //Creates a list which shows the first task
        TestTask[] listDate = new TestTask[index];
        listDate[index-1] = currentList[index-1];
        
        //confirm the list show tasks on 06/11/2016
        assertTrue(taskListPanel.isListMatching(listDate));        
        //confirm the result message is correct
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, 1));
    }
    
}

