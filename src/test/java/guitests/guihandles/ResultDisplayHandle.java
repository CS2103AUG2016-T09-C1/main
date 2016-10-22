package guitests.guihandles;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.inbx0.TestApp;
import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.testutil.TestUtil;

/**
 * A handler for the ResultDisplay of the UI
 */
public class ResultDisplayHandle extends GuiHandle {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getText() {
        return getResultDisplay().getText();
    }

    private TextArea getResultDisplay() {
        return (TextArea) getNode(RESULT_DISPLAY_ID);
    }
    
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getResultDisplay());
        guiRobot.clickOn(point.getX(), point.getY());
    }
    
}
