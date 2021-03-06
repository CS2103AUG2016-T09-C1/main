package seedu.inbx0.model.reminder;

import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.events.ui.ShowReminderRequestEvent;
import seedu.inbx0.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.Time;

//@@author A0139579J
/**
 * Represents a Reminder for the task 
 */
public class ReminderTask {
    
    private Toolkit toolkit;
    private Timer timer;
    private Date date;
    private Time time;
    private ReadOnlyTask task;
    private boolean isAlive;
    
    public ReminderTask(Date date, Time time, ReadOnlyTask task, boolean isAlive) {
        toolkit = Toolkit.getDefaultToolkit();
        this.date = date;
        this.time = time;
        this.task = task;
        this.isAlive = true;
        
        Date currentDate = null;
        try {
            currentDate = new Date("now");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        
        if (currentDate.value.equals(date.value)) {
            if (!("").equals(time.getTime())) {
                setTimer(time);
            } else {
                timer = new Timer();
                timer.schedule(new ReminderMessage(), 1);
            }
        }
    }
    
    /**
     * Sets the timer according to the start time of the Reminder
     */
    private void setTimer(Time time) {
        int reminderTime = Integer.parseInt(time.getTime().replaceAll("\\D+",""));
        int reminderHour = reminderTime / 100;
        int reminderMin = reminderTime % 100;
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
        calendar.set(Calendar.MINUTE, reminderMin);
        calendar.set(Calendar.SECOND, 0);
        
        java.util.Date timerRemind = calendar.getTime();
        timer = new Timer();
        timer.schedule(new ReminderMessage(), timerRemind);
    }
    
    /**
     * Copy constructor.
     * @throws IllegalValueException if not valid
     */
    public ReminderTask(final ReminderTask source) throws IllegalValueException {
        this(source.getStartDate(), source.getStartTime(),source.getReadableOnlyTask(), source.getIsAlive());
    }
    
    /**
     * Getter methods for ReminderTask
     */
    public Date getStartDate() {
        return date;
    }
    
    public Time getStartTime() {
        return time;
    }

    public ReadOnlyTask getReadableOnlyTask() {
        return task;
    }
    
    public boolean getIsAlive() {
        return isAlive;
    }
    
    /**
     * Setter method for ReminderTask
     */
    public void setReadableOnlyTask(ReadOnlyTask task) {
       this.task = task;
    }
    
    /**
     * Cancels the ReminderTask
     */
    public void cancel() {
        timer.cancel();
     }
    
    /**
     * Format state as text for viewing.
     */
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Reminder")
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" Start Time: ")
                .append(getStartTime());
        return builder.toString();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderTask // this is first to avoid NPE below 
                && this.date.value.equals(((ReminderTask) other).date.value) // state checks here onwards
                && this.time.value.equals(((ReminderTask) other).time.value)
                && this.isAlive == (((ReminderTask) other).isAlive)
                && this.task.getName().equals(((ReminderTask) other).task.getName())
                && this.task.getStartDate().equals(((ReminderTask) other).task.getStartDate())
                && this.task.getStartTime().equals(((ReminderTask) other).task.getStartTime())
                && this.task.getEndDate().equals(((ReminderTask) other).task.getEndDate())
                && this.task.getEndTime().equals(((ReminderTask) other).task.getEndTime()));
    }
    
    class ReminderMessage extends TimerTask {
        public void run() {
            toolkit.beep();           
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
            EventsCenter.getInstance().post(new ShowReminderRequestEvent(task));
            EventsCenter.getInstance().post(new TaskPanelSelectionChangedEvent(task));
            });
            isAlive = false;
            timer.cancel();            
        }
    }
}