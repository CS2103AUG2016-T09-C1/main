package seedu.inbx0.ui;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.util.FxViewUtil;
import seedu.inbx0.logic.Logic;

public class TitledPaneList extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(TitledPaneList.class);
    private static final String FXML = "TitledPaneList.fxml";
    private AnchorPane placeHolderPane;
    private AnchorPane mainPane;
    private Logic logic;
    private TaskListPanel taskListPanel;
    private AnchorPane taskListPanelPlaceholder; 
       
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
        mainPane = (AnchorPane) node;
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
             TaskListPanel taskListPanel, AnchorPane taskListPanelPlaceholder){
        TitledPaneList titlePaneList = UiPartLoader.loadUiPart(primaryStage, titledPaneListPlaceholder, new TitledPaneList()); 
        titlePaneList.configure(logic, taskListPanel, taskListPanelPlaceholder);
        titlePaneList.addToPlaceholder();
        return titlePaneList;
    }
    
    private void configure(Logic logic, TaskListPanel taskListPanel, AnchorPane taskListPanelPlaceholder){
        this.logic = logic;
        this.taskListPanel = taskListPanel;
        this.taskListPanelPlaceholder = taskListPanelPlaceholder;
    }
    
    private AnchorPane getTaskListPanelPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(mainPane);
    }

    @FXML
    public void handleListToday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("today"));
    }

    @FXML
    public void handleListMonday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("Monday"));
    }

    @FXML
    public void handleListTuesday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("Tuesday"));
    }

    @FXML
    public void handleListWednesday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("Wednesday"));
    }

    @FXML
    public void handleListThursday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("Thursday"));
    }

    @FXML
    public void handleListFriday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("Friday"));
    }

    @FXML
    public void handleListSaturday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("Saturday"));
    }

    @FXML
    public void handleListSunday() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredTaskListByDay("Sunday"));
    }

    @FXML
    public void handleListEvent() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredEventTaskList());
    }

    @FXML
    public void handleListFloating() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredFloatTaskList());
    }

    @FXML
    public void handleListDeadline() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredDeadlineTaskList());
    }

    @FXML
    public void handleListRed() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredImportanceTaskList("Red"));
    }

    @FXML
    public void handleListYellow() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredImportanceTaskList("Yellow"));
    }

    @FXML
    public void handleListGreen() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredImportanceTaskList("Green"));
    }

    @FXML
    public void handleListNone() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredImportanceTaskList(""));
    }

    @FXML
    public void handleListComplete() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredDoneTaskList());
    }

    @FXML
    public void handleListIncomplete() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredToDoTaskList());
    }

    @FXML
    public void handleListExpired() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredOverdueTaskList());
    }

    @FXML
    public void handleListUnexpired() {
        taskListPanel = null;
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredBeforedueTaskList());
    }

    public void handleShowFilteredListRequestByShowCommand(String filterCondition) {
        if ("today".equals(filterCondition)) {
            closeAllTitledPane();
            handleListToday();
            
        } else if ("Monday".equals(filterCondition)) {
            DayTitledPane.setExpanded(true);
            handleListMonday();
        } else if ("Tuesday".equals(filterCondition)) {
            DayTitledPane.setExpanded(true);
            handleListTuesday();
        } else if ("Wednesday".equals(filterCondition)) {
            DayTitledPane.setExpanded(true);
            handleListWednesday();
        } else if ("Thursday".equals(filterCondition)) {
            DayTitledPane.setExpanded(true);
            handleListThursday();
        } else if ("Friday".equals(filterCondition)) {
            DayTitledPane.setExpanded(true);
            handleListFriday();
        } else if ("Saturday".equals(filterCondition)) {
            DayTitledPane.setExpanded(true);
            handleListSaturday();
        } else if ("Sunday".equals(filterCondition)) {
            DayTitledPane.setExpanded(true);
            handleListSunday();
        } else if ("Event".equals(filterCondition)) {
            CategoryTitledPane.setExpanded(true);
            handleListEvent();
        } else if ("Floating".equals(filterCondition)) {
            CategoryTitledPane.setExpanded(true);
            handleListFloating();
        } else if ("Deadline".equals(filterCondition)) {
            CategoryTitledPane.setExpanded(true);
            handleListDeadline();
        } else if ("Red".equals(filterCondition)) {
            ImportanceTitledPane.setExpanded(true);
            handleListRed();
        } else if ("Yellow".equals(filterCondition)) {
            ImportanceTitledPane.setExpanded(true);
            handleListYellow();
        } else if ("Green".equals(filterCondition)) {
            ImportanceTitledPane.setExpanded(true);
            handleListGreen();
        } else if ("None".equals(filterCondition)) {
            ImportanceTitledPane.setExpanded(true);
            handleListNone();
        } else if ("Complete".equals(filterCondition)) {
            CompletenessTitledPane.setExpanded(true);
            handleListComplete();
        } else if ("Incomplete".equals(filterCondition)) {
            CompletenessTitledPane.setExpanded(true);
            handleListIncomplete();
        } else if ("Expired".equals(filterCondition)) {
            ExpiredTitledPane.setExpanded(true);
            handleListExpired();
        } else if ("Unexpired".equals(filterCondition)) {
            ExpiredTitledPane.setExpanded(true);
            handleListUnexpired();
        }
    }
    
    public void handleShowNormalTaskList() {
        closeAllTitledPane();
        taskListPanel = TaskListPanel.load(primaryStage, getTaskListPanelPlaceholder(),
                logic.getFilteredToDoTaskList());
    }

    private void closeAllTitledPane() {
        DayTitledPane.setExpanded(false);
        CategoryTitledPane.setExpanded(false);
        ImportanceTitledPane.setExpanded(false);
        CompletenessTitledPane.setExpanded(false);
        ExpiredTitledPane.setExpanded(false);
    }
}

