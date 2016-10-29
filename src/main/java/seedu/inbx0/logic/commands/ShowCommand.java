package seedu.inbx0.logic.commands;


import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.events.ui.ShowFilteredListRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show a filtered list by the specific filter condition\n"
            + "Parameters: today|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday|"
            + "event|dealine|floating|red|yellow|green|none|complete|incomplete|expired|unexpired\n"
            + "Show command is non-casesensitive and allow use first three letters of the condition keywords as shortcut\n"
            + "Example: " + COMMAND_WORD + " today\n"
            + "Example: " + COMMAND_WORD + " sat";

    public static final String SHOWING_FILTERED_LIST_MESSAGE = "Show a filtered list";
    
    private String filterCondition;
    
    public ShowCommand (String filterCondition) {
        this.filterCondition = filterCondition;
    }
    
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowFilteredListRequestEvent(filterCondition));
        return new CommandResult(SHOWING_FILTERED_LIST_MESSAGE + " with keyword " + filterCondition);
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
