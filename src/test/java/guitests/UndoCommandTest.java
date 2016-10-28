package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.UndoCommand;
import seedu.inbx0.testutil.TestTask;

public class UndoCommandTest extends TaskListGuiTest {
    
    //@@author A0139481Y
    @Test
    public void undoZeroCommand() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        assertUndoResult("undo", UndoCommand.MESSAGE_NOTHING_TO_UNDO, currentList);
    }
    
    @Test
    public void undoZeroCommand_withArgs() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        assertUndoResult("undo 3", UndoCommand.MESSAGE_NOTHING_TO_UNDO, currentList);
    }
    
    @Test
    public void undoOneDelCommand() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("del 1");
        assertUndoResult("undo", UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, currentList);
    }
    
    @Test
    public void undoTwoDelCommands() throws IllegalArgumentException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("del 1");
        commandBox.runCommand("del 1");
        assertUndoResult("undo 2", UndoCommand.MESSAGE_UNDO_TASK_SUCCESS, currentList);
    }
    
    private void assertUndoResult(String command, String expectedMessage, TestTask... currentList) 
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(currentList.length);
        assertResultMessage(expectedMessage);
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
