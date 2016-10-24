package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.inbx0.model.reminder.ReminderTask;

public class ReminderCard extends UiPart{

    private static final String FXML = "ReminderListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;

    private ReminderTask reminder;
    private int displayedIndex;

    public static ReminderCard load(ReminderTask reminder, int displayedIndex){
        ReminderCard card = new ReminderCard();
        card.reminder = reminder;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        id.setText(displayedIndex + ". ");
        startDate.setText(reminder.getStartDate().getTotalDate());
        startTime.setText(reminder.getStartTime().value); 
             
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

