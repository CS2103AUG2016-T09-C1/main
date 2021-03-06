package seedu.inbx0.logic.commands;

import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exits the program\n" 
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Tasklist as requested ...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
