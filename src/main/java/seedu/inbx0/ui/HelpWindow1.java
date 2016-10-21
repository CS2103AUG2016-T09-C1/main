package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.logic.Logic;
import seedu.inbx0.logic.commands.Command;
import javafx.stage.Modality;

import java.util.logging.Logger;

import ch.makery.address.model.Person;

/**
 * Controller for a help page
 */
public class HelpWindow1 extends UiPart {
    
    
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "OverdueTaskWindow.fxml";
    private static final String TITLE = "Overdue Tasks";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;
    
    @FXML
    private TableView<String> helpTable;
    @FXML
    private TableColumn<Command, String> commandColumn;
    @FXML
    private TableColumn<Command, String> exampleColumn;
    
    private TitledPane mainPane;
    private ResultDisplay resultDisplay;

    private Stage dialaogStage;

    private Scene scene;
    private Logic logic;
    
    private Stage dialogStage;
    private TaskListPanel taskListPanel;
    
    @FXML
    private AnchorPane taskListPanelPlaceholder;

    public static HelpWindow1 load(Stage primaryStage, Logic logic) {
        logger.fine("Showing overdue tasks.");
        HelpWindow1 overdueTaskWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow1());
        overdueTaskWindow.configure(logic);
        return overdueTaskWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(Logic logic){
        this.logic = logic;
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        this.dialogStage = dialogStage;
/*        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(TITLE);
        dialogStage.setMinWidth(250);
        this.dialogStage = dialogStage;
        Label label = new Label();
        label.setText("Overdue Tasks");
        
        VBox layout = new VBox(10);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);     
        dialogStage.setScene(layout);
        dialogStage.showAndWait(); */
        
        setTitle(TITLE);
        setIcon(dialogStage, ICON);
        setWindowMinSize();
        scene = new Scene(mainPane);
        dialogStage.setScene(scene);
        taskListPanel = TaskListPanel.load(dialogStage, getTaskListPlaceholder(), logic.getFilteredOverdueTaskList());
        
    }
    

    public void show() {
        dialogStage.showAndWait();
    }
    
    public AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }
    
    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }
    
    private void setTitle(String title) {
        dialogStage.setTitle(title);
    }
    
    private void setWindowMinSize() {
        dialogStage.setMinHeight(MIN_HEIGHT);
        dialogStage.setMinWidth(MIN_WIDTH);
    }
}
