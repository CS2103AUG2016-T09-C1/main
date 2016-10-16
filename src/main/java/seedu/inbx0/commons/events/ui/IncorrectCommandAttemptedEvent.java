package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;
import seedu.inbx0.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    private Command command;

    public IncorrectCommandAttemptedEvent(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
