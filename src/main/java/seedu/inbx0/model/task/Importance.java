package seedu.inbx0.model.task;

import java.util.Arrays;

import seedu.inbx0.commons.exceptions.IllegalValueException;

//@@author A0139579J
/**
 * Represents Importance of a Task in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidImportance(String)}
 */
public class Importance {
    
    public static final String MESSAGE_IMPORTANCE_CONSTRAINTS = "Importance has to be alphabetical format and has three levels (green, yellow, red) \n"
                                                            + "Example: G OR yellow OR r";;
    public static final String IMPORTANCE_VALIDATION_REGEX = "[a-zA-Z]+";
    public static final int NUM_OF_STRINGS_IN_ALLOWED_IMPORTANCE_NAMES = 12;
    public static final String [] ALLOWED_IMPORTANCE_NAMES = new String [] {
                                                              "g", "G", "green", "Green",
                                                              "y", "Y", "yellow", "Yellow",
                                                               "r", "R", "red", "Red"                                                            
                                                             };
    
    public final String value;
    public final int level;
    
    /**
     * Validates given importance.
     *
     * @throws IllegalValueException if given start importance string is invalid.
     */
    
    public Importance(String importance) throws IllegalValueException {
        assert importance != null;
        String trimmedImportance = importance.trim();
        if("".equals(trimmedImportance) | trimmedImportance.length() == 0 | trimmedImportance == null) {
            this.value = "";
            this.level = 0;
        }
        else {
            if (!isValidImportance(trimmedImportance)) {
                throw new IllegalValueException(MESSAGE_IMPORTANCE_CONSTRAINTS);
            }
            else {
                this.value = changeStringIntoProperColorName(trimmedImportance);
        
                if ("Green".equals(value))
                    level = 1;
                else if("Yellow".equals(value))
                    level = 2;
                else if ("Red".equals(value))
                    level = 3;
                else
                    level = 0;
                } 
        }
    }
    
    private String changeStringIntoProperColorName(String trimmedImportance) {
        String importance = trimmedImportance;
        if("g".equals(importance) | "G".equals(importance) | "green".equals(importance))
            importance = "Green";
        else if ("y".equals(importance) | "Y".equals(importance) | "yellow".equals(importance))
            importance = "Yellow";
        else if ("r".equals(importance) | "R".equals(importance) | "red".equals(importance))
            importance = "Red";
        return importance;
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidImportance(String test) {
        
        return (test.matches(IMPORTANCE_VALIDATION_REGEX) && Arrays.asList(ALLOWED_IMPORTANCE_NAMES).contains(test));
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getLevel() {
        return value;
    }
    
    public int getNumberLevel() {
        return level;
    }
}
