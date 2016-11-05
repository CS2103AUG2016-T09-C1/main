package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;

/** Indicates close all titlepanes*/
public class CloseAllTitledPanesEvent extends BaseEvent {

    @Override
    public String toString() {
        return "programably close all titlepanes";
    }
}