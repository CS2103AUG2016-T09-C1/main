package seedu.inbx0.commons.events.model;

import seedu.inbx0.commons.events.BaseEvent;

/** Indicates show the normal TaskList*/
public class ShowNormalTaskListEvent extends BaseEvent {

    @Override
    public String toString() {
        return "ShowNormalTaskList";
    }
}