package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
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
	private final static String FXML_FILE_FOLDER = "/view/";
    private static final String ICON = "/images/inbx0.png";
    private static final String FXML = "MainWindow2.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 800;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    // private BrowserPanel browserPanel;
    // private TaskListPanel floatTaskListPanel;
    // private TaskListPanel taskListPanel;
    //private TaskListPanel upperTaskListPanel;
    //private InformationPanel bottomReminderListPanel;
    //private MainDisplayPanel mainDisplayPanel;
    private ResultDisplay resultDisplay;
    private TitledPaneList titledPaneList;
    private TaskListPanel taskListPanel;
    private ReminderList reminderList;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String taskManagerName;

    @FXML
    private AnchorPane titledPanePlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;
    
    @FXML
    private AnchorPane reminderListPlaceholder;

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
        
        scene.getStylesheets().clear();
        scene.getStylesheets().add(FXML_FILE_FOLDER + "PinkTheme.css");
        titledPanePlaceholder.getStyleClass().add("pane");
        commandBoxPlaceholder.getStyleClass().add("pane");
        resultDisplayPlaceholder.getStyleClass().add("pane");
        taskListPanelPlaceholder.getStyleClass().add("pane");
        reminderListPlaceholder.getStyleClass().add("pane");
        statusbarPlaceholder.getStyleClass().add("pane");
        
        setAccelerators();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts() {
        reminderList = ReminderList.load(primaryStage, getReminderListPlaceholder(), null);
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(), logic.getFilteredTaskList());
        titledPaneList = TitledPaneList.load(primaryStage, getTitledPanePlaceholder(), logic, taskListPanel, getTaskListPanelPlaceholder());
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
    
    private AnchorPane getTaskListPanelPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    private AnchorPane getTitledPanePlaceholder() {
        return titledPanePlaceholder;
    }
    
    private AnchorPane getReminderListPlaceholder() {
        return reminderListPlaceholder;
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
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
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
    
    public void handleReminder(ReadOnlyTask task) {
        ReminderWindow reminderWindow = ReminderWindow.load(primaryStage, task);
        reminderWindow.show();
    }
    
    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    public ReminderList getReminderList() {
    	return reminderList;
    }

    public TitledPaneList getTitledPaneList() {
        return titledPaneList;
    }
}