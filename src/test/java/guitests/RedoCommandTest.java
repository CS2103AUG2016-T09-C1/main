package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.RedoCommand;
import seedu.inbx0.testutil.TestTask;
import seedu.inbx0.testutil.TestUtil;

//@@author A0139481Y
public class RedoCommandTest extends TaskListGuiTest {
    
    @Test
    public void redoOneCommand() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("del 1");
        commandBox.runCommand("undo");
        assertRedoResult("redo", RedoCommand.MESSAGE_REDO_TASK_SUCCESS, 
                td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george);
    }
    
    @Test
    public void redoOneCommandFromTwoUndoes() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("del 1");
        commandBox.runCommand("del 1");
        commandBox.runCommand("undo 2");
        assertRedoResult("redo", RedoCommand.MESSAGE_REDO_TASK_SUCCESS, 
                td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george);
    }
    
    @Test
    public void redo_noHistory() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("del 1");
        assertRedoResult("redo", RedoCommand.MESSAGE_NOTHING_TO_REDO, 
                td.benson, td.carl, td.daniel, td.elle, td.fiona, td.george);
    }
    
    /**
     * 
     * Runs the Undo command to undo the TaskList back to a previous state and confirms the result is correct 
     * @param command Redo.
     * @param expectedMessage The expected result message.
     * @param expectedHits Expected ListView.     
     */
    private void assertRedoResult(String command, String expectedMessage, TestTask... expectedHits) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedMessage);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
