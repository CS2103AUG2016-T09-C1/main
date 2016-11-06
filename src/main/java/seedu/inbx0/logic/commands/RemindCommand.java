package seedu.inbx0.logic.commands;

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

//@@author A0139579J
/**
 * Adds a reminder for the task identified using it's last displayed index from the address book.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "rem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a reminder for the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX s=[START DATE] [START TIME] ( Index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 s=5 minutes from now";

    public static final String MESSAGE_REMINDER_TASK_SUCCESS = "Added Reminder for Task: %1$s";
    public static final String MESSAGE_REMINDER_CONSTRAINTS = "The reminder date or time is invalid.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "There is already a reminder during that time.";
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
        Date currentDate = null;
        try {
          currentDate = new Date("now");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        
        if(currentDate.getDate().equals(date.getDate())) {
           return isValidTime(time);
        } else
           return isValidDate(currentDate, date);      
    }

    private boolean isValidTime(Time time) {
        boolean timeCheck = false;
        String current = Time.getCurrentTime();
        int currentTime = Integer.parseInt(current.replaceAll("\\D+",""));
        
        int currentHour = currentTime / 100;
        int currentMin = currentTime % 100;
        
        if (time == null) {
            timeCheck = false;
        }
        if (("").equals(time.value)) {
            timeCheck = true;
        } else {
            
            int reminderTime = Integer.parseInt(time.getTime().replaceAll("\\D+",""));
            int reminderHour = reminderTime / 100;
            int reminderMin = reminderTime % 100;
            
            if((reminderHour > currentHour) |
               (reminderHour == currentHour && reminderMin > currentMin))
                timeCheck = true;       
        }       
       return timeCheck; 
    }
    
    private boolean isValidDate(Date currentDate, Date date) {
        boolean dateCheck = false;
        int currentDay = currentDate.getDay();
        int currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();
        
        int reminderDay = date.getDay();
        int reminderMonth = date.getMonth();
        int reminderYear = date.getYear();
        
        if ((reminderYear > currentYear) |
           (reminderYear == currentYear && reminderMonth > currentMonth) |
           (reminderYear == currentYear && reminderMonth == currentMonth && reminderDay > currentDay)) 
            dateCheck = true;
        return dateCheck;
    }
    
    private Task createTaskWithReminder(ReadOnlyTask taskToAddReminder, ReminderTask newReminder) throws IllegalValueException {
        String [] arguments = new String [TOTAL_NUMBER_OF_ARGUMENTS];
        arguments = obtainArguments(arguments, taskToAddReminder);
        UniqueTagList tags = obtainUniqueTagList(taskToAddReminder);
        UniqueReminderList reminders = addReminder(taskToAddReminder, newReminder);
        
        Task taskWithReminder = new Task (
            new Name(arguments[TASK_NAME]),
            new Date(arguments[TASK_START_DATE]),
            new Time(arguments[TASK_START_TIME]),
            new Date(arguments[TASK_END_DATE]),
            new Time(arguments[TASK_END_TIME]),
            new Importance(arguments[TASK_IMPORTANCE]),
            tags,
            reminders
            );
        return taskWithReminder;
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
      
    private UniqueReminderList addReminder(ReadOnlyTask taskToAddReminder, ReminderTask newReminder) throws DuplicateReminderException {
        UniqueReminderList originalReminderList = taskToAddReminder.getReminders();
        originalReminderList.add(newReminder);
        return originalReminderList;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (!isValidDateAndTime(startDate, startTime)) 
            return new CommandResult(MESSAGE_REMINDER_CONSTRAINTS);
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToAddReminder = lastShownList.get(targetIndex - 1);
        
        ReminderTask newReminder = new ReminderTask(startDate, startTime, taskToAddReminder, false);
  
        Task withReminder = null;
        try {
            withReminder = createTaskWithReminder(taskToAddReminder, newReminder);
            EventsCenter.getInstance().post(new TaskPanelSelectionChangedEvent(withReminder));
        } catch (DuplicateReminderException d) {
            newReminder.cancel();
            return new CommandResult(MESSAGE_DUPLICATE_REMINDER);
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