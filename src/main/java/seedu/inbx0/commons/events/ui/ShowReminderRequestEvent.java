package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;

/**
 * An event requesting to display the reminder.
 */
public class ShowReminderRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
