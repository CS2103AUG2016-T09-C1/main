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



/**
 * Controller for a help page
 */
public class HelpWindow1 extends UiPart {
    
    
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "helpWindow1.fxml";
    private static final String TITLE = "Help";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;
    
    private VBox mainPane;   
    private Stage dialogStage;
    private ResultDisplay resultDisplay;
    
    @FXML
    private TableView<String> helpTable;
    @FXML
    private TableColumn<String, String> commandColumn;
    @FXML
    private TableColumn<String, String> exampleColumn;
    @FXML
    private AnchorPane resultDisplayPlaceholder;

    public static HelpWindow1 load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow1 helpWindow1 = UiPartLoader.loadUiPart(primaryStage, new HelpWindow1());
        helpWindow1.configure();
        return helpWindow1;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);
        

        dialogStage.setScene(scene);
        resultDisplay = ResultDisplay.load(dialogStage, getResultDisplayPlaceholder());
        
        String command = helpTable.getSelectionModel().getSelectedItem();
        showHelpDetail(command);
        
    }
    

    private void showHelpDetail(String command) {
        if (command != null) {
            // Fill the labels with info from the person object.
            resultDisplay.postMessage("");
        }
        
    }

    public void show() {
        dialogStage.showAndWait();
    }
    
    public AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }
    
    private void setTitle(String title) {
        dialogStage.setTitle(title);
    }
    
    private void setWindowMinSize() {
        dialogStage.setMinHeight(MIN_HEIGHT);
        dialogStage.setMinWidth(MIN_WIDTH);
    }
}
