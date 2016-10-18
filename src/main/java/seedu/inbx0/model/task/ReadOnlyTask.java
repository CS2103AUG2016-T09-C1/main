package seedu.inbx0.model.task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.inbx0.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the tasklist.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Date getStartDate();
    Time getStartTime();
    Date getEndDate();
    Time getEndTime();
    Importance getLevel();
    boolean getIsCompleted();
    
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndDate().equals(this.getEndDate())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Importance: ")
                .append(getLevel())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }  
    
    /**
     * Formats the task as text set containing all details.
     */
    
    default Set<String> getAsTextSet() {
        String[] textString = new String[30];
        textString[0] = getName().getName();
        textString[1] = " Start Date: " + getStartDate().getDate();
        textString[2] = getStartDate().getDate();
        textString[3] = " Start Time: " + getStartTime().getTime();
        textString[4] = getStartTime().getTime();
        textString[5] = " End Date: " + getEndDate().getDate();
        textString[6] = getEndDate().getDate();
        textString[7] = " End Time: " + getEndTime().getTime();
        textString[8] = getEndTime().getTime();
        textString[9] = " Importance: " + getLevel().getLevel();
        textString[10] = getLevel().getLevel(); 
        String tags = tagsString();
        String[] tagString = tags.split("\\s+");
        for(int i=0; i<tagString.length; i++) {
            textString[11+2*i] = " Tags: " + tagString[i];
            textString[11+2*i+1] = tagString[i];
        }
        
        Set<String> textSet = new HashSet<>(Arrays.asList(textString));
        return textSet;
    }
    
    
    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }

}
