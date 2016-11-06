package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;
import seedu.inbx0.model.task.ReadOnlyTask;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToTaskRequestEvent extends BaseEvent {

    public ReadOnlyTask task;

    public JumpToTaskRequestEvent(ReadOnlyTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
