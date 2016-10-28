package guitests;

import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.logic.commands.SetDirCommand;

public class SetDirCommandTest extends TaskListGuiTest {
    
    @Test
    public void saveAs_Invalid() {
    
    commandBox.runCommand("saveas /new");
    assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetDirCommand.MESSAGE_USAGE));
    
    commandBox.runCommand("saveas /new/directory");
    assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetDirCommand.MESSAGE_USAGE));
    }
}
