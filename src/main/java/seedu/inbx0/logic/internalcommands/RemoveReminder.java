package seedu.inbx0.logic.internalcommands;

import seedu.inbx0.logic.commands.Command;
import seedu.inbx0.logic.commands.CommandResult;

/**
 * Checks tasks in the tasklist and removes reminders that have already been activated
 */
//@@author A0139579J
public class RemoveReminder extends Command {
    
    private static final String MESSAGE_SUCCESS = "Successfully checked for activated reminders";
    
    public CommandResult execute() {
        assert model != null;
        model.checkReminders();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}