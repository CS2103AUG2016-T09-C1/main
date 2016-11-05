package seedu.inbx0.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.inbx0.commons.util.FxViewUtil;
import seedu.inbx0.logic.Logic;
import seedu.inbx0.logic.commands.*;
import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    private String previousCommandText;
    private Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }


    @FXML
    private void handleCommandInputChanged() {
        //Take a copy of the command text
        previousCommandText = commandTextField.getText();

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandText);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }


    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event,"Invalid command: " + previousCommandText));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
        commandTextField.positionCaret(commandTextField.getText().length());
    }
    
    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandText);
    }
    
    //@@author A0148044J
    /**
     * Fill in the command box text with the next command test
     */
    private void goToPreviousCommandText() {
        if(!logic.getPreviousCommandText().empty()) {
            String previousCommand = logic.popPreviousCommandText();
            logic.setNextCommandText(previousCommand);
            commandTextField.setText(previousCommand);
        }
    }
    
    /**
     * Fill in the command box text with the next command test
     */
    private void goToNextCommandText() {
        if(!logic.getNextCommandText().empty()) {
            String nextCommand = logic.popNextCommandText();
            logic.setPreviousCommandText(nextCommand);
            commandTextField.setText(nextCommand);
        }
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }
    
    /**
     * Fill in the command box automatically when user key in TAB
     */
    private void autoFillInCommandText() {
        String currentText = commandTextField.getText();
        if(currentText.matches("^a$|^ad$")) {
            commandTextField.setText("add");
        } else if(currentText.matches("^add$|^addt$|^addta$")){
                commandTextField.setText("addtag");
        } else if(currentText.matches("^c$|^cl$")) {
            commandTextField.setText("clr");
        } else if(currentText.matches("^de$")) {
            commandTextField.setText("del");
        } else if(currentText.matches("^del$|^delt$|^delta$")) {
                commandTextField.setText("deltag");
        } else if(currentText.matches("^do$")) {
            commandTextField.setText("done");
        } else if(currentText.matches("^ed$|^edi$")) {
            commandTextField.setText("edit");
        } else if(currentText.matches("^ex$|^exi$")) {
            commandTextField.setText("exit");
        } else if(currentText.matches("^f$|^fi$|^fin$")) {
            commandTextField.setText("find");
        } else if(currentText.matches("^h$|^he$|^hel$")) {
            commandTextField.setText("help");
        } else if(currentText.matches("^l$|^li$|^lis$")) {
            commandTextField.setText("list");
        } else if(currentText.matches("^r$|^re$")) {
            commandTextField.setText("rem");
        } else if(currentText.matches("^sa$|^sav$|^save$|^savea$")) {
            commandTextField.setText("saveas");
        } else if(currentText.matches("^se$")) {
            commandTextField.setText("sel");
        } else if(currentText.matches("^sh$|^sho$")) {
            commandTextField.setText("show");
        } else if(currentText.matches("^so$|^sor$")) {
            commandTextField.setText("sort");
        } else if(currentText.matches("^u$|^un$|^und$")) {
            commandTextField.setText("undo");
        } 
    }
    
    /**
     * Updates the command box according to key pressed on keyboard
     * TAB: auto fill in command word
     * UP: restore previous command text
     * DOWN: go to next command text
     */
    @FXML
    public void keyPressed(KeyEvent evt) {
        if(evt.getCode().equals(KeyCode.UP)) {
            goToPreviousCommandText();
            commandTextField.positionCaret(commandTextField.getText().length());
        }
        else if(evt.getCode().equals(KeyCode.DOWN)) {
            goToNextCommandText();
            commandTextField.positionCaret(commandTextField.getText().length());
        }
        else if(evt.getCode().equals(KeyCode.RIGHT)) {
            autoFillInCommandText();
            commandTextField.positionCaret(commandTextField.getText().length());
        }
    }
    //@@author

}
