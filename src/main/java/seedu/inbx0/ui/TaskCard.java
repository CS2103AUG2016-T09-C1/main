package seedu.inbx0.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
     /*   name.setWrapText(true);
        id.setWrapText(true);
        startDate.setWrapText(true);
        startTime.setWrapText(true);
        endDate.setWrapText(true);
        endTime.setWrapText(true);
        tags.setWrapText(true);*/
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
            cardPane.setStyle("-fx-background-color: rgba(124, 252, 0, 0.5);");
        else if(task.getLevel().getNumberLevel() == 1)
            cardPane.setStyle("-fx-background-color: rgba(124, 252, 0, 1);");
        else if(task.getLevel().getNumberLevel() == 2 && task.getIsExpired() == true && task.getIsEvent() == true)
            cardPane.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5);");
        else if(task.getLevel().getNumberLevel() == 2)
            cardPane.setStyle("-fx-background-color: rgba(255, 255, 0, 1);");
        else if(task.getLevel().getNumberLevel() == 3 && task.getIsExpired() == true && task.getIsEvent() == true)
            cardPane.setStyle("-fx-background-color: rgba(255, 69, 0, 0.5);");
        else if(task.getLevel().getNumberLevel() == 3)
            cardPane.setStyle("-fx-background-color: rgba(255, 69, 0, 1);");
        
        if(task.getIsCompleted()) {
            isCompleted.setText("\u02713");
            isCompleted.setStyle("-fx-background-color: GREEN;");
        }else {
            isCompleted.setText("\ud800\udc35");
            isCompleted.setStyle("-fx-background-color: RED;");
        }   /* if(task.getIsExpired() == true && task.getIsEvent() == true) {
                cardPane.setStyle("-fx-background-color: grey;");
            } else {
                cardPane.setStyle("-fx-background-color: black;");
            }
            name.setStyle("-fx-text-fill: white;");
            id.setStyle("-fx-text-fill: white;");
            startDate.setStyle("-fx-text-fill: white;");
            startTime.setStyle("-fx-text-fill: white;");
            endDate.setStyle("-fx-text-fill: white;");
            endTime.setStyle("-fx-text-fill: white;");
            tags.setStyle("-fx-text-fill: white;");       */    
             
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
