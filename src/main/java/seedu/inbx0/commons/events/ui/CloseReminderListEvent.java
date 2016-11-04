package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;

/**
 * An event requesting to close the reminder list
 */
public class CloseReminderListEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
