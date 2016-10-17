package seedu.inbx0.logic.commands;

import java.util.Set;

import seedu.inbx0.commons.exceptions.IllegalValueException;

/**
 * Finds and lists all tasks in tasklist whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " concert dance movie";

    private final Set<String> keywords;
    private final boolean andRelation;
    
    public FindCommand(boolean andRelation, Set<String> keywords) throws IllegalValueException {
        this.andRelation = andRelation;
        this.keywords = keywords;
    }

	@Override  
	public CommandResult execute() {
	    model.updateFilteredTaskList(andRelation, keywords);
	    return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
