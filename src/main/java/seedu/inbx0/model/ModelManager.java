package seedu.inbx0.model;

import javafx.collections.transformation.FilteredList;
import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.events.model.TaskListChangedEvent;
import seedu.inbx0.commons.exceptions.IllegalValueException;
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
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredNormalTasks;
    private final FilteredList<Task> filteredFloatTasks;
    private final FilteredList<Task> filteredEventTasks;
    private final FilteredList<Task> filteredDeadlineTasks;
    private final FilteredList<Task> filteredDoneTasks;
    private final FilteredList<Task> filteredToDoTasks;
    private final FilteredList<Task> filteredOverdueTasks;
    private final FilteredList<Task> filteredBeforedueTasks;
    private final FilteredList<Task> filteredDayTasks;
    private final FilteredList<Task> filteredImportanceTasks;

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
        filteredNormalTasks = new FilteredList<>(taskList.getTasks());
        filteredFloatTasks = new FilteredList<>(taskList.getTasks());
        filteredEventTasks = new FilteredList<>(taskList.getTasks());
        filteredDeadlineTasks = new FilteredList<>(taskList.getTasks());
        filteredToDoTasks = new FilteredList<>(taskList.getTasks());
        filteredDoneTasks = new FilteredList<>(taskList.getTasks());
        filteredOverdueTasks = new FilteredList<>(taskList.getTasks());
        filteredBeforedueTasks = new FilteredList<>(taskList.getTasks());
        filteredDayTasks = new FilteredList<>(taskList.getTasks());
        filteredImportanceTasks = new FilteredList<>(taskList.getTasks());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs) {
        taskList = new TaskList(initialData);
        filteredTasks = new FilteredList<>(taskList.getTasks());
        filteredNormalTasks = new FilteredList<>(taskList.getTasks());
        filteredFloatTasks = new FilteredList<>(taskList.getTasks());
        filteredEventTasks = new FilteredList<>(taskList.getTasks());
        filteredDeadlineTasks = new FilteredList<>(taskList.getTasks());
        filteredToDoTasks = new FilteredList<>(taskList.getTasks());
        filteredDoneTasks = new FilteredList<>(taskList.getTasks());
        filteredOverdueTasks = new FilteredList<>(taskList.getTasks());
        filteredBeforedueTasks = new FilteredList<>(taskList.getTasks());
        filteredDayTasks = new FilteredList<>(taskList.getTasks());
        filteredImportanceTasks = new FilteredList<>(taskList.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateTaskListChanged();
    }
    
    @Override
    public synchronized void sortTaskList(String type, boolean defaultOrder) {
        taskList.getUniqueTaskList().sortUniqueTaskList(type, defaultOrder);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

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
    
    //@@author A0139579J
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
    
    //@@author A0139579J
    @Override
    public synchronized void checkReminders() {
        if(taskList.checkReminders())
            indicateTaskListChanged();
    }
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredNormalTaskList() {
        updateFilteredNormalTaskList();
        return new UnmodifiableObservableList<>(filteredNormalTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDoneTaskList() {
        updateFilteredDoneTaskList();
        return new UnmodifiableObservableList<>(filteredDoneTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredToDoTaskList() {
        updateFilteredToDoTaskList();
        return new UnmodifiableObservableList<>(filteredToDoTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatTaskList() {
        updateFilteredFloatTaskList();
        return new UnmodifiableObservableList<>(filteredFloatTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredOverdueTaskList() {    
        updateFilteredOverdueTaskList();
        return new UnmodifiableObservableList<>(filteredOverdueTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventTaskList() {
        updateFilteredEventTaskList();
        return new UnmodifiableObservableList<>(filteredEventTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineTaskList() {
        updateFilteredDeadlineTaskList();
        return new UnmodifiableObservableList<>(filteredDeadlineTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredBeforedueTaskList() {
        updateFilteredBeforedueTaskList();
        return new UnmodifiableObservableList<>(filteredBeforedueTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDayTaskList(String day) {
        updateFilteredDayTaskList(day);
        return new UnmodifiableObservableList<>(filteredDayTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredImportanceTaskList(String importance) {
        updateFilteredImportanceTaskList(importance);
        return new UnmodifiableObservableList<>(filteredImportanceTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredTaskList(boolean logicRelation, List<String> keywords){
        if(logicRelation) {
            updateFilteredTaskList(new PredicateExpression(new LogicQualifier(keywords)));
        }
        else {
            updateFilteredTaskList(new PredicateExpression(new OrQualifier(keywords)));
        }
    }
    
    /*
     * Updates the list according to list command parameters
     */
    //@@author A0139579J
    @Override
    public void updateFilteredTaskList(String date, String preposition){
        if(preposition.equals(""))
            updateFilteredTaskList(new PredicateExpression(new StartOnAndEndOnDateQualifier(date)));
        else if(preposition.equals("overdue"))
            updateFilteredTaskList(new PredicateExpression(new OverdueTaskQualifier()));
        else
            updateFilteredTaskList(new PredicateExpression(new EndUntilDateQualifier(date)));
    }
    
    //@@author
    @Override
    public void updateFilteredNormalTaskList() {
        updateFilteredNormalTaskList(new PredicateExpression(new NormalTaskQualifier()));
    }
    
    @Override
    public void updateFilteredFloatTaskList() {
        updateFilteredFloatTaskList(new PredicateExpression(new FloatTaskQualifier()));
    }
    
    @Override
    public void updateFilteredDoneTaskList() {
        updateFilteredDoneTaskList(new PredicateExpression(new DoneTaskQualifier()));
    }
    
    @Override
    public void updateFilteredToDoTaskList() {
        updateFilteredToDoTaskList(new PredicateExpression(new ToDoTaskQualifier()));
    }    
    
    @Override
    public void updateFilteredOverdueTaskList() {
        updateFilteredOverdueTaskList(new PredicateExpression(new OverdueTaskQualifier()));
    }
    
    @Override
    public void updateFilteredBeforedueTaskList() {
        updateFilteredBeforedueTaskList(new PredicateExpression(new BeforedueTaskQualifier()));
    }
    
    @Override
    public void updateFilteredEventTaskList() {
        updateFilteredEventTaskList(new PredicateExpression(new EventTaskQualifier()));
        
    }

    @Override
    public void updateFilteredDeadlineTaskList() {
        updateFilteredDeadlineTaskList(new PredicateExpression(new DeadlineTaskQualifier()));
        
    }

    @Override
    public void updateFilteredDayTaskList(String day) {
        updateFilteredDayTaskList(new PredicateExpression(new DayTaskQualifier(day)));
        
    }

    @Override
    public void updateFilteredImportanceTaskList(String importance) {
        updateFilteredImportanceTaskList(new PredicateExpression(new ImportanceTaskQualifier(importance)));
        
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredNormalTaskList(Expression expression) {
        filteredNormalTasks.setPredicate(expression::satisfies);
    }

    private void updateFilteredFloatTaskList(Expression expression) {
        filteredFloatTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredDoneTaskList(Expression expression) {
        filteredDoneTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredToDoTaskList(Expression expression) {
        filteredToDoTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredOverdueTaskList(Expression expression) {
        filteredOverdueTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredBeforedueTaskList(Expression expression) {
        filteredBeforedueTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredEventTaskList(Expression expression) {
        filteredEventTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredDeadlineTaskList(Expression expression) {
        filteredDeadlineTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredDayTaskList(Expression expression) {
        filteredDayTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredImportanceTaskList(Expression expression) {
        filteredImportanceTasks.setPredicate(expression::satisfies);
    }

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
    /*
    private class AndQualifier implements Qualifier {
        private Set<String> andKeywords;

        AndQualifier(Set<String> andKeywords) {
            this.andKeywords = andKeywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return andKeywords.stream()
                   .allMatch(keyword -> task.getAsText().contains(keyword));
        }

        @Override
        public String toString() {
            return "AndSearchKeyword =" + String.join(", ", andKeywords);
        }
    }*/
    
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
    
    private class NormalTaskQualifier implements Qualifier {

        NormalTaskQualifier() {
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.getIsFloatTask();
        }

        @Override
        public String toString() {
            return "isNotFloatTask";
        }
    }
    
    private class FloatTaskQualifier implements Qualifier {
        
        FloatTaskQualifier() {
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getIsFloatTask();
        }
        
        @Override
        public String toString() {
            return "isFloatTask";
        }
    }
    
    private class EventTaskQualifier implements Qualifier {
        
        EventTaskQualifier() {
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getIsEvent();
        }
        
        @Override
        public String toString() {
            return "isEvent";
        }
    }    
    
    private class DeadlineTaskQualifier implements Qualifier {
        
        DeadlineTaskQualifier() {
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return !(task.getIsEvent() || task.getIsFloatTask());
        }
        
        @Override
        public String toString() {
            return "isDeadline";
        }
    }
    
    private class BeforedueTaskQualifier implements Qualifier {
        
        BeforedueTaskQualifier() {
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.getIsExpired();
        }
        
        @Override
        public String toString() {
            return "isBeforedue";
        }
    }
    
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
    
    //@@author
    private class ToDoTaskQualifier implements Qualifier {
        
        ToDoTaskQualifier() {
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return !task.getIsCompleted();
        }
        
        @Override
        public String toString() {
            return "isNotCompleted";
        }
    }
    
    private class DoneTaskQualifier implements Qualifier {
        
        DoneTaskQualifier() {
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getIsCompleted();
        }
        
        @Override
        public String toString() {
            return "isCompleted";
        }
    }
    
    private class DayTaskQualifier implements Qualifier {
        
        Date day;
        
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
        
        String importance;
        
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
    
    //@@author A0139579J
    private class StartOnAndEndOnDateQualifier implements Qualifier {
        private String date;
        
        StartOnAndEndOnDateQualifier(String date) {
            this.date = date;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return (date.equals(task.getStartDate().value) | date.equals(task.getEndDate().value));
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
             
            if(task.getEndDate().getYear() > today.getYear() |
              (task.getEndDate().getYear() == today.getYear() && task.getEndDate().getMonth() > today.getMonth()) |
              (task.getEndDate().getYear() == today.getYear() && task.getEndDate().getMonth() == today.getMonth() &&
              task.getEndDate().getDay() >= today.getDay()))       
                taskIsAfterCurrentDate = true;
            
            if((dueByYear > task.getEndDate().getYear()) |
               (dueByYear == task.getEndDate().getYear() && dueByMonth > task.getEndDate().getMonth()) |
               (dueByYear == task.getEndDate().getYear() && dueByMonth == task.getEndDate().getMonth() &&
               dueByDay >= task.getEndDate().getDay()))
                taskIsBeforeDueDate = true;
            
            if(taskIsAfterCurrentDate == true && taskIsBeforeDueDate == true)
                isBeforeOrOnDueButAfterOrOnCurrent = true;
           
            return isBeforeOrOnDueButAfterOrOnCurrent;
                   
        }
        
        //@@author
        @Override
        public String toString() {
            return "date= " + date;
        }
    }
    /*
    private class StartDateQualifier implements Qualifier {
        private Set<String> startDateKeyWords;

        StartDateQualifier(Set<String> startDateKeyWords) {
            this.startDateKeyWords = startDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return startDateKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getStartDate().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "startDate=" + String.join(", ", startDateKeyWords);
        }
    }

    private class EndDateQualifier implements Qualifier {
        private Set<String> endDateKeyWords;

        EndDateQualifier(Set<String> endDateKeyWords) {
            this.endDateKeyWords = endDateKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return endDateKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getEndDate().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "endDate=" + String.join(", ", endDateKeyWords);
        }
    }
    
    private class LevelQualifier implements Qualifier {
        private Set<String> levelKeyWords;

        LevelQualifier(Set<String> levelKeyWords) {
            this.levelKeyWords = levelKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return levelKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getLevel().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "level=" + String.join(", ", levelKeyWords);
        }
    }

    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;

        TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return tagKeyWords.stream()
                    .filter(keyword -> task.tagsString().contains(keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "tag=" + String.join(", ", tagKeyWords);
        }
    }*/

    


}
