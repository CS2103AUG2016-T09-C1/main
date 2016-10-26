package seedu.inbx0.logic.commands;

import java.io.IOException;

import seedu.inbx0.commons.core.Config;
import seedu.inbx0.commons.exceptions.DataConversionException;
import seedu.inbx0.commons.util.ConfigUtil;
import seedu.inbx0.commons.util.FileUtil;

/**
 *
 * @author A0135797M - reused from T09-C2
 *
 * Allows the user to specify the directory to save the tasklist.xml file.
 *
 */
//@@author A0135797M
public class SetDirCommand extends Command{

    public static final String COMMAND_WORD = "saveas";
    public static final String COMMAND_WORD_RESET = "reset";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Allows the user to specify the directory to save the tasklist.xml file.\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Jim/Desktop/taklist.xml";

    public static final String MESSAGE_SAVE_TASK_SUCCESS = "Tasklist saved successfully in %1$s";
    public static final String MESSAGE_FILE_PATH_ERROR = "Error: Directory does not exist.";
    public static final String MESSAGE_CONFIG_FILE_ERROR = "Error: Config file does not exist.";
    public static final String MESSAGE_CHANGE_DIR_ERROR = "Error: Please choose a different save directory.";

    public static String configFilePath = Config.DEFAULT_CONFIG_FILE;

    private String taskListFilePath;

    public SetDirCommand (String filePath) {
        this.taskListFilePath = filePath;
    }

    public static void setConfigFilePath (String newConfigFilePath) {
        configFilePath = newConfigFilePath;
    }

    @Override
    public CommandResult execute() {
        if (!FileUtil.isValidPath(taskListFilePath)) {
        	return new CommandResult(MESSAGE_FILE_PATH_ERROR);
        }
        
        try {
            Config config = ConfigUtil.readConfig(configFilePath).orElse(new Config());
            
            String oldTaskListFilePath = config.getTaskListFilePath();
            if (oldTaskListFilePath.equals(taskListFilePath)) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(String.format(MESSAGE_CHANGE_DIR_ERROR));
            }
            config.setTaskListFilePath(taskListFilePath);
            
            ConfigUtil.saveConfig(config, configFilePath);
            
            indicateStoragePathChanged(oldTaskListFilePath, taskListFilePath);
            
            return new CommandResult(String.format(MESSAGE_SAVE_TASK_SUCCESS, config.getTaskListFilePath()));
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_CONFIG_FILE_ERROR);
        } catch (IOException e) {
            return new CommandResult(e.getMessage());
        }
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
