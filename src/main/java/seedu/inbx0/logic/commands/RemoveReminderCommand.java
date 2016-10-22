package seedu.inbx0.logic.commands;

public class RemoveReminderCommand extends Command {
    
    private static final String MESSAGE_SUCCESS = "Successfully checked for expired tasks";
    
    public RemoveReminderCommand() {}
    
    public CommandResult execute() {
        assert model != null;
        model.checkReminders();
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
