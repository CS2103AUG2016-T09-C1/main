package seedu.inbx0.ui;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.util.FxViewUtil;
import seedu.inbx0.logic.Logic;
import seedu.inbx0.model.task.ReadOnlyTask;

public class MainDisplayPanel extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(InformationPanel.class);
    private static final String FXML = "MainDisplay.fxml";
    private AnchorPane placeHolderPane;
    private HBox mainPane;
    private TaskListPanel upperTaskListPanel;
    private InformationPanel bottomReminderListPanel;
    private Logic logic;
       
    @FXML
    private TitledPane DayTitledPane;

    @FXML
    private TitledPane CategoryTitledPane;

    @FXML
    private TitledPane ImportanceTitledPane;

    @FXML
    private TitledPane CompletenessTitledPane;

    @FXML
    private TitledPane ExpiredTitledPane;

    @FXML
    private AnchorPane upperTaskListPanelPlaceholder;

    @FXML
    private AnchorPane bottomReminderListPanelPlaceholder;
    
    @FXML
    private AnchorPane listDisplayPlaceholder;    
    
    @FXML
    private GridPane taskListAttribute;
    
    public MainDisplayPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        mainPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public static MainDisplayPanel load(Stage primaryStage, AnchorPane mainDisplayPanelPlaceholder, Logic logic){
        MainDisplayPanel mainDisplayPanel = UiPartLoader.loadUiPart(primaryStage, mainDisplayPanelPlaceholder, new MainDisplayPanel()); 
        mainDisplayPanel.configure(logic);
        mainDisplayPanel.addToPlaceholder();
        return mainDisplayPanel;
    }
    
    private void configure(Logic logic){
        this.logic = logic;
        fillInnerPart();
    }
    
    private void fillInnerPart() {
        upperTaskListPanel = TaskListPanel.load(primaryStage, getUpperTaskListPlaceholder(),
                logic.getFilteredTaskList());
        bottomReminderListPanel = InformationPanel.load(primaryStage, getReminderListPlaceholder(), null);
    }
    
    private AnchorPane getUpperTaskListPlaceholder() {
        return upperTaskListPanelPlaceholder;
    }

    private AnchorPane getReminderListPlaceholder() {
        return bottomReminderListPanelPlaceholder;
    }    
    
    public InformationPanel getBottomReminderListPanel() {
        return this.bottomReminderListPanel;
    }

    public TaskListPanel getUpperTaskListPanel() {
        return this.upperTaskListPanel;
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        SplitPane.setResizableWithParent(listDisplayPlaceholder, false);
        SplitPane.setResizableWithParent(taskListAttribute, false);
        FxViewUtil.applyAnchorBoundaryParameters(listDisplayPlaceholder, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(mainPane);
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
            ExpiredTitledPane.setExpanded(true);
            handleListExpire();
        } else if (filterCondition.equals("Valid")) {
            ExpiredTitledPane.setExpanded(true);
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
        ExpiredTitledPane.setExpanded(false);
    }

    public void displayReminderInfoPanel(ReadOnlyTask newSelection) {
        bottomReminderListPanel = InformationPanel.load(primaryStage, getReminderListPlaceholder(), newSelection);        
    }
}
