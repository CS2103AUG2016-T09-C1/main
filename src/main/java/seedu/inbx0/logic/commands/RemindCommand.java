package seedu.inbx0.logic.commands;

import java.text.SimpleDateFormat;
import java.util.List;

import com.joestelmach.natty.Parser;

import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.reminder.UniqueReminderList.DuplicateReminderException;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Importance;
import seedu.inbx0.model.task.Name;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Time;
import seedu.inbx0.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Adds a reminder for the task identified using it's last displayed index from the address book.
 */
//@@author A0139579J
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "rem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a reminder for the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX s=[START DATE] [START TIME] ( Index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 s=5 minutes from now";

    public static final String MESSAGE_REMINDER_TASK_SUCCESS = "Added Reminder for Task: %1$s";
    public static final String MESSAGE_REMINDER_CONSTRAINTS = "The reminder date or time is invalid.";
    public static final int TOTAL_NUMBER_OF_ARGUMENTS = 6;
    private static final int TASK_NAME = 0;
    private static final int TASK_START_DATE = 1;
    private static final int TASK_START_TIME = 2;
    private static final int TASK_END_DATE = 3;
    private static final int TASK_END_TIME = 4;
    private static final int TASK_IMPORTANCE = 5;
    
    public final int targetIndex;
    public final Date startDate;
    public final Time startTime;

    public RemindCommand(int targetIndex, String date) {
        this.targetIndex = targetIndex;
        
        Date startDate = null;
        Time startTime = null;
        try {
            startDate = new Date(date);
            startTime = new Time(date);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        
        this.startDate = startDate;
        this.startTime = startTime;
    }
    
    private boolean isValidDateAndTime(Date date, Time time) {
        boolean isValidDate = false;
        boolean isValidTime = false;
        
        Date currentDate = null;
        try {
          currentDate = new Date("now");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        
        SimpleDateFormat ft = new SimpleDateFormat ("HHmm");
        List<java.util.Date> current = new Parser().parse("now").get(0).getDates();
        int currentTime = Integer.parseInt(ft.format(current.get(0)));
        
        int currentHour = currentTime / 100;
        int currentMin = currentTime % 100;
        
        if (time == null) {
            return false;
        }
        if (("").equals(time.value)) {
            isValidTime = true;
        } else {
            
            int reminderTime = Integer.parseInt(time.getTime().replaceAll("\\D+",""));
            int reminderHour = reminderTime / 100;
            int reminderMin = reminderTime % 100;
            
            if((reminderHour > currentHour) |
               (reminderHour == currentHour && reminderMin > currentMin))
                isValidTime = true;         
        }
        
        int currentDay = currentDate.getDay();
        int currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();
        
        int reminderDay = date.getDay();
        int reminderMonth = date.getMonth();
        int reminderYear = date.getYear();
        
        if ((reminderYear > currentYear) |
           (reminderYear == currentYear && reminderMonth > currentMonth) |
           (reminderYear == currentYear && reminderMonth == currentMonth && reminderDay >= currentDay))
            isValidDate = true;
        
        return (isValidDate && isValidTime);
    }
    
    private Task createToEditWithTask(String[] editArguments, UniqueTagList tags, UniqueReminderList reminders) throws IllegalValueException {
        Task toEditWith = new Task (
                new Name(editArguments[TASK_NAME]),
                new Date(editArguments[TASK_START_DATE]),
                new Time(editArguments[TASK_START_TIME]),
                new Date(editArguments[TASK_END_DATE]),
                new Time(editArguments[TASK_END_TIME]),
                new Importance(editArguments[TASK_IMPORTANCE]),
                tags,
                reminders
                );
        return toEditWith;
    }
    
    private String[] obtainArguments(String[] editArguments, ReadOnlyTask taskToEdit) {
        
        String [] originalArguments = new String[TOTAL_NUMBER_OF_ARGUMENTS];
                    
        originalArguments[TASK_NAME] = taskToEdit.getName().getName();
        originalArguments[TASK_START_DATE] = taskToEdit.getStartDate().getDate();
        originalArguments[TASK_START_TIME] = taskToEdit.getStartTime().getTime();
        originalArguments[TASK_END_DATE] = taskToEdit.getEndDate().getDate();
        originalArguments[TASK_END_TIME] = taskToEdit.getEndTime().getTime();
        originalArguments[TASK_IMPORTANCE] = taskToEdit.getLevel().getLevel();
            
        for (int i = 0; i < TOTAL_NUMBER_OF_ARGUMENTS; i++) {
            if (editArguments[i] == null)
                editArguments[i] = originalArguments[i];                       
        }
        
        return editArguments;
    }
    
    private UniqueTagList obtainUniqueTagList(ReadOnlyTask taskToEdit) {
        
        UniqueTagList originalTagList = taskToEdit.getTags();
              
        return originalTagList;
      }
      
      private UniqueReminderList obtainUniqueReminderList(ReadOnlyTask taskToEdit, ReminderTask reminder) {
          UniqueReminderList original = taskToEdit.getReminders();
          try {
            original.add(reminder);
        } catch (DuplicateReminderException e) {

            e.printStackTrace();
        }
          return original;
      }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (!isValidDateAndTime(startDate, startTime)) {
            return new CommandResult(MESSAGE_REMINDER_CONSTRAINTS);
        }
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToAddReminder = lastShownList.get(targetIndex - 1);
        
        ReminderTask newReminder = new ReminderTask(startDate, startTime, taskToAddReminder, true);
        String [] editArguments = new String [6];
        editArguments = obtainArguments(editArguments, taskToAddReminder);
        UniqueTagList tags = obtainUniqueTagList(taskToAddReminder);
        UniqueReminderList reminders = obtainUniqueReminderList(taskToAddReminder, newReminder);
        
        Task withReminder = null;
        try {
            withReminder = createToEditWithTask(editArguments, tags, reminders);
            EventsCenter.getInstance().post(new TaskPanelSelectionChangedEvent(withReminder));
        } catch (IllegalValueException e1) {
            return new CommandResult(MESSAGE_USAGE);
        }
        try {
            model.editTask(taskToAddReminder, withReminder);
        } catch (DuplicateTaskException | TaskNotFoundException e) {
            
            e.printStackTrace();
        } 

        return new CommandResult(String.format(MESSAGE_REMINDER_TASK_SUCCESS, newReminder));
    }

    @Override
    public boolean canUndo() {
        return true;
    }
}