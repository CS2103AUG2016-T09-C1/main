package seedu.inbx0.ui;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
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

public class TitledPaneList extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(TitledPaneList.class);
    private static final String FXML = "TitledPaneList.fxml";
    private AnchorPane placeHolderPane;
    private Accordion mainPane;
    private Logic logic;
    private TaskTableView taskTableView;
    private AnchorPane taskTableViewPlaceholder; 
       
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
    
    public TitledPaneList() {
        super();
    }

    @Override
    public void setNode(Node node) {
        mainPane = (Accordion) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public static TitledPaneList load(Stage primaryStage, AnchorPane titledPaneListPlaceholder, Logic logic, 
             TaskTableView taskTableView, AnchorPane taskTableViewPlaceholder){
        TitledPaneList titlePaneList = UiPartLoader.loadUiPart(primaryStage, titledPaneListPlaceholder, new TitledPaneList()); 
        titlePaneList.configure(logic, taskTableView, taskTableViewPlaceholder);
        titlePaneList.addToPlaceholder();
        return titlePaneList;
    }
    
    private void configure(Logic logic, TaskTableView taskTableView, AnchorPane taskTableViewPlaceholder){
        this.logic = logic;
        this.taskTableView = taskTableView;
        this.taskTableViewPlaceholder = taskTableViewPlaceholder;
    }
    
    private AnchorPane getTaskTableViewPlaceholder() {
        return taskTableViewPlaceholder;
    }
    
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(mainPane);
    }

    @FXML
    public void handleListToday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("today"));
    }

    @FXML
    public void handleListMonday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("Monday"));
    }

    @FXML
    public void handleListTuesday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("Tuesday"));
    }

    @FXML
    public void handleListWednesday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("Wednesday"));
    }

    @FXML
    public void handleListThursday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("Thursday"));
    }

    @FXML
    public void handleListFriday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("Friday"));
    }

    @FXML
    public void handleListSaturday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("Saturday"));
    }

    @FXML
    public void handleListSunday() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDayTaskList("Sunday"));
    }

    @FXML
    public void handleListEvent() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredEventTaskList());
    }

    @FXML
    public void handleListFloating() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredFloatTaskList());
    }

    @FXML
    public void handleListDeadline() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDeadlineTaskList());
    }

    @FXML
    public void handleListRed() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredImportanceTaskList("Red"));
    }

    @FXML
    public void handleListYellow() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredImportanceTaskList("Yellow"));
    }

    @FXML
    public void handleListGreen() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredImportanceTaskList("Green"));
    }

    @FXML
    public void handleListNone() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredImportanceTaskList(""));
    }

    @FXML
    public void handleListComplete() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredDoneTaskList());
    }

    @FXML
    public void handleListIncomplete() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredToDoTaskList());
    }

    @FXML
    public void handleListExpired() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredOverdueTaskList());
    }

    @FXML
    public void handleListUnexpired() {
        taskTableView = null;
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
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
        } else if (filterCondition.equals("Expired")) {
            ExpiredTitledPane.setExpanded(true);
            handleListExpired();
        } else if (filterCondition.equals("Unexpired")) {
            ExpiredTitledPane.setExpanded(true);
            handleListUnexpired();
        }
    }
    
    public void handleShowNormalTaskList() {
        closeAllTitledPane();
        taskTableView = TaskTableView.load(primaryStage, getTaskTableViewPlaceholder(),
                logic.getFilteredTaskList());
    }

    private void closeAllTitledPane() {
        DayTitledPane.setExpanded(false);
        CategoryTitledPane.setExpanded(false);
        ImportanceTitledPane.setExpanded(false);
        CompletenessTitledPane.setExpanded(false);
        ExpiredTitledPane.setExpanded(false);
    }
/*
    public void displayReminderInfoPanel(ReadOnlyTask newSelection) {
        bottomReminderListPanel = InformationPanel.load(primaryStage, getReminderListPlaceholder(), newSelection);        
    }
    */
}

