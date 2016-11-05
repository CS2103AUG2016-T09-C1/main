package seedu.inbx0.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.inbx0.commons.core.Messages;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Importance;
import seedu.inbx0.model.task.Name;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Time;
import seedu.inbx0.model.task.UniqueTaskList;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Edits a task identified using it's last displayed index from the tasklist.
 */
//@@author A0139579J
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) [n=NAME] [s=STARTDATE] [st=STARTTIME] \n"
            + "[e=ENDDATE] [et=ENDTIME] [i=IMPORTANCE] [t=TAG]... (one optional parameter is required)\n"
            + "Example: " + COMMAND_WORD + " 1 s=tomorrow st=9a e=next week et=9a i=r t=CS2103 t=project \n \n"
            + "You can also change an event or a deadline task to a floating task \n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 float \n";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the tasklist";
    public static final String MESSAGE_INVALID_ARGUMENTS = "The arguments provided are invalid.";
    public static final int TOTAL_NUMBER_OF_ARGUMENTS = 6;
    public static final int TASK_NAME = 0;
    public static final int TASK_START_DATE = 1;
    public static final int TASK_START_TIME = 2;
    public static final int TASK_END_DATE = 3;
    public static final int TASK_END_TIME = 4;
    public static final int TASK_IMPORTANCE = 5;
    
    public final int targetIndex;
    public String [] editArguments;
    public UniqueTagList tags;

    public EditCommand(int targetIndex, String [] argumentsToEdit, Set<String> tags) throws IllegalValueException {
        
        this.targetIndex = targetIndex;
        
        if(tags == null) {
            this.tags = null;
        }
        else {
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }
            
            UniqueTagList editedTags = null;
            
            if(!tagSet.isEmpty()) {
                editedTags = new UniqueTagList(tagSet);
            }
         
            this.tags = editedTags;
        }
        this.editArguments = argumentsToEdit;    
    }


    private UniqueTagList obtainUniqueTagList(ReadOnlyTask taskToEdit) {
        
      UniqueTagList original = taskToEdit.getTags();
            
      return original;
    }
    
    private UniqueReminderList obtainUniqueReminderList(ReadOnlyTask taskToEdit) {
        
        UniqueReminderList original = taskToEdit.getReminders();
              
        return original;
    }
    
    private Task updateReminders(Task toEditWith) {
        Task newTask = toEditWith;
        UniqueReminderList reminders = toEditWith.getReminders();
        for(ReminderTask r: reminders) {
            r.setReadableOnlyTask(newTask);
        }
        toEditWith.setReminders(reminders);
        Task newUpdatedTask = toEditWith;
        
        return newUpdatedTask;
        
    }


    private String[] obtainArguments(String[] editArguments, ReadOnlyTask taskToEdit) {
       
        String [] originalArguments = new String[TOTAL_NUMBER_OF_ARGUMENTS];
                    
        originalArguments[TASK_NAME] = taskToEdit.getName().getName();
        originalArguments[TASK_START_DATE] = taskToEdit.getStartDate().getDate();
        originalArguments[TASK_START_TIME] = taskToEdit.getStartTime().getTime();
        originalArguments[TASK_END_DATE] = taskToEdit.getEndDate().getDate();
        originalArguments[TASK_END_TIME] = taskToEdit.getEndTime().getTime();
        originalArguments[TASK_IMPORTANCE] = taskToEdit.getLevel().getLevel();
            
        for(int i = 0; i < TOTAL_NUMBER_OF_ARGUMENTS; i++) {
            if(editArguments[i] == null)
                editArguments[i] = originalArguments[i];                       
        }
        
        return editArguments;
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

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        this.editArguments = obtainArguments(editArguments, taskToEdit);
        
        if(tags == null)
            this.tags = obtainUniqueTagList(taskToEdit);
        
        UniqueReminderList reminders = obtainUniqueReminderList(taskToEdit);
        
        Task toEditWith = null;
        try {
            toEditWith = createToEditWithTask(editArguments, tags, reminders);
        } catch (IllegalValueException e1) {
            return new CommandResult(String.format(MESSAGE_INVALID_ARGUMENTS, MESSAGE_USAGE));
        }
        
        Task updatedReminders = updateReminders(toEditWith);
        
        try {
            model.editTask(taskToEdit, updatedReminders);
            indicateJumpToListRequestEvent(model.getTaskList().getTaskList().indexOf(taskToEdit));
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e ) {    
            return new CommandResult(MESSAGE_DUPLICATE_TASK);            
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEditWith));
    }


    @Override
    public boolean canUndo() {
        return true;
    }
}
