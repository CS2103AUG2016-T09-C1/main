package seedu.inbx0.logic.commands;

public class RemoveReminder extends Command {
    
    private static final String MESSAGE_SUCCESS = "Successfully checked for expired tasks";
    
    public RemoveReminder() {}
    
    public CommandResult execute() {
        assert model != null;
        model.checkReminders();
        return new CommandResult(MESSAGE_SUCCESS);

    }
}