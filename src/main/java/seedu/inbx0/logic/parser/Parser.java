package seedu.inbx0.logic.parser;

import static seedu.inbx0.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inbx0.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.commons.util.StringUtil;
import seedu.inbx0.logic.commands.*;
import seedu.inbx0.model.task.Task;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>[^&]+(?:\\s+)*)"); // one or more keywords separated by whitespace
    
    private static final Pattern CONDITIONAL_KEYWORDS_ARGS_FORMAT = // '&' ampersand are reserved for AND relational indicators
            Pattern.compile("(?<keywords>.*[&].*)"); // one or more keywords separated by ampersand
    
    private static final Pattern SORT_TASK_LIST_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)?)"); // one or two keywords separated by whitespace
   
    private static final Pattern INDEX_NUMBER_ARGS_FORMAT = 
            Pattern.compile("(?<numbers>[0-9]+(?:\\s+[0-9]+)*)"); // one or more index numbers separated by whitespace
    
    private static final Pattern INDEX_NUM_TO_INDEX_NUM_ARGS_FORMAT =
            Pattern.compile("(?<first>[0-9]+) to (?<last>[0-9]+)");
 
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + " st/(?<startTime>[^/]+)"
                    + " e/(?<endDate>[^$]+)"
                    + " et/(?<endTime>[^/]+)"
                    + " i/(?<level>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    private static final Pattern TASK_DATA_ARGS_FORMAT_2 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + " st/(?<startTime>[^/]+)"
                    + " e/(?<endDate>[^$]+)"
                    + " et/(?<endTime>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern TASK_DATA_ARGS_FORMAT_3 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + " e/(?<endDate>[^$]+)"
                    + " i/(?<level>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern TASK_DATA_ARGS_FORMAT_4 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + " e/(?<endDate>[^$]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");  
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " e/(?<endDate>[^$]+)"
                    + " et/(?<endTime>[^/]+)"
                    + " i/(?<level>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT_2 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " e/(?<endDate>[^$]+)"
                    + " et/(?<endTime>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT_3 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " e/(?<endDate>[^$]+)"
                    + " i/(?<level>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT_4 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " e/(?<endDate>[^$]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " i/(?<level>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT_2 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    private static final Pattern START_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + " st/(?<startTime>[^/]+)"
                    + " i/(?<level>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern START_TASK_DATA_ARGS_FORMAT_2 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + " st/(?<startTime>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern START_TASK_DATA_ARGS_FORMAT_3 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + " i/(?<level>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern START_TASK_DATA_ARGS_FORMAT_4 = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " s/(?<startDate>[^$]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");
    private static final Pattern ADD_TAGS_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<targetIndex>\\S+)(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    private static final Pattern DATE_TIME_FORMAT = 
            Pattern.compile("(?<date>[0-9 ]+[./-][0-9 ]+[./-][0-9]+)"
                    + "(?<time>(?: [^/]+))");
    private static final Pattern DATE_TIME_FORMAT_2 = 
            Pattern.compile("(?<time>(?:[^/]+))"
                    + "(?<date> [0-9 ]+[./-][0-9 ]+[./-][0-9]+)");
    private static final Pattern DATE_TIME_FORMAT_3 = 
            Pattern.compile("(?<date>[0-9]{8})"
                    + "(?<time>(?: [^/]+))");
    private static final Pattern DATE_TIME_FORMAT_4 = 
            Pattern.compile("(?<time>(?:[^/]+))"
                    + "(?<date> [0-9]{8})");
    private static final Pattern DATE_TIME_FORMAT_5 = 
            Pattern.compile("(?<date>[0-9]{8})");
    private static final Pattern DATE_TIME_FORMAT_6 = 
            Pattern.compile("(?<date>[0-9 ]+[./-][0-9 ]+[./-][0-9]+)");
    
    
    private static final Pattern TASK_EDIT_DATA_ARGS_FORMAT = Pattern.compile("(?<targetIndex>\\S+)(?<arguments>.*)");
    private static final CharSequence NAME = "n/";
    private static final CharSequence START_DATE = "s/";
    private static final CharSequence START_TIME = "st/";
    private static final CharSequence END_DATE = "e/";
    private static final CharSequence END_TIME = "et/";
    private static final CharSequence IMPORTANCE = "i/";
    private static final CharSequence TAG = "t/";
    
   
    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);
            
        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case SortCommand.COMMAND_WORD:
            return prepareSort(arguments);
            
        case TagCommand.COMMAND_WORD:
            return prepareTag(arguments);
        
        case MarkCompleteCommand.COMMAND_WORD:
            return prepareMarkComplete(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher2 = TASK_DATA_ARGS_FORMAT_2.matcher(args.trim());
        final Matcher matcher3 = TASK_DATA_ARGS_FORMAT_3.matcher(args.trim());
        final Matcher matcher4 = TASK_DATA_ARGS_FORMAT_4.matcher(args.trim());
        final Matcher matcher5 = DEADLINE_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher6 = DEADLINE_TASK_DATA_ARGS_FORMAT_2.matcher(args.trim());       
        final Matcher matcher7 = DEADLINE_TASK_DATA_ARGS_FORMAT_3.matcher(args.trim());
        final Matcher matcher8 = DEADLINE_TASK_DATA_ARGS_FORMAT_4.matcher(args.trim());     
        final Matcher matcher9 = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher10 = FLOATING_TASK_DATA_ARGS_FORMAT_2.matcher(args.trim());
        final Matcher matcher11 = START_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher12 = START_TASK_DATA_ARGS_FORMAT_2.matcher(args.trim());
        final Matcher matcher13 = START_TASK_DATA_ARGS_FORMAT_3.matcher(args.trim());
        final Matcher matcher14 = START_TASK_DATA_ARGS_FORMAT_4.matcher(args.trim());
        
        // Validate arg string format
        if (!matcher.matches() && !matcher2.matches() && !matcher3.matches() && !matcher4.matches() && !matcher5.matches()
            && !matcher6.matches() && !matcher7.matches() && !matcher8.matches() && !matcher9.matches() && !matcher10.matches()
            && !matcher11.matches() && !matcher12.matches() && !matcher13.matches()&& !matcher14.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            if(matcher.matches()) {
            return new AddCommand(
                    matcher.group("name"),
                    matcher.group("startDate"),
                    matcher.group("startTime"),
                    matcher.group("endDate"),
                    matcher.group("endTime"),
                    matcher.group("level"),
                    getTagsFromArgs(matcher.group("tagArguments"))
                    );
            } 
            else if(matcher2.matches()) {
                return new AddCommand(
                        matcher2.group("name"),
                        matcher2.group("startDate"),
                        matcher2.group("startTime"),
                        matcher2.group("endDate"),
                        matcher2.group("endTime"),
                        getTagsFromArgs(matcher2.group("tagArguments"))
                        );
            }
            else if(matcher3.matches()) {
                
               String argStartDate = dateParse(matcher3.group("startDate"));
               String argStartTime = timeParse(matcher3.group("startDate"));
               String argEndDate = dateParse(matcher3.group("endDate"));
               String argEndTime = timeParse(matcher3.group("endDate"));
                
                return new AddCommand(
                        matcher3.group("name"),
                        argStartDate,
                        argStartTime,
                        argEndDate,
                        argEndTime,
                        matcher3.group("level"),
                        getTagsFromArgs(matcher3.group("tagArguments"))
                        );
            }
            else if(matcher4.matches()) {
                String argStartDate = dateParse(matcher4.group("startDate"));
                String argStartTime = timeParse(matcher4.group("startDate"));
                String argEndDate = dateParse(matcher4.group("endDate"));
                String argEndTime = timeParse(matcher4.group("endDate"));
                
                return new AddCommand(
                        matcher4.group("name"),
                        argStartDate,
                        argStartTime,
                        argEndDate,
                        argEndTime,
                        getTagsFromArgs(matcher4.group("tagArguments"))
                        );
            }
            else if(matcher5.matches()) {
                return new AddCommand(
                        matcher5.group("name"),
                        matcher5.group("endDate"),
                        matcher5.group("endTime"),
                        matcher5.group("level"),
                        getTagsFromArgs(matcher5.group("tagArguments"))
                        );
            }
            else if(matcher6.matches()) {
                return new AddCommand(
                        matcher6.group("name"),
                        matcher6.group("endDate"),
                        matcher6.group("endTime"),
                        getTagsFromArgs(matcher6.group("tagArguments"))
                        );
            }
            else if(matcher7.matches()) {
                String argEndDate = dateParse(matcher7.group("endDate"));
                String argEndTime = timeParse(matcher7.group("endDate"));
                return new AddCommand(
                        matcher7.group("name"),
                        argEndDate,
                        argEndTime,
                        matcher7.group("level"),
                        getTagsFromArgs(matcher7.group("tagArguments"))
                        );
            }
            else if(matcher8.matches()) {
                String argEndDate = dateParse(matcher8.group("endDate"));
                String argEndTime = timeParse(matcher8.group("endDate"));
                return new AddCommand(
                        matcher8.group("name"),
                        argEndDate,
                        argEndTime,
                        getTagsFromArgs(matcher8.group("tagArguments"))
                        );
            }
            else if(matcher9.matches()) {
                return new AddCommand(
                        matcher9.group("name"),
                        matcher9.group("level"),
                        getTagsFromArgs(matcher9.group("tagArguments"))
                        );
            }
            else if(matcher10.matches()) {
                return new AddCommand(
                        matcher10.group("name"),
                        getTagsFromArgs(matcher10.group("tagArguments"))
                        );
            
            }
            else if(matcher11.matches()) {
                return new AddCommand(
                        matcher11.group("name"),
                        matcher11.group("startDate"),
                        matcher11.group("startTime"),
                        "",
                        "",
                        matcher11.group("level"),
                        getTagsFromArgs(matcher11.group("tagArguments"))
                        );
            }
            else if(matcher12.matches()) {
                return new AddCommand(
                        matcher12.group("name"),
                        matcher12.group("startDate"),
                        matcher12.group("startTime"),
                        "",
                        "",
                        getTagsFromArgs(matcher12.group("tagArguments"))
                        );
            }
            else if(matcher13.matches()) {
                String argStartDate = dateParse(matcher13.group("startDate"));
                String argStartTime = timeParse(matcher13.group("startDate"));
                return new AddCommand(
                        matcher13.group("name"),
                        argStartDate,
                        argStartTime,
                        "",
                        "",
                        matcher13.group("level"),
                        getTagsFromArgs(matcher13.group("tagArguments"))
                        );
            }
            else {
                String argStartDate = dateParse(matcher14.group("startDate"));
                String argStartTime = timeParse(matcher14.group("startDate"));
                return new AddCommand(
                        matcher14.group("name"),
                        argStartDate,
                        argStartTime,
                        "",
                        "",
                        getTagsFromArgs(matcher14.group("tagArguments"))
                        );
            }
                       
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private String timeParse(String dateTimeString) {
        Matcher matcher1 = DATE_TIME_FORMAT.matcher(dateTimeString.trim());
        Matcher matcher2 = DATE_TIME_FORMAT_2.matcher(dateTimeString.trim());
        Matcher matcher3 = DATE_TIME_FORMAT_3.matcher(dateTimeString.trim());
        Matcher matcher4 = DATE_TIME_FORMAT_4.matcher(dateTimeString.trim());
        Matcher matcher5 = DATE_TIME_FORMAT_5.matcher(dateTimeString.trim());
        Matcher matcher6 = DATE_TIME_FORMAT_6.matcher(dateTimeString.trim());
               
        if(matcher1.matches()) 
            return matcher1.group("time");
        
        if(matcher2.matches())
            return matcher2.group("time");
        
        if(matcher3.matches()) 
            return matcher3.group("time");
        
        if(matcher4.matches()) 
            return matcher4.group("time");
        
        if(matcher5.matches()) 
            return "";
        
        if(matcher6.matches()) 
            return "";
        
        return dateTimeString;
    }

    private String dateParse(String dateTimeString) {
        Matcher matcher1 = DATE_TIME_FORMAT.matcher(dateTimeString.trim());
        Matcher matcher2 = DATE_TIME_FORMAT_2.matcher(dateTimeString.trim());
        Matcher matcher3 = DATE_TIME_FORMAT_3.matcher(dateTimeString.trim());
        Matcher matcher4 = DATE_TIME_FORMAT_4.matcher(dateTimeString.trim());
               
        if(matcher1.matches()) 
            return matcher1.group("date");
        
        if(matcher2.matches())
            return matcher2.group("date");
        
        if(matcher3.matches()) 
            return matcher3.group("date");
        
        if(matcher4.matches()) 
            return matcher4.group("date");
        
        return dateTimeString;
    }


    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }
    
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        final Matcher matcher = TASK_EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        String [] argumentsForEdit = new String [6];
        
        String arguments = matcher.group("arguments");
        
        if(arguments.length() == 0)
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        
        if(arguments.trim().equals("float")) {
            argumentsForEdit[1] = "";
            argumentsForEdit[2] = "";
            argumentsForEdit[3] = "";
            argumentsForEdit[4] = "";
            
            try {
                return new EditCommand(
                        index.get(),
                        argumentsForEdit,
                        null
                );
            } catch (IllegalValueException e) {
                return new IncorrectCommand(e.getMessage());
            }
        }
        
        
        Pattern editArguments = scanArgumentsAndBuildRegex(arguments);
        
        if(editArguments == null) {
            return new IncorrectCommand(
                    String.format(EditCommand.MESSAGE_INVALID_ARGUMENTS, EditCommand.MESSAGE_USAGE));
        }
        
        Matcher matcher2 = editArguments.matcher(arguments);
        if(matcher2.matches()) {
            if(arguments.contains(NAME)) 
                argumentsForEdit[0] = matcher2.group("name");
            if(arguments.contains(START_DATE) && !arguments.contains(START_TIME)) {
                 if(matcher2.group("startDate").equals("nil")) {
                     argumentsForEdit[1] = "";
                     argumentsForEdit[2] = "";
                 }
                 else {
                     argumentsForEdit[1] = dateParse(matcher2.group("startDate"));
                     argumentsForEdit[2] = timeParse(matcher2.group("startDate"));
                 }
            }
            else if(arguments.contains(START_DATE) && arguments.contains(START_TIME)) {
                if(matcher2.group("startDate").equals("nil") && matcher2.group("startTime").equals("nil")) {
                    argumentsForEdit[1] = "";
                    argumentsForEdit[2] = "";                   
                }
                else {
                argumentsForEdit[1] = matcher2.group("startDate");
                argumentsForEdit[2] = matcher2.group("startTime");
                }
            }
            else if(arguments.contains(START_TIME)) {
                if(matcher2.group("startTime").equals("nil")) {
                    argumentsForEdit[2] = "";
                }
                else
                    argumentsForEdit[2] = matcher2.group("startTime");
            }
                
            
            if(arguments.contains(END_DATE) && !arguments.contains(END_TIME)) { 
                if(matcher2.group("endDate").equals("nil")) {
                    argumentsForEdit[3] = "";
                    argumentsForEdit[4] = "";
                }
                else {
                 argumentsForEdit[3] = dateParse(matcher2.group("endDate"));
                 argumentsForEdit[4] = timeParse(matcher2.group("endDate"));
                }
            }
            else if(arguments.contains(END_DATE) && arguments.contains(END_TIME)) {        
                if(matcher2.group("endDate").equals("nil") && matcher2.group("endTime").equals("nil")) {
                    argumentsForEdit[3] = "";
                    argumentsForEdit[4] = "";              
                }
                else {
                 argumentsForEdit[3] = matcher2.group("endDate");
                 argumentsForEdit[4] = matcher2.group("endTime");
                }
            }
            else if (arguments.contains(END_TIME)) {
                if(matcher2.group("endTime").equals("nil")) {
                    argumentsForEdit[4] = "";
                }
                else {
                 argumentsForEdit[4] = timeParse(matcher2.group("endTime"));
                }
            }
            
            if(arguments.contains(IMPORTANCE)) {
                if(matcher2.group("level").equals("nil")) {
                    argumentsForEdit[5] = "";   
                }
                else {
                argumentsForEdit[5] = matcher2.group("level");
                }
            }
        }  
          try{   
             return new EditCommand(
                    index.get(),
                    argumentsForEdit,
                    getTagsFromArgs(matcher2.group("tagArguments"))
            );            
            } catch (IllegalValueException ive) {
        return new IncorrectCommand(ive.getMessage());
            }
    }
    
    private Pattern scanArgumentsAndBuildRegex(String arguments) {
        String regex = "";
        
        if (arguments.contains(NAME)) {
            regex += " n/(?<name>[^/]+)";
        }
        if(arguments.contains(START_DATE)) {
            regex += " s/(?<startDate>[^$]+)";
        }
        if(arguments.contains(START_TIME)) {
            regex += " st/(?<startTime>[^/]+)";
        }
        if(arguments.contains(END_DATE)) {
            regex += " e/(?<endDate>[^$]+)";
        }
        
        if(arguments.contains(END_TIME)) {
            regex += " et/(?<endTime>[^/]+)";
        }
        
        if(arguments.contains(IMPORTANCE)) {
            regex += " i/(?<level>[^/]+)";            
        }
                
        regex += "(?<tagArguments>(?: t/[^/]+)*)";
        
        Pattern editArguments = Pattern.compile(regex);
        
        Matcher matcher = editArguments.matcher(arguments);
        
        if(matcher.matches())
            return editArguments;
        else
            return null;                
    }
      
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author A0139481Y
    private Command prepareTag(String args){
    	final Matcher matcher = ADD_TAGS_ARGS_FORMAT.matcher(args.trim());
    	if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
    	
    	Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
    	System.out.println(index);
    	if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
    	try{   
            return new TagCommand(
                   index.get(),
                   getTagsFromArgs(matcher.group("tagArguments"))
           );            
           } catch (IllegalValueException ive) {
        	   return new IncorrectCommand(ive.getMessage());
           }
    }
    
    /**
     * Parses arguments in the context of the Mark Complete task command.
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMarkComplete(String args) {
        final Matcher matcher = INDEX_NUMBER_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher2 = INDEX_NUM_TO_INDEX_NUM_ARGS_FORMAT.matcher(args.trim());
        
        if (!matcher.matches() && !matcher2.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkCompleteCommand.MESSAGE_USAGE));
        }
        
        Set<Integer> indexNumSet;
        
        if(matcher2.matches()) {
            Integer front = Integer.valueOf(matcher2.group("first"));
            Integer back = Integer.valueOf(matcher2.group("last"));
            
            if(back < front | front == back) {
                return new IncorrectCommand(String.format(MarkCompleteCommand.MESSAGE_INVALID_ARGUMENTS,
                        MarkCompleteCommand.MESSAGE_USAGE));
            }
            Integer[] indexNumInteger = new Integer[back - front + 1];
            int index = 0;
            for(Integer i = front; i <= back; i++) {
               
                indexNumInteger[index] = i;
                index++;
            }
            
            indexNumSet = new HashSet<Integer>(Arrays.asList(indexNumInteger));
            
            
        }
        else {
            final String[] indexNumbers = matcher.group("numbers").split("\\s+");
            final int[] indexNum = Arrays.asList(indexNumbers).stream().mapToInt(Integer::parseInt).toArray();
            Integer[] indexNumInteger = IntStream.of(indexNum).boxed().toArray(Integer[]::new);
            indexNumSet = new HashSet<Integer>(Arrays.asList(indexNumInteger));
        }
        try {
            
            return new MarkCompleteCommand(indexNumSet);
        } catch (IllegalValueException e) {
            
            return new IncorrectCommand(e.getMessage());
        }
    }
    /**
     * Parses arguments in the context of the sort task command.
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSort(String args){
        System.out.println(args.trim());
        final Matcher matcher = SORT_TASK_LIST_ARGS_FORMAT.matcher(args.trim());
        if(!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        
        String[] keywords = matcher.group("keywords").split("\\s+");
        String type = null;
        boolean defaultOrder = true;
        if(keywords[0].equalsIgnoreCase("s")||keywords[0].equalsIgnoreCase("start")) {
            type = "Start Time";
        }
        else if(keywords[0].equalsIgnoreCase("e")||keywords[0].equalsIgnoreCase("end")) {
            type = "End Time";
        } 
        else if(keywords[0].equalsIgnoreCase("i")||keywords[0].equalsIgnoreCase("importance")) {
            type = "Importance";
        } 
        else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        if(keywords.length == 2) {
            if(keywords[1].equalsIgnoreCase("DESC") || keywords[1].equalsIgnoreCase("descending") || keywords[1].equalsIgnoreCase("descend")) {
                defaultOrder = (type.equals("Importance")) ? true : false;
            }
            else if(keywords[1].equalsIgnoreCase("ASC") || keywords[1].equalsIgnoreCase("ascending") || keywords[1].equalsIgnoreCase("ascend")) {
                defaultOrder = (type.equals("Importance")) ? false : true;
            }
            else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }            
        return new SortCommand(type, defaultOrder);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author generated
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author generated
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher1 = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher2 = CONDITIONAL_KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        boolean andRelation = false;
        String[] keywords = null; 
        Set<String> keywordSet = new HashSet<>();
        if (!matcher1.matches() && !matcher2.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
        
        if(matcher2.matches()) {
            andRelation = true;
            keywords = matcher2.group("keywords").split("&");
            try{
                for(String keyword: keywords) {
                    keywordSet.add(convertKeywordsIntoDefinedFormat(keyword));
                }
            }catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
        else{
            andRelation = false;
            keywords = matcher1.group("keywords").split("\\s+");
            try{
                for(String keyword: keywords) {
                    keywordSet.add(convertKeywordsIntoDefinedFormat(keyword));
                }
            }catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
        try{
            return new FindCommand(andRelation, keywordSet);
        }catch(IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }        
    }
        /*
        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final int type;
        if(keywords[0].contains(START_DATE)) {
            type = 1;
            keywords[0] = keywords[0].replace(START_DATE, "").replace("'"," ");
        }
        else if(keywords[0].contains(END_DATE)) {
            type = 2;
            keywords[0] = keywords[0].replace(END_DATE, "").replace("'"," ");
        }
        else if(keywords[0].contains(IMPORTANCE)) {
            type = 3;
            keywords[0] = keywords[0].replace(IMPORTANCE, "");
        }
        else if(keywords[0].contains(TAG)) {
            type = 4;
            keywords[0] = keywords[0].replace(TAG, "");
        }
        else {
            type = 0;
        }
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        try {
            return new FindCommand(type, keywordSet);
		} catch (IllegalValueException ive) {
		    return new IncorrectCommand(ive.getMessage());
		}
    }*/
    
    private String convertKeywordsIntoDefinedFormat(String keyword) throws IllegalValueException{
        String convertedKeyword = null;
        if(keyword.contains(START_DATE)) {
            convertedKeyword = keyword.replace(START_DATE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("date", convertedKeyword);
            convertedKeyword = " Start Date: " + convertedKeyword;
        }
        else if(keyword.contains(START_TIME)) {
            convertedKeyword = keyword.replace(START_TIME, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("time", convertedKeyword);
            convertedKeyword = " Start Time: " + convertedKeyword;
        }        
        else if(keyword.contains(END_DATE)) {
            convertedKeyword = keyword.replace(END_DATE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("date", convertedKeyword);
            convertedKeyword = " End Date: " + convertedKeyword;
        }
        else if(keyword.contains(END_TIME)) {
            convertedKeyword = keyword.replace(END_TIME, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("time", convertedKeyword);
            convertedKeyword = " End Time: " + convertedKeyword;
        }
        else if(keyword.contains(IMPORTANCE)) {
            convertedKeyword = keyword.replace(IMPORTANCE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("importance", convertedKeyword);
            convertedKeyword = " Importance: " + convertedKeyword;
        }
        else if(keyword.contains(TAG)) {
            convertedKeyword = keyword.replace(TAG, "").trim().replace("'", " ");
            convertedKeyword = " Tags: [" + convertedKeyword + "]";
        }
        else {
            convertedKeyword = keyword.replace(NAME, "").trim().replace("'", " ");
        }
        return convertedKeyword;
    }

    /**
     * Parses arguments in the context of the list task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author generated
    private Command prepareList(String arguments) {
        if (arguments.length() == 0)
            return new ListCommand();
        else {
            try {
                return new ListCommand(arguments);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListCommand.MESSAGE_USAGE));
            }
        }       
    }

}