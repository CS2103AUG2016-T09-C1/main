package seedu.inbx0.testutil;

import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private Importance level;
    private boolean isCompleted;
    private boolean isExpired;
    private boolean isEvent;
    private boolean isFloatTask;
    
    
    private UniqueTagList tags;
    private UniqueReminderList reminders;
    
    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setLevel(Importance level) {
        this.level = level;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    @Override
    public UniqueReminderList getReminders() {
        return reminders;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public Importance getLevel() {
        return level;
    }
    
    @Override
    public boolean getIsCompleted() {
        return isCompleted;
    }
    
    @Override
    public boolean getIsExpired() {
        return isExpired;
    }
    
    @Override
    public boolean getIsEvent() {
        return isEvent;
    }
    
    @Override
    public boolean getIsFloatTask() {
        return isFloatTask;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    //@@author A0139481Y
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        if (this.getStartDate() != null)
            sb.append("s=" + this.getStartDate().value + " ");
        if (this.getStartTime() != null)
            sb.append("st=" + this.getStartTime().value + " ");
        if (this.getEndDate() != null)
            sb.append("e=" + this.getEndDate().value + " ");
        if (this.getEndTime() != null)
            sb.append("et=" + this.getEndTime().value + " ");
        if (this.getLevel() != null)
            sb.append("i=" + this.getLevel().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t=" + s.tagName + " "));
        return sb.toString();
    }
    
    public void markAsDone() {
        isCompleted = true;
    }
}
