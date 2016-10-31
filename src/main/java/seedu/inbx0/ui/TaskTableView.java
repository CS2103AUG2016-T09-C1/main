package seedu.inbx0.ui;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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

import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

/**
 * Panel containing the list of tasks.
 */
public class TaskTableView extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskTableView.class);
    private static final String FXML = "TaskTableView.fxml";
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

    public TaskTableView() {
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

    public static TaskTableView load(Stage primaryStage, AnchorPane taskListPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        TaskTableView taskListPanel = UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskTableView());
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
        setTaskTableViewColumnData();
        setTaskTableViewRowColor();
        setEventHandlerForSelectionChangeEvent();
    }

    private void setTaskTableViewColumnData() {
        idColumn.setCellValueFactory(new Callback<CellDataFeatures<ReadOnlyTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ReadOnlyTask, String> p) {
                return new ReadOnlyObjectWrapper(taskTableView.getItems().indexOf(p.getValue()) + 1);
            }
        });
        nameColumn.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().getStatusAndName()));
        tagsColumn.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().tagsString()));
        startDateColumn.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().getStartDate().getTotalDate()));
        startTimeColumn.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().getStartTime().getTime()));
        endDateColumn.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().getEndDate().getTotalDate()));
        endTimeColumn.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().getEndTime().getTime()));
    }
    
    /*
    private void setTaskTableViewRowColor() {
        taskTableView.setRowFactory(new Callback<TableView<ReadOnlyTask>, TableRow<ReadOnlyTask>>() {
            @Override
            public TableRow<ReadOnlyTask> call(final TableView<ReadOnlyTask> p) {
                return new TableRow<ReadOnlyTask>() {
                    @Override
                    public void updateItem(ReadOnlyTask task, boolean empty) {
                        super.updateItem(task, empty);
                        if (task == null || empty) {
                            setStyle("");
                        } else {
                            if (task.getLevel().getNumberLevel() == 1 && task.getIsExpired() == true
                                    && task.getIsEvent() == true) {
                                    setStyle("-fx-background-color: rgba(0, 255, 0, 0.5);");
                                
                            } else if (task.getLevel().getNumberLevel() == 1) {
                                setStyle("-fx-background-color: rgba(0, 255, 0, 0.8);");
                            } else if (task.getLevel().getNumberLevel() == 2 && task.getIsExpired() == true
                                    && task.getIsEvent() == true) {
                                setStyle("-fx-background-color: rgba(255, 255, 0, 0.5);");
                            } else if (task.getLevel().getNumberLevel() == 2) {
                                setStyle("-fx-background-color: rgba(255, 255, 0, 0.8);");
                            } else if (task.getLevel().getNumberLevel() == 3 && task.getIsExpired() == true
                                    && task.getIsEvent() == true) {
                                setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
                            } else if (task.getLevel().getNumberLevel() == 3) {
                                setStyle("-fx-background-color: rgba(255, 0, 0, 0.8);");                                
                            }
                        }
                    }
                };
            }
        });
    }*/
    /*
    private void setTaskTableViewRowColor() {
        int index = 0;
        System.out.println("here");
        for (Node n : taskTableView.lookupAll("TableRow")) {
                TableRow row = (TableRow) n;
                ReadOnlyTask task = taskTableView.getItems().get(index);
                System.out.println(index + " " +task);
                if (task.getLevel().getNumberLevel() == 1 && task.getIsExpired() == true && task.getIsEvent() == true) {
                    row.setStyle("-fx-background-color: rgba(0, 255, 0, 0.5);");
                } else if (task.getLevel().getNumberLevel() == 1) {
                    row.setStyle("-fx-background-color: rgba(0, 255, 0, 0.8);");
                } else if (task.getLevel().getNumberLevel() == 2 && task.getIsExpired() == true
                        && task.getIsEvent() == true) {
                    row.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5);");
                } else if (task.getLevel().getNumberLevel() == 2) {
                    row.setStyle("-fx-background-color: rgba(255, 255, 0, 0.8);");
                } else if (task.getLevel().getNumberLevel() == 3 && task.getIsExpired() == true
                        && task.getIsEvent() == true) {
                    row.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
                } else if (task.getLevel().getNumberLevel() == 3) {
                    row.setStyle("-fx-background-color: rgba(255, 0, 0, 0.8);");
                }
                index++;
                if (index == taskTableView.getItems().size()) {
                    break;
                }
        }
    }
    */


    private void setTaskTableViewRowColor() {
        taskTableView.setRowFactory(new Callback<TableView<ReadOnlyTask>, TableRow<ReadOnlyTask>>() {
            @Override
            public TableRow<ReadOnlyTask> call(TableView<ReadOnlyTask> task) {
                return new TableRow<ReadOnlyTask>() {
                    @Override
                    protected void updateItem(ReadOnlyTask task, boolean empty) {
                        if (task == null || empty) {
                            setStyle("");
                        } else {
                            if (task.getLevel().getNumberLevel() == 1 && task.getIsExpired() == true
                                    && task.getIsEvent() == true) {
                                    setStyle("-fx-background-color: rgba(0, 255, 0, 0.5);");
                                
                            } else if (task.getLevel().getNumberLevel() == 1) {
                                setStyle("-fx-background-color: rgba(0, 255, 0, 0.8);");
                            } else if (task.getLevel().getNumberLevel() == 2 && task.getIsExpired() == true
                                    && task.getIsEvent() == true) {
                                setStyle("-fx-background-color: rgba(255, 255, 0, 0.5);");
                            } else if (task.getLevel().getNumberLevel() == 2) {
                                setStyle("-fx-background-color: rgba(255, 255, 0, 0.8);");
                            } else if (task.getLevel().getNumberLevel() == 3 && task.getIsExpired() == true
                                    && task.getIsEvent() == true) {
                                setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
                            } else if (task.getLevel().getNumberLevel() == 3) {
                                setStyle("-fx-background-color: rgba(255, 0, 0, 0.8);");                                
                            }
                        }
                    }
                };

            }
        });
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

    public TableView<ReadOnlyTask> getTaskTableView() {
        return taskTableView;
    }
}