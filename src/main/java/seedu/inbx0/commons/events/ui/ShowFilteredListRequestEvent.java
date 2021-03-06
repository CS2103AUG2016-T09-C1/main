package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowFilteredListRequestEvent extends BaseEvent {
    
    public final String filterCondition;
    
    public ShowFilteredListRequestEvent(String filterCondition) {
        this.filterCondition = filterCondition;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}