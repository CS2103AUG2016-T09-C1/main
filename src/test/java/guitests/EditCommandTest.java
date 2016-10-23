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
        
        //edit tags
        Tag tagToAdd = new Tag("urgent");
        commandBox.runCommand("update " + targetIndex + " t=newTag");
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getTags().contains(tagToAdd));

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
