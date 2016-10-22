package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.inbx0.model.task.ReadOnlyTask;
import java.util.logging.Logger;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.logic.Logic;

public class ReminderWindow extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);
    private static final String FXML = "ReminderWindow.fxml";
    private static final String ICON = "/images/help_icon.png";
    private static final String TITLE = "Reminder!";
    @FXML
    private VBox mainPane;
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
    
    private ReadOnlyTask task;
    private Logic logic;
    private Scene scene;
    private Stage reminderStage;
    
    public static ReminderWindow load(Stage primaryStage, Logic logic){
        logger.fine("Showing reminders");
        ReminderWindow reminderWindow = UiPartLoader.loadUiPart(primaryStage, new ReminderWindow());
        reminderWindow.configure(logic);
        return UiPartLoader.loadUiPart(reminderWindow);
    }
    
    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        startDate.setText(task.getStartDate().getTotalDate());
        startTime.setText(task.getStartTime().value);
        endDate.setText(task.getEndDate().getTotalDate());
        endTime.setText(task.getEndTime().value);
    }
    
    private void configure(Logic logic){
        this.logic = logic;
        Stage reminderStage = new Stage();
        reminderStage.initModality(Modality.APPLICATION_MODAL);
        this.reminderStage = reminderStage;
        setTitle(TITLE);
        setIcon(reminderStage, ICON);
        scene = new Scene(mainPane);
        reminderStage.setScene(scene);
    }
    
    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    private void setTitle(String title) {
        reminderStage.setTitle(title);
    }
    
    public void show() {
        reminderStage.showAndWait();
    }
}
