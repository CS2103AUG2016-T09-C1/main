package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.logging.Logger;
import seedu.inbx0.commons.core.LogsCenter;

public class HelpWindow extends UiPart {
    
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final String ICON = "/images/help_icon.png";
    private static final String TITLE = "Help";
    
    private Stage helpStage;
    private VBox mainPane;
    
    public static HelpWindow load(Stage primaryStage){
        logger.fine("Showing help window");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
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