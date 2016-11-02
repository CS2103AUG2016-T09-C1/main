package seedu.inbx0.logic.commands;

//@@author A0135797M
/*
 * Redo the previous undone command
 */

public class RedoCommand extends Command{
	 
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo a previously entered undo command(s)\n"
            + "Example: " + COMMAND_WORD ;

    public static final String MESSAGE_REDO_TASK_SUCCESS = "Previous Undo Command redone successfully.";
    public static final String MESSAGE_NOTHING_TO_REDO = "Please undo a command, before you use redo!";
    
    private final int stepsBack;
    
    public RedoCommand() {
        stepsBack = 1;
    }
     
    @Override
    public CommandResult execute() {
        assert model != null;
        
        int commandsUndone = model.undoTaskListHistory(stepsBack);
        
        if (commandsUndone == 0){
            return new CommandResult(MESSAGE_NOTHING_TO_REDO);
        }
        
        return new CommandResult(MESSAGE_REDO_TASK_SUCCESS);
    }
    
    @Override
    public boolean canRedo() {
        return false;
    }
}
