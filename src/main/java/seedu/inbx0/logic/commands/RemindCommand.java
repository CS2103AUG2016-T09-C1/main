package seedu.inbx0.logic.commands;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
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
            + "Parameters: INDEX s/[START DATE] [START TIME] ( Index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 s=5 minutes from now";

    public static final String MESSAGE_REMINDER_TASK_SUCCESS = "Added Reminder for Task: %1$s";
    public static final int TOTAL_NUMBER_OF_ARGUMENTS = 6;
    
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
    
    private Task createToEditWithTask(String[] editArguments, UniqueTagList tags, UniqueReminderList reminders) throws IllegalValueException {
        Task toEditWith = new Task (
                new Name(editArguments[0]),
                new Date(editArguments[1]),
                new Time(editArguments[2]),
                new Date(editArguments[3]),
                new Time(editArguments[4]),
                new Importance(editArguments[5]),
                tags,
                reminders
                );
        return toEditWith;
    }
    
    private String[] obtainArguments(String[] editArguments, ReadOnlyTask taskToEdit) {
        
        String [] originalArguments = new String[6];
                    
        originalArguments[0] = taskToEdit.getName().getName();
        originalArguments[1] = taskToEdit.getStartDate().getDate();
        originalArguments[2] = taskToEdit.getStartTime().getTime();
        originalArguments[3] = taskToEdit.getEndDate().getDate();
        originalArguments[4] = taskToEdit.getEndTime().getTime();
        originalArguments[5] = taskToEdit.getLevel().getLevel();
            
        for(int i = 0; i < TOTAL_NUMBER_OF_ARGUMENTS; i++) {
            if(editArguments[i] == null)
                editArguments[i] = originalArguments[i];                       
        }
        
        return editArguments;
    }
    
    private UniqueTagList obtainUniqueTagList(ReadOnlyTask taskToEdit) {
        
        UniqueTagList original = taskToEdit.getTags();
              
        return original;
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
        return false;
    }

}