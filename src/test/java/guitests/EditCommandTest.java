package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.EditCommand;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.testutil.TestTask;
import seedu.inbx0.testutil.TestUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EditCommandTest extends TaskListGuiTest {
    
    //@@author A0139481Y
    @Test
    public void edit() throws IllegalArgumentException, IllegalValueException{
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        
        //edit the 1st task in the list
        assertEditSuccess(targetIndex, td.hoon, currentList);
        currentList = TestUtil.replaceTaskFromList(currentList, td.hoon, targetIndex - 1);
        
        //add a tag
        Tag tagToAdd = new Tag("urgent");
        commandBox.runCommand("addtag " + targetIndex + " t=urgent");
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getTags().contains(tagToAdd));
        
         //invalid index for adding tag
        commandBox.runCommand("deltag " + 13 + " t=urgent");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //delete a tag
        Tag tagToDelete = new Tag("urgent");
        commandBox.runCommand("deltag " + targetIndex + " t=urgent");
        ReadOnlyTask newTask2 = taskListPanel.getTask(targetIndex - 1);
        assertFalse(newTask2.getTags().contains(tagToDelete));
        
        //invalid index for delete tag
        commandBox.runCommand("deltag " + 13 + " t=urgent");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // invalid command
        commandBox.runCommand("edit Kitty");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        // invalid command, no parameters given
        commandBox.runCommand("edit");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        // invalid index
        commandBox.runCommand(td.ida.getAddCommand().replace("add", "edit " + (currentList.length+1) + " n="));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    
    /**
     * 
     * Runs the Edit command to edit a task at the specified index and confirms the result is correct 
     * @param targetIndex e.g. to mark the first task in the list as done, 1 should be given as the target index.
     * @param editedTask The Task that has been edited.
     * @param currentList A copy of the current list of tasks (before being marked as done).     
     */
    private void assertEditSuccess(int targetIndex, TestTask editedTask, TestTask... currentList) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(editedTask.getAddCommand().replace("add", "edit " + targetIndex + " n="));

        //confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(targetIndex-1);
        assertMatching(editedTask, editedCard);

        //confirm the list now contains all previous tasks with updated task
        currentList[targetIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(currentList));
      
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
