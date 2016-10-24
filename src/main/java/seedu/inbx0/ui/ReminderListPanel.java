package seedu.inbx0.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class ReminderListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);
    private static final String FXML = "ReminderListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReminderTask> reminderListView;

    public ReminderListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static ReminderListPanel load(Stage primaryStage, AnchorPane reminderListPlaceholder,
                                        ReadOnlyTask task) {
        ObservableList<ReminderTask> reminderList = task.getReminders().getInternalList();
        ReminderListPanel reminderListPanel =
                UiPartLoader.loadUiPart(primaryStage, reminderListPlaceholder, new ReminderListPanel());
        reminderListPanel.configure(reminderList);
        return reminderListPanel;
    }

    private void configure(ObservableList<ReminderTask> reminderList) {
        setConnections(reminderList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReminderTask> reminderList) {
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        reminderListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            reminderListView.scrollTo(index);
            reminderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class ReminderListViewCell extends ListCell<ReminderTask> {

        public ReminderListViewCell() {
        }

        @Override
        protected void updateItem(ReminderTask reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null || reminder.getIsAlive() == false) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(ReminderCard.load(reminder, getIndex() + 1).getLayout());
            }
        }
    }

}