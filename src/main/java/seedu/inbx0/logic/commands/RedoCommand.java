package seedu.inbx0.logic.commands;

//@@author A0135797M
/*
 * Redo the previous undone command
 */

public class RedoCommand extends Command{
	 
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo a previously entered undo command(s)\n"
            + "Parameters: [STEPS_FORWARD]\n"
            + "Example: " + COMMAND_WORD  + " 2";

    public static final String MESSAGE_REDO_TASK_SUCCESS = "Previous Undo Command redone successfully.";
    public static final String MESSAGE_NOTHING_TO_REDO = "Please undo a command, before you use redo!";
    
    private final int stepsBack;
    
    public RedoCommand() {
        stepsBack = 1;
    }
    
    public RedoCommand(int stepsBack) {
        this.stepsBack = stepsBack;
    }
     
    @Override
    public CommandResult execute() {
        assert model != null;
        
        int commandsRedone = model.redoTaskListHistory(stepsBack);
        
        if (commandsRedone == 0){
            return new CommandResult(MESSAGE_NOTHING_TO_REDO);
        }
        
        return new CommandResult(MESSAGE_REDO_TASK_SUCCESS);
    }
    
    @Override
    public boolean canUndo() {
        return false;
    }
}
