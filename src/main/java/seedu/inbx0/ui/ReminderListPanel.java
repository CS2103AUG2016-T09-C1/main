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
import seedu.inbx0.model.reminder.ReminderTask;
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

    public static ReminderListPanel load(Stage primaryStage, AnchorPane reminderListViewPlaceholder,
            ObservableList<ReminderTask> reminderList) {
        ReminderListPanel reminderListPanel = UiPartLoader.loadUiPart(primaryStage, reminderListViewPlaceholder,
                new ReminderListPanel());
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
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    class ReminderListViewCell extends ListCell<ReminderTask> {

        public ReminderListViewCell() {
        }

        @Override
        protected void updateItem(ReminderTask reminder, boolean empty) {
            super.updateItem(reminder, empty);
            if (empty || reminder.getIsAlive() == false || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(ReminderListCard.load(reminder, getIndex() + 1).getLayout());
            }
        }
    }
}