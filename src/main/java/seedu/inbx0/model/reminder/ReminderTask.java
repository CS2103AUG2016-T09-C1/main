package seedu.inbx0.model.reminder;

import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.events.ui.ShowHelpRequestEvent;
import seedu.inbx0.commons.events.ui.ShowReminderRequestEvent;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.logic.commands.Command;
import seedu.inbx0.logic.commands.RemindCommand;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.Time;

public class ReminderTask {
    
    Toolkit toolkit;
    Timer timer;
    Date date;
    Time time;
    ReadOnlyTask task;
    boolean isAlive;
    
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
        
        if(currentDate.value.equals(date.value)) {
            if(!("").equals(time.getTime())) {
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
            else
            {
                timer = new Timer();
                timer.schedule(new ReminderMessage(), 1);
            }
        }
    }
    
    /**
     * Copy constructor.
     * @throws IllegalValueException if not valid
     */

    public ReminderTask(final ReminderTask source) throws IllegalValueException {
        this(source.getStartDate(), source.getStartTime(),source.getReadableOnlyTask(), source.getIsAlive());
    }
    
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
    
    public void setReadableOnlyTask(ReadOnlyTask task) {
       this.task = task;
    }
    
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Reminder")
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" Start Time: ")
                .append(getStartTime());
        return builder.toString();
    }
    
    class ReminderMessage extends TimerTask {
        public void run() {
            toolkit.beep();
//            EventsCenter.getInstance().post(new ShowReminderRequestEvent());
            System.out.println("Here's a reminder!");
            System.out.println(task.getName());
            System.out.println("Start Date: " + task.getStartDate());
            System.out.println("Start Time: " + task.getStartTime());
            System.out.println("End Date: " + task.getEndDate());
            System.out.println("End Time: " + task.getEndTime());
            isAlive = false;
            timer.cancel();
            
        }
    }
}
