package seedu.inbx0.logic.commands;

/**
 * Undo the past n-number of commands.
 */
//@@author A0139481Y
public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the last command(s)\n"
            + "Parameters: STEPS_BACK\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Last Command undone successfully.";
    public static final String MESSAGE_NOTHING_TO_UNDO = "There is no past command to undo.";
    
    private final int stepsBack;
    
    public UndoCommand(int stepsBack) {
        this.stepsBack = stepsBack;
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        
        int commandsUndone = model.undoTaskListHistory(stepsBack);
        
        if (commandsUndone == 0){
            return new CommandResult(MESSAGE_NOTHING_TO_UNDO);
        }
        
        return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
    
    @Override
    public boolean canUndo() {
        return false;
    }
}
