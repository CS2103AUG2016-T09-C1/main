package seedu.inbx0.model;

import javafx.collections.transformation.FilteredList;
import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.core.UnmodifiableObservableList;
import seedu.inbx0.commons.events.model.TaskListChangedEvent;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Time;
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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Represents the in-memory model of the task list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given TaskList
     * TaskList and its variables should not be null
     */
    public ModelManager(TaskList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskList = new TaskList(src);
        filteredTasks = new FilteredList<>(taskList.getTasks());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs) {
        taskList = new TaskList(initialData);
        filteredTasks = new FilteredList<>(taskList.getTasks());
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
    
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    
    @Override
    public synchronized void checkExpiry(Date currentDate, String currentTime) {
        taskList.checkExpiry(currentDate, currentTime);
        indicateTaskListChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredTaskList(boolean logicRelation, List<String> keywords){
        if(logicRelation == true) {
            updateFilteredTaskList(new PredicateExpression(new LogicQualifier(keywords)));
        }
        else {
            updateFilteredTaskList(new PredicateExpression(new OrQualifier(keywords)));
        }
    }
    
    @Override
    public void updateFilteredTaskList(String date, String preposition){
        System.out.println(preposition);
        if(preposition == "")
            updateFilteredTaskList(new PredicateExpression(new StartOnAndEndOnDateQualifier(date)));
        else
            updateFilteredTaskList(new PredicateExpression(new EndUntilDateQualifier(date)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");

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
                else if(keyword.equals("&")) {
                    logicKeywords.add("&&");
                }
                else if(keyword.equals("|")) {
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
    /*
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getAsText(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }*/
    
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
         
            if(dueByYear > task.getEndDate().getYear() && task.getEndDate().getYear() > today.getYear()) 
                isBeforeOrOnDueButAfterOrOnCurrent = true;
            
            
            if(dueByYear == task.getEndDate().getYear() && task.getEndDate().getYear() == today.getYear() &&
               dueByMonth > task.getEndDate().getMonth() && task.getEndDate().getMonth() > today.getMonth())
                isBeforeOrOnDueButAfterOrOnCurrent = true;
            
            if(dueByYear == task.getEndDate().getYear() && task.getEndDate().getYear() == today.getYear() && 
               dueByMonth == task.getEndDate().getMonth() && task.getEndDate().getMonth() == today.getMonth() &&
               dueByDay >= task.getEndDate().getDay() && task.getEndDate().getDay() >= today.getDay())
                isBeforeOrOnDueButAfterOrOnCurrent = true;
            
            return isBeforeOrOnDueButAfterOrOnCurrent;
                   
        }
        
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
