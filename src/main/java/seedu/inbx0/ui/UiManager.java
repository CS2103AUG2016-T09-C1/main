package seedu.inbx0.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.inbx0.MainApp;
import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.Config;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.events.ui.CloseAllTitledPanesEvent;
import seedu.inbx0.commons.events.storage.DataSavingExceptionEvent;
import seedu.inbx0.commons.events.ui.CloseReminderListEvent;
import seedu.inbx0.commons.events.ui.JumpToListRequestEvent;
import seedu.inbx0.commons.events.ui.JumpToTaskRequestEvent;
import seedu.inbx0.commons.events.ui.ShowFilteredListRequestEvent;
import seedu.inbx0.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.inbx0.commons.events.ui.ShowHelpRequestEvent;
import seedu.inbx0.commons.events.ui.ShowReminderRequestEvent;
import seedu.inbx0.commons.util.StringUtil;
import seedu.inbx0.logic.Logic;
import seedu.inbx0.model.UserPrefs;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/inbx0.png";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
            if(!logic.getFilteredOverdueTaskList().isEmpty())
                mainWindow.handleOverdueTasks();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        mainWindow.updatePreferenceSetting();
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    private void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }
   
    //@@author A0139579J
    /**
     * Handles ShowReminderEvent and displays the reminder window
     */
    @Subscribe
    private void handleShowReminderEvent(ShowReminderRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleReminder(event.task);
    }
    
    //@@author
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTitledPaneList().scrollTo(event.targetIndex);
    }
    
    //@@author A0148044J
    @Subscribe
    private void handleJumpToTaskRequestEvent(JumpToTaskRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTitledPaneList().scrollTo(event.task);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.updateReminderList(event.getNewSelection());
    }
    
    @Subscribe 
    private void handleCloseReminderListEvent(CloseReminderListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.closeReminderList();
    }
    
    @Subscribe
    private void handleShowFilteredListRequestEvent(ShowFilteredListRequestEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTitledPaneList().handleShowFilteredListRequestByShowCommand(event.filterCondition);
    }
    
    @Subscribe 
    private void handleCloseAllTitledpanesEvent(CloseAllTitledPanesEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTitledPaneList().closeAllTitledPanes();
    }
    //@@author
}
