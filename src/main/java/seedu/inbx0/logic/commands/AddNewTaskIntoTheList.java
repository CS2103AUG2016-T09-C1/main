package seedu.inbx0.logic.commands;

import seedu.inbx0.commons.events.BaseEvent;
import seedu.inbx0.model.task.ReadOnlyTask;

public class AddNewTaskIntoTheList extends BaseEvent {
    
    public ReadOnlyTask task;
    public AddNewTaskIntoTheList(ReadOnlyTask task) {
        this.task = task;
    }
    
    @Override
    public String toString() {
        
        return "new task added";
    }

}
