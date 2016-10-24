package seedu.inbx0.logic.internalcommands;

import java.text.SimpleDateFormat;
import java.util.List;

import com.joestelmach.natty.Parser;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.Command;
import seedu.inbx0.logic.commands.CommandResult;
import seedu.inbx0.model.task.Date;

/**
 * Checks tasks in the tasklist and determines whether the task has expired
 */
//@@author A0139579J
public class ExpiredCommand extends Command {
    
    private static final String MESSAGE_SUCCESS = "Successfully checked for expired tasks";
    public final Date currentDate;
    public final String currentTime;
    
    public ExpiredCommand() {
        Date currentDate = null;
        String currentTime = null;
        List<java.util.Date> current = new Parser().parse("now").get(0).getDates();
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
        
        currentTime = ft.format(current.get(0));
                
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
}

