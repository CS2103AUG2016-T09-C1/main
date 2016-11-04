package seedu.inbx0.logic;

import javafx.collections.ObservableList;
import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.logic.commands.Command;
import seedu.inbx0.logic.commands.CommandResult;
import seedu.inbx0.logic.internalcommands.ExpiredCommand;
import seedu.inbx0.logic.internalcommands.RemoveReminder;
import seedu.inbx0.logic.parser.Parser;
import seedu.inbx0.model.Model;
import seedu.inbx0.model.task.ReadOnlyTask;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private Stack<String> previousCommandText = new Stack<String> ();
    private Stack<String> nextCommandText = new Stack<String> ();

    public LogicManager(Model model) {
        this.model = model;
        this.parser = new Parser();
    }
    
    //@@author A0148044J
    public Stack<String> getPreviousCommandText() {
        return previousCommandText;
    }

    public Stack<String> getNextCommandText() {
        return nextCommandText;
    }
    
    public String popPreviousCommandText() {
        return previousCommandText.pop();
    }

    public String popNextCommandText() {
        return nextCommandText.pop();
    }
    
    public void setPreviousCommandText(String commandText) {
        previousCommandText.push(commandText);
    }
    
    public void setNextCommandText(String commandText) {
        nextCommandText.push(commandText);
    }
  //@@author
    
    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        previousCommandText.push(commandText);
        command.setData(model);
        if(command.canUndo())
            model.saveTaskListHistory();
        return command.execute();
    }
    
    @Override
    public CommandResult executeCheckExpiry() {
        Command command = new ExpiredCommand();
        command.setData(model);
        return command.execute();
    }
    
    @Override
    public CommandResult removeReminders() {
        Command command = new RemoveReminder();
        command.setData(model);
        return command.execute();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    //@@author A0148044J
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskListByCategory(String category) {
        return model.getFilteredTaskListByCategory(category);
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskListByCompleteness(boolean isComplete) {
        return model.getFilteredTaskListByCompleteness(isComplete);
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskListByExpiry(boolean isExpired) {
        return model.getFilteredTaskListByExpiry(isExpired);
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskListByDay(String day) {
        return model.getFilteredTaskListByDay(day);
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskListByImportance(String importance) {
        return model.getFilteredTaskListByImportance(importance);
    }
    //@@author
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredOverdueTaskList() {
        return model.getFilteredOverdueTaskList();
    }
}
