package seedu.inbx0.logic.internalcommands;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.Command;
import seedu.inbx0.logic.commands.CommandResult;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Time;

//@@author A0139579J
/**
 * Checks tasks in the tasklist and determines whether the task has expired
 */
public class ExpiredCommand extends Command {
    
    private static final String MESSAGE_SUCCESS = "Successfully checked for expired tasks";
    public final Date currentDate;
    public final String currentTime;
    
    public ExpiredCommand() {
        Date currentDate = null;
        String currentTime = Time.getCurrentTime();
        
        try {
            currentDate = new Date("now");
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
                
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }
    
    public CommandResult execute() {
        assert model != null;
        model.checkExpiry(currentDate, currentTime);
        return new CommandResult(MESSAGE_SUCCESS);

    }

    @Override
    public boolean canUndo() {
        return false;
    }
}

