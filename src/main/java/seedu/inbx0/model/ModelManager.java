package seedu.inbx0.model;

import javafx.collections.transformation.FilteredList;
import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.events.model.TaskListChangedEvent;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.history.HistoryList;
import seedu.inbx0.model.history.HistoryList.EmptyHistoryException;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.UniqueTaskList;
import seedu.inbx0.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.inbx0.model.task.UniqueTaskList.TaskNotFoundException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredOverdueTasks;
    private HistoryList<TaskList> taskListHistory;

    /**
     * Initializes a ModelManager with the given TaskList
     * TaskList and its variables should not be null
     */
    public ModelManager(TaskList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task list: " + src + " and user prefs " + userPrefs);

        taskList = new TaskList(src);
        filteredTasks = new FilteredList<>(taskList.getTasks());
        filteredOverdueTasks = new FilteredList<>(taskList.getTasks());
        taskListHistory = new HistoryList<TaskList>();
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs) {
        taskList = new TaskList(initialData);
        filteredTasks = new FilteredList<>(taskList.getTasks());
        filteredOverdueTasks = new FilteredList<>(taskList.getTasks());
        taskListHistory = new HistoryList<TaskList>();
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
//        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    
    @Override
    public synchronized void sortTaskList(String type, boolean defaultOrder) {
        taskList.getUniqueTaskList().sortUniqueTaskList(type, defaultOrder);
//        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }
    
    //@@author A0139481Y
    @Override
    public void saveTaskListHistory() {
        taskListHistory.pushState(taskList);
    }
    
    @Override
    public int undoTaskListHistory(int stepsBack) {
        assert stepsBack > 0;
        
        int numUndone = 0;
        TaskList historyList = null;
        try {
            for (int i = 0; i < stepsBack; i++) {
                historyList = taskListHistory.popState();
                numUndone++;
            }
        } catch (EmptyHistoryException e) {
            logger.fine(e.getMessage());
        }
        
        if (historyList != null) {
            resetData(historyList);
        }
        
        return numUndone;
    }
    //@@author

    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        indicateTaskListChanged();
    }
    
    //@@author A0139579J
    @Override
    public synchronized void editTask(ReadOnlyTask target, Task task) throws TaskNotFoundException, DuplicateTaskException {
        taskList.editTask(target, task);
        indicateTaskListChanged();
    }
    
    @Override
    public synchronized void markTaskComplete(ReadOnlyTask target, Task task) throws TaskNotFoundException {
        taskList.markTaskComplete(target, task);
        indicateTaskListChanged();
    }
    
    //@@author
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    
    //@@author A0139579J
    @Override
    public synchronized void checkExpiry(Date currentDate, String currentTime) {
        if(taskList.checkExpiry(currentDate, currentTime))
            indicateTaskListChanged();
    }

    @Override
    public synchronized void checkReminders() {
        if(taskList.checkReminders())
            indicateTaskListChanged();
    }
    //@@author
    //=========== Filtered Task List Accessors ===============================================================
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    //@@author A0139579J
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredOverdueTaskList() {    
        updateFilteredOverdueTaskList();
        return new UnmodifiableObservableList<>(filteredOverdueTasks);
    }
    //@@author
    
    //@@author A0148044J
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByCategory(String category) {
        updateFilteredTaskListByCategory(category);
        return getFilteredTaskList();
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByExpiry(boolean isExpired) {
        updateFilteredTaskListByExpiry(isExpired);
        return getFilteredTaskList();
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByDay(String day) {
        updateFilteredTaskListByDay(day);
        return getFilteredTaskList();
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByImportance(String importance) {
        updateFilteredTaskListByImportance(importance);
        return getFilteredTaskList();
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskListByCompleteness(boolean isComplete) {
        updateFilteredTaskListByCompleteness(isComplete);
        return getFilteredTaskList();
    }
    //@@author
    
    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
    /*
     * Updates the list according to list command parameters
     */
    //@@author A0139579J
    @Override
    public void updateFilteredTaskList(String date, String preposition){
        if("".equals(preposition))
            updateFilteredTaskList(new PredicateExpression(new StartOnAndEndOnDateQualifier(date)));
        else if("overdue".equals(preposition))
            updateFilteredTaskList(new PredicateExpression(new OverdueTaskQualifier()));
        else
            updateFilteredTaskList(new PredicateExpression(new EndUntilDateQualifier(date)));
    }
        
    //@@author A0139579J
    @Override
    public void updateFilteredOverdueTaskList() {
        updateFilteredOverdueTaskList(new PredicateExpression(new OverdueTaskQualifier()));
    }

	//@@author A0148044J
    /*
     * Updates the list according to show command with parameters under category titledpane
     */
    @Override
    public void updateFilteredTaskListByCategory(String category) {
        updateFilteredTaskList(new PredicateExpression(new CategoryTaskQualifier(category)));
    }
    
    /*
     * Updates the list according to show command with parameters under completeness titledpane
     */
    @Override
    public void updateFilteredTaskListByCompleteness(boolean isCompleted) {
        updateFilteredTaskList(new PredicateExpression(new CompletenessTaskQualifier(isCompleted)));
    }
    
    /*
     * Updates the list according to show command with parameters under expiry titledpane
     */
    @Override
    public void updateFilteredTaskListByExpiry(boolean isExpired) {
        updateFilteredTaskList(new PredicateExpression(new ExpiryTaskQualifier(isExpired)));
    }
    
    /*
     * Updates the list according to show command with parameters under day titledpane
     */
    @Override
    public void updateFilteredTaskListByDay(String day) {
        updateFilteredTaskList(new PredicateExpression(new DayTaskQualifier(day)));        
    }
    
    /*
     * Updates the list according to show command with parameters under importance titledpane
     */
    @Override
    public void updateFilteredTaskListByImportance(String importance) {
        updateFilteredTaskList(new PredicateExpression(new ImportanceTaskQualifier(importance)));       
    }
    
    /*
     * Updates the list according to find command with keywords
     */
    @Override
    public void updateFilteredTaskList(boolean logicRelation, List<String> keywords){
        if(logicRelation) {
            updateFilteredTaskList(new PredicateExpression(new LogicQualifier(keywords)));
        }
        else {
            updateFilteredTaskList(new PredicateExpression(new OrQualifier(keywords)));
        }
    }
    //@@author
 
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredOverdueTaskList(Expression expression) {
    	filteredOverdueTasks.setPredicate(expression::satisfies);
	}
    
    //@@author 
    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }
   
    //@@author A0148044J
    private class OrQualifier implements Qualifier {
        private List<String> orKeywords;

        OrQualifier(List<String> keywords) {
            this.orKeywords = keywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return orKeywords.stream()
                    .filter(keyword -> task.getAsTextSet().contains(keyword.toLowerCase()))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "OrSearchKeywords =" + String.join(", ", orKeywords);
        }
    }
    
    private class LogicQualifier implements Qualifier {
        private List<String> copyKeywords;
        private List<String> logicKeywords;
        private ScriptEngineManager sem = new ScriptEngineManager();
        private ScriptEngine se = sem.getEngineByName("JavaScript");

        LogicQualifier(List<String> keywords) {
            this.copyKeywords = keywords;
        }
        
        
        @Override
        public boolean run(ReadOnlyTask task) {
            logicKeywords = new ArrayList<String>();
            for(String keyword: copyKeywords) {
                if(!keyword.matches("[(&|)]")){
                    if(task.getAsTextSet().contains(keyword.toLowerCase())) {
                        logicKeywords.add("true");
                    }
                    else {
                        logicKeywords.add("false");
                    }
                }
                else if("&".equals(keyword)) {
                    logicKeywords.add("&&");
                }
                else if("|".equals(keyword)) {
                    logicKeywords.add("||");
                }
                else {
                    logicKeywords.add(keyword);
                }
            }
            String logicExpression = String.join(" ", logicKeywords);
            boolean result = false;
            try {
                result = (boolean) se.eval(logicExpression);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            return result;       
        }

        @Override
        public String toString() {
            return "LogicSearchKeyword =" + String.join(", ", logicKeywords);
        }
    }
    
    private class CategoryTaskQualifier implements Qualifier {
    	private String category;
    	CategoryTaskQualifier(String category) {
    		this.category = category;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	if ("Floating".equals(category)) {
        		return task.getIsFloatTask();
        	} else if ("Event".equals(category)) {
        		return task.getIsEvent();
        	} else if ("Deadline".equals(category)) {
        		return !task.getIsEvent() && !task.getIsEvent();
        	}
        	return false;
        }

        @Override
        public String toString() {
            return "Category=" + category;
        }
    }
    
    private class ExpiryTaskQualifier implements Qualifier {
        boolean isExpired;
        ExpiryTaskQualifier(boolean isExpired) {
        	this.isExpired = isExpired;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
        	if (isExpired) {
        		return task.getIsExpired();
        	}
        	return !task.getIsExpired();
        }
        
        @Override
        public String toString() {
            return "Expiry of the task: " + isExpired;
        }
    }
    
    private class CompletenessTaskQualifier implements Qualifier {
        private boolean isComplete;
        CompletenessTaskQualifier(boolean isComplete) {
        	this.isComplete = isComplete;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
        	if(isComplete) {
        		return task.getIsCompleted();
        	}
            return !task.getIsCompleted();
        }
        
        @Override
        public String toString() {
            return "Completeness of the task: " + isComplete;
        }
    }
    
    private class DayTaskQualifier implements Qualifier {
        
        private Date day;
        
        DayTaskQualifier(String day)  {
            try {
                this.day = new Date(day);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getStartDate().getDate().equals(day.getDate());
        }
        
        @Override
        public String toString() {
            return "Date: " + day.getDate();
        }
    }
    
    private class ImportanceTaskQualifier implements Qualifier {
        
        private String importance;
        
        ImportanceTaskQualifier(String importance)  {
            this.importance = importance;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            System.out.println("task: " + task.getLevel().getLevel());
            System.out.println("importance: " + importance);
            return task.getLevel().getLevel().equals(this.importance);
        }
        
        @Override
        public String toString() {
            return "Importance: " + this.importance;
        }
    }
    //@@author
    
    //@@author A0139579J
    private class OverdueTaskQualifier implements Qualifier {
        
        OverdueTaskQualifier() {
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return (task.getIsExpired() && !task.getIsCompleted() &&
                    task.getStartDate().value.equals("") && task.getStartTime().value.equals("") &&
                    !task.getEndDate().equals("") && !task.getEndDate().equals("")) | 
                    (task.getIsExpired() && !task.getIsCompleted() &&
                     task.getStartDate().value.equals("") && task.getStartTime().value.equals("") &&
                     !task.getEndDate().equals("") && task.getEndDate().equals(""));
        }
        
        @Override
        public String toString() {
            return "isExpired";
        }
    }
    
    //@@author A0139579J
    private class StartOnAndEndOnDateQualifier implements Qualifier {
        private String date;
        
        StartOnAndEndOnDateQualifier(String date) {
            this.date = date;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return (date.equals(task.getStartDate().value) | date.equals(task.getEndDate().value)) && !task.getIsCompleted();
        }
        
        @Override
        public String toString() {
            return "date= " + date;
        }
    }
    
    private class EndUntilDateQualifier implements Qualifier {
        private String date;
        
        EndUntilDateQualifier(String date) {
            this.date = date;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            Date today = null;
           
            boolean taskIsAfterCurrentDate = false;
            boolean taskIsBeforeDueDate = false;
            boolean isBeforeOrOnDueButAfterOrOnCurrent = false;
            try {
                today = new Date("today");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            
            int dueByNumberDate = Integer.parseInt(date.replaceAll("\\D+",""));
            int dueByDay = dueByNumberDate / 1000000;
            int dueByMonth = (dueByNumberDate / 10000) % 100;
            int dueByYear = dueByNumberDate % 10000;
             
            if (task.getEndDate().getYear() > today.getYear() | 
               (task.getEndDate().getYear() == today.getYear() && task.getEndDate().getMonth() > today.getMonth()) |
               (task.getEndDate().getYear() == today.getYear() && task.getEndDate().getMonth() == today.getMonth() &&
               task.getEndDate().getDay() >= today.getDay()))       
                taskIsAfterCurrentDate = true;
            
            if ((dueByYear > task.getEndDate().getYear()) |
               (dueByYear == task.getEndDate().getYear() && dueByMonth > task.getEndDate().getMonth()) |
               (dueByYear == task.getEndDate().getYear() && dueByMonth == task.getEndDate().getMonth() &&
               dueByDay >= task.getEndDate().getDay()))
                taskIsBeforeDueDate = true;
            
            if (taskIsAfterCurrentDate && taskIsBeforeDueDate)
                isBeforeOrOnDueButAfterOrOnCurrent = true;
           
            return isBeforeOrOnDueButAfterOrOnCurrent && !task.getIsCompleted();
                   
        }
        
        //@@author
        @Override
        public String toString() {
            return "date= " + date;
        }
    }
}
