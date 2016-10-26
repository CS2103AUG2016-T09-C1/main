package seedu.inbx0.ui;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.inbx0.model.reminder.ReminderTask;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label tags;
    @FXML
    private Label isCompleted;
    @FXML
    private Label hasReminders;
    //@FXML
    //private Label isExpired;

    private ReadOnlyTask task;
    private int displayedIndex;

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setWrapText(true);
        id.setWrapText(true);
        startDate.setWrapText(true);
        startTime.setWrapText(true);
        endDate.setWrapText(true);
        endTime.setWrapText(true);
        tags.setWrapText(true);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startDate.setText(task.getStartDate().getTotalDate());
        startTime.setText(task.getStartTime().value);
        endDate.setText(task.getEndDate().getTotalDate());
        endTime.setText(task.getEndTime().value);
        tags.setText(task.tagsString());
        //isCompleted.setText("isCompleted: " + task.getIsCompleted());
        
        /*if((task.getStartDate().value.equals("") && task.getStartTime().value.equals("") && !task.getEndDate().value.equals("") && !task.getEndTime().value.equals("")) |
           (task.getStartDate().value.equals("") && task.getStartTime().value.equals("") && !task.getEndDate().value.equals("") && task.getEndTime().value.equals("")))
            isExpired.setText("isExpired: " + task.getIsExpired());
        else
            isExpired.setText("");
        */
        if(task.getLevel().getNumberLevel() == 1 && task.getIsExpired() == true && task.getIsEvent() == true) 
            cardPane.setStyle("-fx-background-color: rgba(0, 255, 0, 0.5);");
        else if(task.getLevel().getNumberLevel() == 1)
            cardPane.setStyle("-fx-background-color: rgba(0, 255, 0, 0.8);");
        else if(task.getLevel().getNumberLevel() == 2 && task.getIsExpired() == true && task.getIsEvent() == true)
            cardPane.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5);");
        else if(task.getLevel().getNumberLevel() == 2)
            cardPane.setStyle("-fx-background-color: rgba(255, 255, 0, 0.8);");
        else if(task.getLevel().getNumberLevel() == 3 && task.getIsExpired() == true && task.getIsEvent() == true)
            cardPane.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
        else if(task.getLevel().getNumberLevel() == 3)
            cardPane.setStyle("-fx-background-color: rgba(255, 0, 0, 0.8);");
        
        if(task.getIsCompleted()) {
            isCompleted.setText("\u2713");
            isCompleted.setStyle("-fx-font-size: 24;");
        }else {
            isCompleted.setText("\u2717");
            isCompleted.setStyle("-fx-font-size: 24;");
        }  
        hasReminders.setText("");
        UniqueReminderList reminders = task.getReminders();
        Iterator<ReminderTask> check = reminders.iterator();
        while(check.hasNext()) {
            if(check.next().getIsAlive() == true) {
               hasReminders.setText("\uD83D\uDD14");
               break;
            }
        }
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
