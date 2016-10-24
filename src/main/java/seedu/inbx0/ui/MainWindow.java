package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.inbx0.commons.core.Config;
import seedu.inbx0.commons.core.GuiSettings;
import seedu.inbx0.commons.events.ui.ExitAppRequestEvent;
import seedu.inbx0.logic.Logic;
import seedu.inbx0.model.UserPrefs;
import seedu.inbx0.model.task.ReadOnlyTask;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/inbx0.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 800;
    public static final int MIN_WIDTH = 1300;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    // private BrowserPanel browserPanel;
    // private TaskListPanel floatTaskListPanel;
    // private TaskListPanel taskListPanel;
    private TaskListPanel upperTaskListPanel;
    private InformationPanel bottomReminderListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String taskManagerName;

    // @FXML
    // private AnchorPane browserPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TitledPane DayTitledPane;

    @FXML
    private TitledPane CategoryTitledPane;

    @FXML
    private TitledPane ImportanceTitledPane;

    @FXML
    private TitledPane CompletenessTitledPane;

    @FXML
    private TitledPane ExpireTitledPane;

    @FXML
    private AnchorPane upperTaskListPanelPlaceholder;

    @FXML
    private AnchorPane bottomReminderListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;
    
    @FXML
    private AnchorPane mainDisplayPlaceholder;
    
    @FXML
    private AnchorPane listDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getTaskListName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String taskManagerName, Config config, UserPrefs prefs, Logic logic) {

        // Set dependencies
        this.logic = logic;
        this.taskManagerName = taskManagerName;
        this.config = config;
        this.userPrefs = prefs;

        // Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        SplitPane.setResizableWithParent(mainDisplayPlaceholder, false);
        SplitPane.setResizableWithParent(listDisplayPlaceholder, false);
        
        setAccelerators();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts() {
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredTaskList());
        bottomReminderListPanel = InformationPanel.load(primaryStage, getReminderListPlaceholder(), null);
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), config.getTaskListFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    private AnchorPane getUpperTaskListPlaceholder() {
        return upperTaskListPanelPlaceholder;
    }

    private AnchorPane getReminderListPlaceholder() {
        return bottomReminderListPanelPlaceholder;
    }

    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(), (int) primaryStage.getX(),
                (int) primaryStage.getY());
    }

    @FXML
    public void handleHelp() {
        HelpWindow1 helpWindow = HelpWindow1.load(primaryStage);
        helpWindow.show();
    }
    
    //@@author A0139579J
    @FXML
    public void handleOverdueTasks() {
        OverdueTaskWindow overdueTaskWindow = OverdueTaskWindow.load(primaryStage, logic);
        overdueTaskWindow.show();
    }
    
    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public InformationPanel getBottomReminderListPanel() {
        return this.bottomReminderListPanel;
    }

    public TaskListPanel getUpperTaskListPanel() {
        return this.upperTaskListPanel;
    }

    @FXML
    public void handleListToday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("today"));
    }

    @FXML
    public void handleListMonday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("Monday"));
    }

    @FXML
    public void handleListTuesday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("Tuesday"));
    }

    @FXML
    public void handleListWednesday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("Wednesday"));
    }

    @FXML
    public void handleListThursday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("Thursday"));
    }

    @FXML
    public void handleListFriday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("Friday"));
    }

    @FXML
    public void handleListSaturday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("Saturday"));
    }

    @FXML
    public void handleListSunday() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDayTaskList("Sunday"));
    }

    @FXML
    public void handleListEvent() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredEventTaskList());
    }

    @FXML
    public void handleListFloating() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredFloatTaskList());
    }

    @FXML
    public void handleListDeadline() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDeadlineTaskList());
    }

    @FXML
    public void handleListRed() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredImportanceTaskList("Red"));
    }

    @FXML
    public void handleListYellow() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredImportanceTaskList("Yellow"));
    }

    @FXML
    public void handleListGreen() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredImportanceTaskList("Green"));
    }

    @FXML
    public void handleListNone() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredImportanceTaskList(""));
    }

    @FXML
    public void handleListComplete() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredDoneTaskList());
    }

    @FXML
    public void handleListIncomplete() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredToDoTaskList());
    }

    @FXML
    public void handleListExpire() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredOverdueTaskList());
    }

    @FXML
    public void handleListValid() {
        upperTaskListPanel = null;
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredBeforedueTaskList());
    }

    public void handleShowFilteredListRequestByShowCommand(String filterCondition) {
        if (filterCondition.equals("today")) {
            closeAllTitledPane();
            handleListToday();
        } else if (filterCondition.equals("Monday")) {
            DayTitledPane.setExpanded(true);
            handleListMonday();
        } else if (filterCondition.equals("Tuesday")) {
            DayTitledPane.setExpanded(true);
            handleListTuesday();
        } else if (filterCondition.equals("Wednesday")) {
            DayTitledPane.setExpanded(true);
            handleListWednesday();
        } else if (filterCondition.equals("Thursday")) {
            DayTitledPane.setExpanded(true);
            handleListThursday();
        } else if (filterCondition.equals("Friday")) {
            DayTitledPane.setExpanded(true);
            handleListFriday();
        } else if (filterCondition.equals("Saturday")) {
            DayTitledPane.setExpanded(true);
            handleListSaturday();
        } else if (filterCondition.equals("Sunday")) {
            DayTitledPane.setExpanded(true);
            handleListSunday();
        } else if (filterCondition.equals("Event")) {
            CategoryTitledPane.setExpanded(true);
            handleListEvent();
        } else if (filterCondition.equals("Floating")) {
            CategoryTitledPane.setExpanded(true);
            handleListFloating();
        } else if (filterCondition.equals("Deadline")) {
            CategoryTitledPane.setExpanded(true);
            handleListDeadline();
        } else if (filterCondition.equals("Red")) {
            ImportanceTitledPane.setExpanded(true);
            handleListRed();
        } else if (filterCondition.equals("Yellow")) {
            ImportanceTitledPane.setExpanded(true);
            handleListYellow();
        } else if (filterCondition.equals("Green")) {
            ImportanceTitledPane.setExpanded(true);
            handleListGreen();
        } else if (filterCondition.equals("None")) {
            ImportanceTitledPane.setExpanded(true);
            handleListNone();
        } else if (filterCondition.equals("Complete")) {
            CompletenessTitledPane.setExpanded(true);
            handleListComplete();
        } else if (filterCondition.equals("Incomplete")) {
            CompletenessTitledPane.setExpanded(true);
            handleListIncomplete();
        } else if (filterCondition.equals("Expire")) {
            ExpireTitledPane.setExpanded(true);
            handleListExpire();
        } else if (filterCondition.equals("Valid")) {
            ExpireTitledPane.setExpanded(true);
            handleListValid();
        }
    }

    public void handleReminder(ReadOnlyTask task) {
        ReminderWindow reminderWindow = ReminderWindow.load(primaryStage, task);
        reminderWindow.show();
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredTaskList());
    }
    
    public void handleShowNormalTaskList() {
        closeAllTitledPane();
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredTaskList());
    }

    private void closeAllTitledPane() {
        DayTitledPane.setExpanded(false);
        CategoryTitledPane.setExpanded(false);
        ImportanceTitledPane.setExpanded(false);
        CompletenessTitledPane.setExpanded(false);
        ExpireTitledPane.setExpanded(false);
    }

    public void displayReminderInfoPanel(ReadOnlyTask newSelection) {
        bottomReminderListPanel = InformationPanel.load(primaryStage, getReminderListPlaceholder(), newSelection);        
    }
}