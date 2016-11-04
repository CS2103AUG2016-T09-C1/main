package seedu.inbx0.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.task.ReadOnlyTask;

import java.util.Iterator;
import java.util.logging.Logger;
import seedu.inbx0.commons.core.LogsCenter;

//@@author A0139579J
public class InformationPanel extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(InformationPanel.class);
    private static final String FXML = "InformationPanel.fxml";
    private AnchorPane placeHolderPane;
    private VBox mainPane;
    
    @FXML
    private ListView<ReminderTask> reminderListView;
    @FXML
    private Label name;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label tags;
    
    
    public InformationPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public static InformationPanel load(Stage primaryStage, AnchorPane infoPanelPlaceholder,  ReadOnlyTask task){
        InformationPanel infoPanel = UiPartLoader.loadUiPart(primaryStage, infoPanelPlaceholder, new InformationPanel());
        logger.fine("Showing task info"); 
        infoPanel.configure(task);
        return infoPanel;
    }
    
    //void fillInnerParts() {
        //ReminderListPanelHolder = ReminderListPanel.load(primaryStage, reminderListPlaceholder, task);        
    //}

    public void displayInfo(ReadOnlyTask task) {
        name.setText(task.getName().getName());
        startDate.setText(task.getStartDate().getTotalDate());
        startTime.setText(task.getStartTime().getTime());
        endDate.setText(task.getEndDate().getTotalDate());
        endTime.setText(task.getEndTime().getTime());
        tags.setText(task.tagsString());
    }
    
    private void configure(ReadOnlyTask task){
        if(task != null) {
            displayInfo(task);
            UniqueReminderList uniqueReminderList = new UniqueReminderList(task.getReminders());
            Iterator<ReminderTask> check = uniqueReminderList.iterator();
            while(check.hasNext()) {
                if(check.next().getIsAlive() == false) {
                    check.remove();
                }
            }
            ObservableList<ReminderTask> reminderList = uniqueReminderList.getInternalList();
            setConnections(reminderList);
            addToPlaceholder();
        }
    }
    
    
    private void setConnections(ObservableList<ReminderTask> reminderList) {
        reminderListView.setItems(reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        SplitPane.setResizableWithParent(reminderListView, false);
        placeHolderPane.getChildren().add(mainPane);
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