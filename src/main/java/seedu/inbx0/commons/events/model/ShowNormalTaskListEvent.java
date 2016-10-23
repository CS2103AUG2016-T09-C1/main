package seedu.inbx0.commons.events.model;

import seedu.inbx0.commons.events.BaseEvent;

/** Indicates the TaskList in the model has changed*/
public class ShowNormalTaskListEvent extends BaseEvent {

    public ShowNormalTaskListEvent(){
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}