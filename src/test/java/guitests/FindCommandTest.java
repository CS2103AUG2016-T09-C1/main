package guitests;

import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.FindCommand;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Time;
import seedu.inbx0.testutil.TestTask;

import static org.junit.Assert.assertTrue;
import static seedu.inbx0.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class FindCommandTest extends TaskListGuiTest {
	
	//@@author A0148044J
    @Test
    public void find_nonEmptyList() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.benson, td.daniel); //multiple results
       
        assertFindResult("find meier", td.benson, td.daniel, td.hoon);
        assertFindResult("find benson'Meier", td.benson); //find by connected name keywords

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
    
    @Test
    public void find_byTags() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find t=lunch", td.alice, td.hoon);
        assertFindResult("find t=suit");
    }

    @Test
    public void find_byStartDate() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find s=4th'Nov", td.alice, td.george);
        assertFindResult("find s=tmr", td.carl, td.ida);
        assertFindResult("find s=05092016"); //no result
        
        //type in invalid date when finding
        commandBox.runCommand("find s=good night");
        assertResultMessage(Date.MESSAGE_DATE_CONSTRAINTS);
        //keywords with whitespace should use ' to connect in OR search
        commandBox.runCommand("find s=next month"); 
        assertResultMessage(Date.MESSAGE_DATE_CONSTRAINTS);
    }
    
    @Test
    public void find_byEndtime() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find et=noon ", td.ida, td.noImportance, td.deadlineNoImportance);
        assertFindResult("find et=1am");
        //type in invalid time when finding
        commandBox.runCommand("find et=lunch");
        assertResultMessage(Time.MESSAGE_TIME_CONSTRAINTS);
    }
    
    @Test
    public void find_multipleKeywords() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find Alice Benson", td.alice, td.benson);
        assertFindResult("find Alice i=r", td.alice, td.carl, td.daniel, td.george, td.deadline);
        assertFindResult("find tmr"); //tmr need s=, e= to convert to date format, or it will be regarded as a task name
    }
    
    @Test 
    public void logic_expressionSearch() throws IllegalArgumentException, IllegalValueException {
        assertFindResult("find Meier & i=red", td.daniel);
        assertFindResult("find meier | alice", td.alice, td.benson, td.daniel, td.hoon);
        //logic expression search treat a string between two indicator [|, &, (, )] as one keyword
        assertFindResult("find benson meier | alice", td.alice, td.benson); 
        
        //complex logic search
        assertFindResult("find (meier | deadline)  & i=r", td.daniel, td.deadline);
        assertFindResult("find (meier | deadline)  & i=r | (floating & ( i=g | i=y))", td.daniel, td.deadline, td.floating);
        
        //Invalid input

        commandBox.runCommand("find (meier | deadline & i=r");  //bracket mismatch
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_BRACKET_USAGE));
        
        commandBox.runCommand("find | meier | deadline & i=r"); //start with & or |
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
        
        commandBox.runCommand("find meier | deadline & i=r &"); //end with & or |
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
        
        commandBox.runCommand("find meier (deadline & i=r)"); //open bracket after keyword
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
        
        commandBox.runCommand("find meier | & deadline & i=r"); //no keyword between two operator 
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));

        commandBox.runCommand("find meier | ) deadline & i=r"); //no keyword between two operator 
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
        
        commandBox.runCommand("find meier ( ) deadline & i=r"); //no keyword within bracket
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));

        commandBox.runCommand("find meier ( & deadline & i=r"); //no keyword between two operator 
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
        
        commandBox.runCommand("find meier && deadline & i=r"); //violate use of '&' 
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
        
        commandBox.runCommand("find meier || deadline & i=r"); //violate use of '|' 
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));

        commandBox.runCommand("find meier ) deadline & i=r"); //keyword after ')'
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
        
        commandBox.runCommand("find meier ) ( deadline & i=r"); //no keyword between ')' and '('
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
    }
    //@@author

    private void assertFindResult(String command, TestTask... expectedHits ) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
