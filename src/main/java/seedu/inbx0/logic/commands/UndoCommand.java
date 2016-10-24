package seedu.inbx0.logic.commands;

public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the last command(s)\n"
            + "Parameters: STEPS_BACK\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Last Command undone successfully.";
    public static final String MESSAGE_NOTHING_TO_UNDO = "There is no past command to undo.";
    
    private final int stepsBack;
}
