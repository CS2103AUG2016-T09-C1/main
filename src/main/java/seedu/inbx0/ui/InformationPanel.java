import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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

public class InformationPanel extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(InformationPanel.class);
    private static final String FXML = "InformationPanel.fxml";
    
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
   
    private ReminderListPanel ReminderListPanelHolder;
    private ReadOnlyTask task;
    private Stage infoStage;
    private VBox mainPane;

    @FXML
    private AnchorPane reminderListPlaceholder;
    
    public static InformationPanel load(Stage primaryStage, AnchorPane infoPanelPlaceholder,  ReadOnlyTask task){
        InformationPanel infoPanel = UiPartLoader.loadUiPart(primaryStage, infoPanelPlaceholder, new InformationPanel());
        if(task != null) {
            logger.fine("Showing task info");
            
            infoPanel.configure(task);
            infoPanel.fillInnerParts();
        }
        return infoPanel;
    }
    
    void fillInnerParts() {
        ReminderListPanelHolder = ReminderListPanel.load(primaryStage, reminderListPlaceholder, task);        
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
        Stage infoStage = new Stage();
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
        infoStage.show();
    }
    
}