package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.UndoCommand;
import seedu.inbx0.testutil.TestTask;
import seedu.inbx0.testutil.TestUtil;

//@@author A0139481Y
public class UndoCommandTest extends TaskListGuiTest {
    
    @Test
    public void undo_ZeroCommand() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        assertUndoResult("undo", UndoCommand.MESSAGE_NOTHING_TO_UNDO, currentList);
    }
    
    @Test
    public void undo_ZeroCommandWithArgs() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        assertUndoResult("undo 3", UndoCommand.MESSAGE_NOTHING_TO_UNDO, currentList);
    }
    
    @Test
    public void undo_OneCommand() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("del 1");
        assertUndoResult("undo", UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, currentList);
    }
    
    @Test
    public void undo_TwoCommands() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("del 1");
        commandBox.runCommand("del 1");
        assertUndoResult("undo 2", UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, currentList);
    }
    
    @Test
    public void undo_OneDelCommandsFromTwo() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("del 1");
        commandBox.runCommand("del 1");
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, 1);
        assertUndoResult("undo", UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, expectedRemainder);
    }
    
    @Test
    public void undo_InvalidArgs() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("del 1");
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, 1);
        commandBox.runCommand("del 1");
        expectedRemainder = TestUtil.removeTaskFromList(expectedRemainder, 1);
        assertUndoResult("undo -2", "Invalid command format! \n" + UndoCommand.MESSAGE_USAGE, expectedRemainder);
        assertUndoResult("undo String", "Invalid command format! \n" + UndoCommand.MESSAGE_USAGE, expectedRemainder);
    }
    
    @Test
    public void undo_AllCommands() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("del 1");
        TestTask[] remainder = TestUtil.removeTaskFromList(currentList, 1);
        commandBox.runCommand("del 1");
        remainder = TestUtil.removeTaskFromList(remainder, 1);
        assertUndoResult("undo 4", UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, currentList);
    }
    
    /**
     * 
     * Runs the Undo command to undo the TaskList back to a previous state and confirms the result is correct 
     * @param command Undo.
     * @param expectedMessage The expected result message.
     * @param currentList Expected ListView.     
     */
    private void assertUndoResult(String command, String expectedMessage, TestTask... currentList) 
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(currentList.length);
        assertResultMessage(expectedMessage);
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
