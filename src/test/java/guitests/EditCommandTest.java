package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class EditCommandTest extends TaskListGuiTest {
    
    @Test
    public void edit() throws IllegalArgumentException, IllegalValueException{
        TestTask[] currentList = td.getTypicalTasks();
        currentList[0] = td.hoon;
        assertEditSuccess(1, td.hoon, currentList);

        
    }
    
    private void assertEditSuccess(int index, TestTask editedTask, TestTask... currentList) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(editedTask.getAddCommand().replace("add", "edit " + index + " n/"));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        //confirm the list now contains all previous tasks with updated task
        currentList[index - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
