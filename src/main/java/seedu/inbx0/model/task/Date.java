package seedu.inbx0.model.task;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.Parser;

import seedu.inbx0.commons.exceptions.IllegalValueException;

/**
 * Represents Date of a Task Event in the tasklist.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date can either be a numeric string, alphanumeric string, or alphabet string \n"
                                                        + "Example: DD/MM/YYYY format OR 3rd Jan OR next year";
    public static final Pattern DATE_NUMERIC_VALIDATION_REGEX = Pattern.compile("(?<front>[0-9 ]+)[./-](?<middle>[0-9 ]+)[./-](?<back>[0-9 ]+)");
    public static final String DATE_NUMERIC_VALIDATION_REGEX_2 = "^[0-9]{8,8}$";

    public final String value;
    public final int day;
    public final int month;
    public final int year;
    public final String dayWord;
    public final String DDMMYYYYFormat;
    
    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    
    public Date(String date) throws IllegalValueException {
        assert date != null;
        final Matcher matcher = DATE_NUMERIC_VALIDATION_REGEX.matcher(date.trim());
             
        if("".equals(date) | date.length() == 0 | date == null) {
            this.day = 0;
            this.month = 0;
            this.year = 0;
            this.value = "";
            this.dayWord = "";
            this.DDMMYYYYFormat = "";            
        }
        else if(date.matches(DATE_NUMERIC_VALIDATION_REGEX_2)) {
            
            String dateWithoutSpaces = date.replaceAll(" ","");
            int numberDate = Integer.parseInt(dateWithoutSpaces);

            if(dateWithoutSpaces.length() == 8) {
                this.day = numberDate / 1000000;
                this.month = (numberDate / 10000) % 100;
                this.year = numberDate % 10000;
                
                String nattyFormat = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);

                List<java.util.Date> dates = new Parser().parse(nattyFormat).get(0).getDates();
                SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
                SimpleDateFormat dayInWord = new SimpleDateFormat ("E, ");
                
                this.dayWord = dayInWord.format(dates.get(0));
                this.DDMMYYYYFormat = ft.format(dates.get(0));
                this.value = ft.format(dates.get(0)).replaceAll("\\D+","");
            }
            else
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        else if(matcher.matches()) {
            
            String dateFront = matcher.group("front").trim();
            String dateMiddle = matcher.group("middle").trim();
            String dateBack = matcher.group("back").trim();
            
            if(dateFront.length() == 4) {
                this.year = Integer.parseInt(dateFront);
                this.month = Integer.parseInt(dateMiddle); 
                this.day = Integer.parseInt(dateBack);
            }
            else if(dateFront.length() <= 0 | dateFront.length() > 4 | dateMiddle.length() <= 0 |
                    dateMiddle.length() > 2 | dateBack.length() < 2 | dateBack.length() > 4)
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            else {
                this.day = Integer.parseInt(dateFront);
                this.month = Integer.parseInt(dateMiddle); 
                this.year = Integer.parseInt(dateBack);
            }
            
            String nattyFormat = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
            List<java.util.Date> dates = new Parser().parse(nattyFormat).get(0).getDates();
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
            SimpleDateFormat dayInWord = new SimpleDateFormat ("E, ");
            
            this.dayWord = dayInWord.format(dates.get(0));
            this.DDMMYYYYFormat = ft.format(dates.get(0));
            
            this.value = ft.format(dates.get(0)).replaceAll("\\D+","");
            }
        else {
            try {
            List<java.util.Date> dates = new Parser().parse(date).get(0).getDates(); 
            
            SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
            SimpleDateFormat dayInWord = new SimpleDateFormat ("E, ");
            SimpleDateFormat numericFormat = new SimpleDateFormat ("ddMMyyyy");
            this.DDMMYYYYFormat = ft.format(dates.get(0));
            this.value = numericFormat.format(dates.get(0));
            int digitsOnly = Integer.parseInt(DDMMYYYYFormat.replaceAll("\\D+",""));
            this.day = digitsOnly / 1000000;
            this.month = (digitsOnly / 10000) % 100;
            this.year = digitsOnly % 10000;
            this.dayWord = dayInWord.format(dates.get(0));  

            } catch (IndexOutOfBoundsException e) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
            
        }        
    }
    
    @Override
    public String toString() {      
        return value;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getDate() {
        return value;
    }
    
    /**
     * @return a string representation of date in YYYYMMDD format
     */
    public String getDateYYYYMMDDFormat() {
        if ("".equals(value)) {
            return value;      
        }
        return value.substring(4,8) + value.substring(2,4) + value.substring(0,2);
    }
    
    public String getTotalDate() {
        return dayWord + DDMMYYYYFormat;
    }
    
    public int getDay() {
        return day;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getYear() {
        return year;
    }
}
