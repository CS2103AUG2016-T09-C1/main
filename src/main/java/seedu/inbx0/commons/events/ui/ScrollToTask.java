package seedu.inbx0.commons.events.ui;

import seedu.inbx0.commons.events.BaseEvent;
import seedu.inbx0.model.task.ReadOnlyTask;

public class ScrollToTask extends BaseEvent {
 
    public ReadOnlyTask task;

    public ScrollToTask(ReadOnlyTask task) {
        this.task = task;
    }
    
    @Override
    public String toString() {
        
        return "scroll to the task";
    }

}
