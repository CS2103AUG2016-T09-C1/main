package guitests;

import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.logic.commands.SetDirCommand;

public class SetDirCommandTest extends TaskListGuiTest {
    
    @Test
    public void saveAs() {
        commandBox.runCommand("saveas temp.xml");
        assertResultMessage(String.format(SetDirCommand.MESSAGE_SAVE_TASK_SUCCESS, "temp.xml"));
    }
    
    @Test
    public void saveAs_Reset() {
        commandBox.runCommand("saveas reset");
        assertResultMessage(String.format(SetDirCommand.MESSAGE_SAVE_TASK_SUCCESS, "data/tasklist.xml"));
    }
    
    @Test
    public void saveAs_Invalid() {
        
    commandBox.runCommand("saveas");
    assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetDirCommand.MESSAGE_USAGE));
    
    commandBox.runCommand("saveas /new");
    assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetDirCommand.MESSAGE_USAGE));
    
    commandBox.runCommand("saveas /new/directory");
    assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetDirCommand.MESSAGE_USAGE));
    }
}
