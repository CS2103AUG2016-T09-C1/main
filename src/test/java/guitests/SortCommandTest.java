package guitests;

import org.junit.Test;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.ShowCommand;
import seedu.inbx0.logic.commands.SortCommand;
import seedu.inbx0.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A048044J
public class SortCommandTest extends TaskListGuiTest {
    
	//sort by name ascending order
    @Test
    public void sort_byName_ASC() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("sort name");  //default order
        assertShowResult("sort", "name", "", td.alice, td.benson, td.carl, td.daniel, td.elle,
                td.fiona, td.george);
        
        commandBox.runCommand("sort n");  //use shortcut
        assertShowResult("sort", "n", "", td.alice, td.benson, td.carl, td.daniel, td.elle,
                td.fiona, td.george);
        
        commandBox.runCommand("sort name asc");  //ascending order format 1
        assertShowResult("sort", "name", "asc", td.alice, td.benson, td.carl, td.daniel, td.elle,
                td.fiona, td.george);
        
        commandBox.runCommand("sort name ascending");  //ascending order format 2
        assertShowResult("sort", "name", "ascending", td.alice, td.benson, td.carl, td.daniel, td.elle,
                td.fiona, td.george);
        
        commandBox.runCommand("sort name ascend");  //ascending order format 3
        assertShowResult("sort", "name", "ascend", td.alice, td.benson, td.carl, td.daniel, td.elle,
                td.fiona, td.george);
        
        commandBox.runCommand("sort name ASc");  //test if ignore case
        assertShowResult("sort", "name", "ASc", td.alice, td.benson, td.carl, td.daniel, td.elle,
                td.fiona, td.george);
        
        commandBox.runCommand("done 1");  //after done 1
        commandBox.runCommand("sort name");  
        assertShowResult("sort", "name", "", td.benson, td.carl, td.daniel, td.elle,
                td.fiona, td.george);        
    }
    
	//sort by name descending order
    @Test
    public void sort_byName_DESC() throws IllegalArgumentException, IllegalValueException {
    	 commandBox.runCommand("sort name desc");  //descending order format 1
         assertShowResult("sort", "name", "desc", td.george, td.fiona, td.elle, 
					      td.daniel, td.carl, td.benson, td.alice);
         
    	 commandBox.runCommand("sort name descending");  //descending order format 2
    	 assertShowResult("sort", "name", "desc", td.george, td.fiona, td.elle, 
			      td.daniel, td.carl, td.benson, td.alice);         
    	 commandBox.runCommand("sort name descend");  //descending order format 3
    	 assertShowResult("sort", "name", "desc", td.george, td.fiona, td.elle, 
			      td.daniel, td.carl, td.benson, td.alice);
    	 
    	 commandBox.runCommand("sort name deSc");  //test if ignore case
    	 assertShowResult("sort", "name", "desc", td.george, td.fiona, td.elle, 
			      td.daniel, td.carl, td.benson, td.alice);

         commandBox.runCommand("done 1");  //after del 1
         commandBox.runCommand("sort name desc");  
         assertShowResult("sort", "name", "desc", td.fiona, td.elle, 
			      td.daniel, td.carl, td.benson, td.alice);
    }
    
    //sort by date 
    @Test
    public void sort_byDate() throws IllegalArgumentException, IllegalValueException {
    	commandBox.runCommand("sort start");  //default order
        assertShowResult("sort", "start", "", td.benson, td.george,td.alice, td.carl,
        		  td.daniel,  td.elle,  td.fiona);
        
    	commandBox.runCommand("sort s");  //use shortcut
        assertShowResult("sort", "s", "", td.benson, td.george, td.alice, td.carl,
        		  td.daniel,  td.elle,  td.fiona);
        
        commandBox.runCommand("sort start asc");  //ascending order
        assertShowResult("sort", "start", "asc", td.benson, td.george, td.alice, td.carl,
      		  td.daniel,  td.elle,  td.fiona);
        
        commandBox.runCommand("sort start desc"); //descending order
        assertShowResult("sort", "start", "desc", td.fiona, td.elle,  td.daniel, td.carl,
        		td.alice, td.george, td.benson);
    
    	commandBox.runCommand("sort end");  
        assertShowResult("sort", "end", "", td.benson, td.george, td.alice, td.carl,
        		  td.daniel,  td.elle,  td.fiona);
        
        commandBox.runCommand("sort e");  
        assertShowResult("sort", "e", "", td.benson, td.george, td.alice, td.carl,
        		  td.daniel,  td.elle,  td.fiona);
        
        commandBox.runCommand("sort end asc");  
        assertShowResult("sort", "end", "asc", td.benson, td.george, td.alice, td.carl,
      		  td.daniel,  td.elle,  td.fiona);
        
        commandBox.runCommand("sort end desc"); 
        assertShowResult("sort", "end", "desc", td.fiona,  td.elle, td.daniel, td.carl, 
        		td.alice, td.george, td.benson);
    }
    
  //sort by date 
    @Test
    public void sort_byImportance() throws IllegalArgumentException, IllegalValueException {
    	commandBox.runCommand("sort importance");  //default order
        assertShowResult("sort", "importance", "", td.carl, td.daniel, td.george, td.benson,
      		    td.elle,  td.alice, td.fiona);
        
        commandBox.runCommand("sort i");  //use shortcut
        assertShowResult("sort", "i", "", td.carl, td.daniel, td.george, td.benson,
      		    td.elle,  td.alice, td.fiona);
        
        commandBox.runCommand("sort i desc");  //descending order
        assertShowResult("sort", "importance", "desc", td.carl, td.daniel, td.george, td.benson,
      		    td.elle,  td.alice, td.fiona);
        
        commandBox.runCommand("sort importance asc");  //ascending order
        assertShowResult("sort", "importance", "asc", td.alice, td.fiona, td.benson, td.elle, 
      		      td.carl, td.daniel, td.george);
    }
        
    
    //invalid command
    @Test
    public void show_invalidCommand() {
        commandBox.runCommand("sort"); //no type
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));       

        commandBox.runCommand("soRt name"); //command word sensitivity
        assertResultMessage(String.format(Messages.MESSAGE_UNKNOWN_COMMAND));

        commandBox.runCommand("sort importance des"); //violate input to indicate descending order
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("sort name a"); //redundant word
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        commandBox.runCommand("sort asc name"); //cannot change order
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("sort importance name"); //cannot sort two type together
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
    
    /**
     * 
     * Runs the Sort command to sort all tasks that pertain to a specific type and confirms the result is correct 
     * @param command Sort.
     * @param type The type to sort the TaskList with.
     * @param order The order for sorting
     * @param expectedList Epected ListView.     
     */
    private void assertShowResult(String command, String type, String order, TestTask... expectedList) 
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command + " " + type + " " + order);
        assertListSize(expectedList.length);
        assertResultMessage(SortCommand.MESSAGE_SORT_TASK_SUCCESS);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    //@@author
}
