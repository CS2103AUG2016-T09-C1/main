package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.ShowCommand;
import seedu.inbx0.testutil.TestTask;
import seedu.inbx0.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ShowCommandTest extends TaskListGuiTest {
    
    //invalid command
    @Test
    public void show_invalidCommand() {
        commandBox.runCommand("show");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void show_inCompletedTasks() throws IllegalArgumentException, IllegalValueException {
        assertShowResult("show", "incomplete", td.alice, td.benson, td.carl, 
                td.daniel, td.elle, td.fiona, td.george);
    }
    
    private void assertShowResult(String command, String filterCondition, TestTask... expectedList) 
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command + " " + filterCondition);
        assertListSize(expectedList.length);
        assertResultMessage(String.format(ShowCommand.SHOWING_FILTERED_LIST_MESSAGE + 
                " with keyword " + filterCondition.substring(0, 1).toUpperCase() + filterCondition.substring(1)));
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
