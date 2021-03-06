package seedu.inbx0.commons.events.storage;

import seedu.inbx0.commons.events.BaseEvent;

//@@author A0135797M
public class StoragePathChangedEvent extends BaseEvent {
    
    public final String oldPath;
    public final String newPath;
    
    public StoragePathChangedEvent(String oldPath, String newPath) {
        this.oldPath = oldPath;
        this.newPath = newPath;
    }
    
    @Override
    public String toString() {
        return "Storage Path changed " + newPath;
    }
}
