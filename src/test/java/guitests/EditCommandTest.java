package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.EditCommand;
import seedu.inbx0.testutil.TestTask;
import seedu.inbx0.testutil.TestUtil;

public class EditCommandTest extends TaskListGuiTest {
    
    @Test
    public void editTask_invalidIndexGiven() {
        TestTask[] currentList = td.getTypicalTasks();
        
        commandBox.runCommand("edit -1 newName"); //invalid index
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void edit() throws IllegalArgumentException, IllegalValueException{
        TestTask[] currentList = td.getTypicalTasks();
        
        commandBox.runCommand("edit 2 newName s/next week t/late");
        assertResultMessage(EditCommand.MESSAGE_EDIT_TASK_SUCCESS);
        
        commandBox.runCommand("edit 3 newName s/invalid_date t/late");
        assertResultMessage(Messages.MESSAGE_INVALID_ARGUMENTS);
    }
}
