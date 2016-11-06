package guitests;

import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskListGuiTest {

    @Test
    public void find_nonEmptyList() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.benson, td.daniel); //multiple results

        //find after deleting one result
        commandBox.runCommand("del 1");
        assertFindResult("find Meier",td.daniel);
    }

    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalValueException{
        commandBox.runCommand("clr");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    //@@author A0148044J
    @Test
    public void find_byTags() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find t=friends", td.benson);
    }
    
    @Test
    public void find_multipleKeywords() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find Alice Benson", td.alice, td.benson);
    }
    
    @Test
    public void find_byNameAndImportance() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find Meier & i=red", td.daniel);
    }
    //@@author

    private void assertFindResult(String command, TestTask... expectedHits ) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
