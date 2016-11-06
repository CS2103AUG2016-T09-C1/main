package seedu.inbx0.ui;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seedu.inbx0.model.reminder.ReminderTask;

//@@author A0139579J
public class ReminderListCard extends UiPart{

    private static final String FXML = "ReminderListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private GridPane gridPane;
    
    @FXML
    private ColumnConstraints firstColumn;

    @FXML
    private ColumnConstraints secondColumn;

    @FXML
    private ColumnConstraints thirdColumn;

    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;

    private ReminderTask reminder;
    private int displayedIndex;

    public static ReminderListCard load(ReminderTask reminder, int displayedIndex){
        ReminderListCard card = new ReminderListCard();
        card.reminder = reminder;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        id.setText(displayedIndex + ". ");
        startDate.setText(reminder.getStartDate().getTotalDate());
        startTime.setText(reminder.getStartTime().getTime()); 
        setGridPaneRatio();
    }
    
    private void setGridPaneRatio() {
    	firstColumn.setMaxWidth(200);
    	secondColumn.setMaxWidth(200);
    	thirdColumn.setMaxWidth(200);
    	firstColumn.setMinWidth(200);
    	secondColumn.setMinWidth(200);
    	thirdColumn.setMinWidth(200);
	}

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}

