package seedu.inbx0.model.task;

import java.util.Objects;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.commons.util.CollectionUtil;
import seedu.inbx0.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    
    public static final String MESSAGE_TIME_CONSTRAINTS = "The event is not possible as "
                                                           + "the end of the event is earlier or same as the start of event.";
    private Name name;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private Importance level;
    private boolean isEvent;
    private boolean isCompleted;
    private boolean isExpired;
    
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     * @throws IllegalValueException if it is an event and not valid
     */
    public Task(final Name name, final Date startDate, final Time startTime, final Date endDate, final Time endTime, final Importance level, final UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, startDate, startTime, endDate, endTime, level, tags);
        
        if ((startDate.getDate().equals("") && startTime.getTime().equals("") && endDate.getDate().equals("") && endTime.getTime().equals("")) |
            (startDate.getDate().equals("") && startTime.getTime().equals("") && !endDate.getDate().equals(""))|
            (!startDate.getDate().equals("") && !startTime.getTime().equals("") && endDate.getDate().equals("") && endTime.getTime().equals("")) |
            (!startDate.getDate().equals("") && startTime.getTime().equals("") && endDate.getDate().equals("") && endTime.getTime().equals(""))) {
            this.isEvent = false;
        } else {
            this.isEvent = true;
        }

        if (isEvent) {
            if (!isValidEvent(startDate, startTime, endDate, endTime)) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }
        }

        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.level = level;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = false;
        this.isExpired = false;

    }

    public Task(final Name name, final Date startDate, final Time startTime, final Date endDate, final Time endTime, final Importance level, final UniqueTagList tags, final boolean isCompleted, final boolean isExpired) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, startDate, startTime, endDate, endTime, level, tags, isCompleted);
        
        if ((startDate.getDate().equals("") && startTime.getTime().equals("") && endDate.getDate().equals("") && endTime.getTime().equals("")) |
            (startDate.getDate().equals("")  && startTime.getTime().equals("") && !endDate.getDate().equals(""))|
            (!startDate.getDate().equals("") && !startTime.getTime().equals("") && endDate.getDate().equals("") && endTime.getTime().equals("")) |
            (!startDate.getDate().equals("") && startTime.getTime().equals("") && endDate.getDate().equals("") && endTime.getTime().equals(""))) {
            this.isEvent = false;
        } else {
            this.isEvent = true;
        }

        if (isEvent) {
            if (!isValidEvent(startDate, startTime, endDate, endTime)) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }
        }

        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.level = level;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = isCompleted;
        this.isExpired = isExpired;

    }

    /**
     * Returns true if a given Date and Time allows it to be a valid event.
     */
    public static boolean isValidEvent(final Date startDate, final Time startTime, final Date endDate, final Time endTime) {
        boolean isValid = false;

        if ((startTime.getTime().equals("") && endTime.getTime().equals("") && !endDate.getDate().equals("") && !startDate.getDate().equals("")) | 
           (startTime.getTime().equals("") && !endTime.getTime().equals("") && !endDate.getDate().equals("") && !startDate.getDate().equals("")) |
           (!startTime.getTime().equals("") && endTime.getTime().equals("") && !endDate.getDate().equals("") && !startDate.getDate().equals(""))) {
            if (endDate.getYear() > startDate.getYear()) {
                isValid = true;
            } else if ((endDate.getYear() == startDate.getYear()) && (endDate.getMonth() > startDate.getMonth())) {
                isValid = true;
            } else if (((endDate.getYear() == startDate.getYear()) && 
                    (endDate.getMonth() == startDate.getMonth())) &&
                    (endDate.getDay() >= startDate.getDay())) {
                isValid = true;
            }
            
            return isValid;            
        }
        
        int numStartTime = Integer.parseInt(startTime.getTime().replaceAll("\\D+",""));
        int numEndTime = Integer.parseInt(endTime.getTime().replaceAll("\\D+",""));
        
        if(endDate.getYear() > startDate.getYear()) {
            isValid = true;
        } else if ((endDate.getYear() == startDate.getYear()) && (endDate.getMonth() > startDate.getMonth())) {
            isValid = true;
        } else if (((endDate.getYear() == startDate.getYear()) && 
                 (endDate.getMonth() == startDate.getMonth())) &&
                 (endDate.getDay() > startDate.getDay())) {
            isValid = true;
        } else if (((endDate.getYear() == startDate.getYear()) && 
                (endDate.getMonth() == startDate.getMonth())) &&
                (endDate.getDay() == startDate.getDay()) &&
                numEndTime > numStartTime) {
            isValid = true;
        }
           
        return isValid;         
    }

    /**
     * Copy constructor.
     * @throws IllegalValueException if it is an event and not valid
     */

    public Task(final ReadOnlyTask source) throws IllegalValueException {
        this(source.getName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getLevel(), source.getTags(), source.getIsCompleted(), source.getIsExpired());
    }

    @Override
    public final Name getName() {
        return name;
    }
    
    @Override
    public final Date getStartDate() {
        return startDate;
    }
    
    @Override
    public final Time getStartTime() {
        return startTime;
    }
    
    @Override
    public final Date getEndDate() {
        return endDate;
    }
    
    @Override
    public final Time getEndTime() {
        return endTime;
    }
    
    @Override
    public final Importance getLevel() {
        return level;
    }
    
    @Override
    public final UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public final boolean getIsCompleted() {
        return isCompleted;
    }
    
    @Override
    public final boolean getIsExpired() {
        return isExpired;
    }
    
    public final boolean getIsEvent() {
        return isEvent;
    }
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public final void setTags(final UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    public final void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public final void setStartTime(final Time startTime) {
        this.startTime = startTime;
    }

    public final void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public final void setEndTime(final Time endTime) {
        this.endTime = endTime;
    }

    public final void setLevel(final Importance level) {
        this.level = level;
    }

    public final void setCompleted(final boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    public final void setExpired(final boolean isExpired) {
        this.isExpired = isExpired;
    }

    @Override
    public final boolean equals(final Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public final int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, startTime, endDate, endTime, level, tags, isCompleted);
    }

    @Override
    public final String toString() {
        return getAsText();
    }

    public static String formatInput(final String type, final String toFormat) throws IllegalValueException{
        if ("date".equals(type)) {
            Date date = new Date(toFormat);
            return date.getDate();
        } else if ("time".equals(type)) {
            Time time = new Time(toFormat);
            return time.getTime();
        } else if ("importance".equals(type)) {
            Importance importance = new Importance(toFormat);
            return importance.getLevel();
        }
        return toFormat;
    }

}
