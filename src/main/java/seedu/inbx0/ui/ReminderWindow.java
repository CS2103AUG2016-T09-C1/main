package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.inbx0.model.task.ReadOnlyTask;
import java.util.logging.Logger;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.util.FxViewUtil;
import seedu.inbx0.logic.Logic;

//@@author A0139579J
public class ReminderWindow extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);
    private static final String FXML = "ReminderWindow.fxml";
    private static final String ICON = "/images/help_icon.png";
    private static final String TITLE = "Reminder!";
    
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
    private Button OkayButton;
    
    private ReadOnlyTask task;
    private Stage reminderStage;
    private VBox mainPane;
    
    public static ReminderWindow load(Stage primaryStage, ReadOnlyTask task){
        logger.fine("Showing reminders");
        ReminderWindow reminderWindow = UiPartLoader.loadUiPart(primaryStage, new ReminderWindow());
        reminderWindow.configure(task);
        return reminderWindow;
    }
    
    public void displayInfo() {
        name.setText(task.getName().getName());
        startDate.setText(task.getStartDate().getTotalDate());
        startTime.setText(task.getStartTime().getTime());
        endDate.setText(task.getEndDate().getTotalDate());
        endTime.setText(task.getEndTime().getTime());
    }
    
    private void configure(ReadOnlyTask task){
        this.task = task;        
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        reminderStage = createDialogStage(TITLE, null, scene);
        setIcon(reminderStage, ICON);
        displayInfo();
    }
    
    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    public void show() {
        reminderStage.showAndWait();
    }
    
    @FXML
    public void handleCloseReminder() {
        reminderStage.close();
    }
    
    @FXML
    public void keyPressed(KeyEvent evt) {
        if(evt.getCode().equals(KeyCode.ENTER)) {
            handleCloseReminder();
        }
        
    }
}