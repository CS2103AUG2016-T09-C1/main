package seedu.inbx0.logic.commands;


import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.events.ui.ShowFilteredListRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show a filtered list by the specific filter condition\n"
            + "Example: " + COMMAND_WORD + "today";

    public static final String SHOWING_FILTERED_LIST_MESSAGE = "Show a filtered list";
    
    private String filterCondition;
    
    public ShowCommand() {
        this.filterCondition = "";
    }
    
    public ShowCommand (String filterCondition) {
        this.filterCondition = filterCondition;
    }
    
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowFilteredListRequestEvent(filterCondition));
        return new CommandResult(SHOWING_FILTERED_LIST_MESSAGE + " with keyword " + filterCondition);
    }
}
