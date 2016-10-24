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

public class HelpWindow1 extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(HelpWindow1.class);
    private static final String FXML = "HelpWindow1.fxml";
    private static final String ICON = "/images/help_icon.png";
    private static final String TITLE = "Help!";
    
    private Stage helpStage;
    private VBox mainPane;
    
    public static HelpWindow1 load(Stage primaryStage){
        logger.fine("Showing help window");
        HelpWindow1 helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow1());
        helpWindow.configure();
        return helpWindow;
    }
    
    private void configure(){     
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        helpStage = createDialogStage(TITLE, null, scene);
        setIcon(helpStage, ICON);
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
        helpStage.showAndWait();
    }
    
    @FXML
    public void handleCloseHelpWindow() {
        helpStage.close();
    }
    
    @FXML
    public void keyPressed(KeyEvent evt) {
        if(evt.getCode().equals(KeyCode.ENTER)) {
            handleCloseHelpWindow();
        }
        
    }
}