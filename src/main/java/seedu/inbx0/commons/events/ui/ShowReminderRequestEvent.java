package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;
import seedu.inbx0.model.task.ReadOnlyTask;

/**
 * An event requesting to display the reminder.
 */
public class ShowReminderRequestEvent extends BaseEvent {
    
    public final ReadOnlyTask task;

    public ShowReminderRequestEvent(ReadOnlyTask task) {
        this.task = task;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}