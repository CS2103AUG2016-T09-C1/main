package seedu.inbx0.model.task;

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
     * Formats the task as all possible keywords containing all details for find command.
     * @author A0148044J
     */
    
    default Set<String> getAsTextSet() {
        Set<String> textSet = new HashSet<>();
        for(String nameString: getName().getName().split("\\s+")) {
            textSet.add(nameString.toLowerCase());
            textSet.add(" Name: " + nameString.toLowerCase());
        }
        textSet.add(getName().getName().toLowerCase());
        textSet.add(" Start Date: " + getStartDate().getDate());
        textSet.add(getStartDate().getDate());
        textSet.add(" Start Time: " + getStartTime().getTime());
        textSet.add(getStartTime().getTime());
        textSet.add(" End Date: " + getEndDate().getDate());
        textSet.add(getEndDate().getDate());
        textSet.add(" End Time: " + getEndTime().getTime());
        textSet.add(getEndTime().getTime());
        textSet.add(" Importance: " + getLevel().getLevel());
        textSet.add(getLevel().getLevel()); 
        for(String tagString: tagsString().split("\\s+")) {
            textSet.add(" Tags: " + tagString.toLowerCase());
            textSet.add(tagString.toLowerCase());
        }
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
