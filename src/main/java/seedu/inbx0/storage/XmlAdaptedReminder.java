package seedu.inbx0.storage;

import javax.xml.bind.annotation.XmlElement;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Importance;
import seedu.inbx0.model.task.Name;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Time;

/**
 * JAXB-friendly adapted version of the Reminder.
 */
public class XmlAdaptedReminder {
    
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String taskStartDate;
    @XmlElement(required = true)
    private String taskStartTime;
    @XmlElement(required = true)
    private String taskEndDate;
    @XmlElement(required = true)
    private String taskEndTime;
    @XmlElement(required = true)
    private boolean isAlive;
    
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedReminder() {}
    
    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(ReminderTask source) {
        startDate = source.getStartDate().value;
        startTime = source.getStartTime().value;
        isAlive = source.getIsAlive();
        taskName = source.getReadableOnlyTask().getName().fullName;
        taskStartDate = source.getReadableOnlyTask().getStartDate().value;
        taskStartTime = source.getReadableOnlyTask().getStartTime().value;
        taskEndDate= source.getReadableOnlyTask().getEndDate().value;
        taskEndTime = source.getReadableOnlyTask().getEndTime().value;        
    }
    
    
    /**
     * Converts this jaxb-friendly adapted ReminderTask object into the model's ReminderTask object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ReminderTask
     */
    public ReminderTask toModelType() throws IllegalValueException {
        final Date startDate = new Date(this.startDate);
        final Time startTime = new Time(this.startTime);
        final UniqueTagList tags = new UniqueTagList();
        final UniqueReminderList reminders = new UniqueReminderList();
        final boolean isAlive = this.isAlive;
        final Name name = new Name(this.taskName);
        final Date taskStartDate = new Date(this.taskStartDate);
        final Time taskStartTime = new Time(this.taskStartTime);
        final Date taskEndDate = new Date(this.taskEndDate);
        final Time taskEndTime = new Time(this.taskEndTime);
        final Importance level = new Importance("");
        ReadOnlyTask remindTask = new Task(name, taskStartDate, taskStartTime, taskEndDate, taskEndTime, level, tags, reminders);
        return new ReminderTask(startDate, startTime, remindTask, isAlive);
    }
}