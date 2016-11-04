package seedu.inbx0.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.ReadOnlyTaskList;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskList that is serializable to XML format
 */
@XmlRootElement(name = "tasklist")
public class XmlSerializableTaskList implements ReadOnlyTaskList {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<Tag> tags;
    @XmlElement
    private List<XmlAdaptedReminder> reminders;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
        reminders = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskList() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskList(ReadOnlyTaskList src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
        reminders.addAll(src.getReminderList().stream().map(XmlAdaptedReminder::new).collect(Collectors.toList()));
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            //TODO: better error handling
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public UniqueReminderList getUniqueReminderList() {
        UniqueReminderList lists = new UniqueReminderList();
        for (XmlAdaptedReminder r : reminders) {
            try {
                lists.add(r.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }
    
    @Override
    public List<ReminderTask> getReminderList() {
        return reminders.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
