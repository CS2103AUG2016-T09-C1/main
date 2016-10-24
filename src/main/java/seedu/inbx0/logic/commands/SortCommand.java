package seedu.inbx0.logic.commands;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the tasks according to name, start time, end time or importance in descending or ascending order\n"
            + "Parameters: " + "[n|name] [s|start] [e|end] [i|importance] " + " [ASC|ascending|ascend] [DESC|descending|descend]\n"
            + "Example: " + COMMAND_WORD + " name" + " ASC";

    public static final String MESSAGE_SORT_TASK_SUCCESS = "Sorts Task Successfully";
    
    public final String type;
    private final boolean defaultOrder;
    
    public SortCommand(String type, boolean defaultOrder) {
        this.type = type;
        this.defaultOrder = defaultOrder;
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList(type, defaultOrder);
        return new CommandResult(MESSAGE_SORT_TASK_SUCCESS);
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
