package seedu.inbx0.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.inbx0.commons.exceptions.IllegalValueException;

/**
 * Finds and lists all tasks in tasklist whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks accordingly to restriction defined by"
            + "the specified keywords (non case-sensitive) and displays them as a list with index numbers.\n"
            + "allow short cut that use in add command like i/G (importance: green)\n"
            + "1. Normal search (displays task as long as it contains any of the keywords)\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meeting i/g Red \n"
            + "2. Logic serach (displays task only when it fits the logic expression)\n" 
            + "LOGIC OPERATOR: | means OR, & means AND, () are for grouping logic operation\n" 
            + "Parameters: [LOGIC_OPERATOR] KEYWORD [MORE_KEYWORDS] [MORE_LOGIC_OPERATOR]...\n"
            + "Example: " + COMMAND_WORD + " (homework | assignment) & s/tmr & i/r";

    public static final String MESSAGE_BRACKET_USAGE = "Bracket must be closed properly";

    public static final String INVALID_LOGIC_SEARCH = "Input format must match normal logic operation format.\n" 
            + "Parameters: [LOGIC_OPERATOR] KEYWORD [MORE_KEYWORDS] [MORE_LOGIC_OPERATOR]...\n"
            + "Example: " + COMMAND_WORD + " (homework | assignment) & s/tmr & i/r";
    
    private final List<String> keywords;
    private final boolean logicRelation;
    
    public FindCommand(boolean logicRelation, List<String> keywordSet) throws IllegalValueException {
        this.logicRelation = logicRelation;
        this.keywords = keywordSet;
    }

	@Override  
	public CommandResult execute() {
	    System.out.println("execute");
	    model.updateFilteredTaskList(logicRelation, keywords);
	    return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
