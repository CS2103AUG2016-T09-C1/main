package seedu.inbx0.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.inbx0.commons.util.FxViewUtil;
import seedu.inbx0.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private AnchorPane panel;
    private AnchorPane placeHolderPane;
    
    @FXML 
    private TableView<ReadOnlyTask> taskTableView;
    @FXML
    private TableColumn<ReadOnlyTask, String> idColumn;
    @FXML 
    private TableColumn<ReadOnlyTask, String> nameColumn; 
    @FXML 
    private TableColumn<ReadOnlyTask, String> tagsColumn;
    @FXML 
    private TableColumn<ReadOnlyTask, String> startDateColumn;
    @FXML 
    private TableColumn<ReadOnlyTask, String> startTimeColumn;
    @FXML 
    private TableColumn<ReadOnlyTask, String> endDateColumn;
    @FXML 
    private TableColumn<ReadOnlyTask, String> endTimeColumn;
    
    public TaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskTableView.setItems(taskList);
        taskTableView.setTableMenuButtonVisible(true);  
        nameColumn.setCellValueFactory(task-> new SimpleStringProperty(task.getValue().getStatusAndName())); 
        tagsColumn.setCellValueFactory(task-> new SimpleStringProperty(task.getValue().tagsString()));  
        startDateColumn.setCellValueFactory(task-> new SimpleStringProperty(task.getValue().getStartDate().getTotalDate()));  
        startTimeColumn.setCellValueFactory(task-> new SimpleStringProperty(task.getValue().getStartTime().getTime()));  
        endDateColumn.setCellValueFactory(task-> new SimpleStringProperty(task.getValue().getEndDate().getTotalDate())); 
        endTimeColumn.setCellValueFactory(task-> new SimpleStringProperty(task.getValue().getEndTime().getTime())); 
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(panel, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskTableView.scrollTo(index);
            taskTableView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    
/*
    class TaskListViewCell extends TableRow<ReadOnlyTask> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }
*/
   
    
/*   
    public static class TaskData {  
        SimpleStringProperty name, startDate, startTime, endDate, endTime, tags;  
  
        public TaskData(ReadOnlyTask task) {  
            this.name = new SimpleStringProperty(task.getName().getName());  
            this.startDate = new SimpleStringProperty(task.getStartDate().getDate());  
            this.startTime = new SimpleStringProperty(task.getStartTime().getTime());  
            this.endDate = new SimpleStringProperty(task.getEndDate().getDate());
            this.tags = new SimpleStringProperty(task.tagsString());
        }  
  
        public String getName() {  
            return name.get();  
        }  
  
        public void setName(String name) {  
            this.name.set(name);  
        }  
  
        public String getStartDate() {  
            return startDate.get();  
        }  
        
        public void setStartDate(String startDate) {  
            this.startDate.set(startDate);  
        }  
        
        public String getStartTime() {  
            return startTime.get();  
        }  
        
        public void setStartTime(String startTime) {  
            this.startTime.set(startTime);  
        } 
        
        public String getEndDate() {  
            return endDate.get();  
        }
        
        public void setEndDate(String endDate) {  
            this.endDate.set(endDate);  
        }  
        
        public String getTags() {  
            return tags.get();  
        }  
         
        
        public void setTags(String tags) {  
            this.tags.set(tags);  
        }  
  
    }  
*/
}
