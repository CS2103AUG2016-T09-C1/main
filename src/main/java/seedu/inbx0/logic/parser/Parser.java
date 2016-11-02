package seedu.inbx0.logic.parser;

import static seedu.inbx0.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inbx0.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import seedu.inbx0.commons.core.Config;
import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.commons.util.StringUtil;
import seedu.inbx0.logic.commands.*;
import seedu.inbx0.model.task.Task;
import seedu.inbx0.model.task.Time;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern NORMAL_KEYWORDS_ARGS_FORMAT = // '&', '|', '(',
                                                               // ')' are
                                                               // reserved for
                                                               // logic
                                                               // operation
            Pattern.compile("(?<keywords>[^&|()]+(?:\\s+)*)"); // one or more
                                                               // keywords
                                                               // separated by
                                                               // whitespace

    private static final Pattern LOGIC_KEYWORDS_ARGS_FORMAT = // '&', '|', '(',
                                                              // ')' are
                                                              // reserved for
                                                              // logic operation
            Pattern.compile("(?<arguments>.*[&|()].*)"); // one or more keywords
                                                         // separated by logic
                                                         // operation words

    private static final Pattern INVALID_LOGIC_SEARCH_ARGS1 = Pattern.compile(".*\\w\\s*[(].*"); // keywords
                                                                                                 // followed
                                                                                                 // by
                                                                                                 // '('
                                                                                                 // is
                                                                                                 // invalid
                                                                                                 // in
                                                                                                 // logic
                                                                                                 // operation

    private static final Pattern INVALID_LOGIC_SEARCH_ARGS2 = Pattern.compile(".*[(&|]\\s*[&|)].*"); // no
                                                                                                     // keywords
                                                                                                     // between
                                                                                                     // [(&|]
                                                                                                     // and
                                                                                                     // [&|)]
                                                                                                     // is
                                                                                                     // invalid
                                                                                                     // in
                                                                                                     // logic
                                                                                                     // operation

    private static final Pattern INVALID_LOGIC_SEARCH_ARGS3 = Pattern.compile(".*[&|]\\s*"); // end
                                                                                             // with
                                                                                             // &
                                                                                             // or
                                                                                             // |
                                                                                             // is
                                                                                             // invalid

    private static final Pattern SORT_TASK_LIST_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)?)"); // one
                                                                                                                 // or
                                                                                                                 // two
                                                                                                                 // keywords
                                                                                                                 // separated
                                                                                                                 // by
                                                                                                                 // whitespace

    private static final Pattern INDEX_NUMBER_ARGS_FORMAT = Pattern.compile("(?<numbers>[0-9]+(?:\\s+[0-9]+)*)"); // one
                                                                                                                  // or
                                                                                                                  // more
                                                                                                                  // index
                                                                                                                  // numbers
                                                                                                                  // separated
                                                                                                                  // by
                                                                                                                  // whitespace

    private static final Pattern INDEX_NUM_TO_INDEX_NUM_ARGS_FORMAT = Pattern
            .compile("(?<first>[0-9]+) to (?<last>[0-9]+)");

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes
                                                         // are reserved for
                                                         // delimiter prefixes
            Pattern.compile(
                    "(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + " st=(?<startTime>[^=]+)" + " e=(?<endDate>[^=]+)"
                            + " et=(?<endTime>[^=]+)" + " i=(?<level>[^=]+)" + "(?<tagArguments>(?: t=[^=]+)*)"); // variable
                                                                                                                  // number
                                                                                                                  // of
                                                                                                                  // tags
    private static final Pattern TASK_DATA_ARGS_FORMAT_2 = // '/' forward
                                                           // slashes are
                                                           // reserved for
                                                           // delimiter prefixes
            Pattern.compile("(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + " st=(?<startTime>[^=]+)"
                    + " e=(?<endDate>[^=]+)" + " et=(?<endTime>[^=]+)" + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern TASK_DATA_ARGS_FORMAT_3 = // '/' forward
                                                           // slashes are
                                                           // reserved for
                                                           // delimiter prefixes
            Pattern.compile("(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + " e=(?<endDate>[^=]+)" + " i=(?<level>[^=]+)"
                    + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern TASK_DATA_ARGS_FORMAT_4 = // '/' forward
                                                           // slashes are
                                                           // reserved for
                                                           // delimiter prefixes
            Pattern.compile("(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + " e=(?<endDate>[^=]+)"
                    + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT = // '/' forward
                                                                  // slashes are
                                                                  // reserved
                                                                  // for
                                                                  // delimiter
                                                                  // prefixes
            Pattern.compile("(?<name>[^=]+)" + " e=(?<endDate>[^=]+)" + " et=(?<endTime>[^=]+)" + " i=(?<level>[^=]+)"
                    + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT_2 = // '/'
                                                                    // forward
                                                                    // slashes
                                                                    // are
                                                                    // reserved
                                                                    // for
                                                                    // delimiter
                                                                    // prefixes
            Pattern.compile("(?<name>[^=]+)" + " e=(?<endDate>[^=]+)" + " et=(?<endTime>[^=]+)"
                    + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT_3 = // '/'
                                                                    // forward
                                                                    // slashes
                                                                    // are
                                                                    // reserved
                                                                    // for
                                                                    // delimiter
                                                                    // prefixes
            Pattern.compile("(?<name>[^=]+)" + " e=(?<endDate>[^=]+)" + " i=(?<level>[^=]+)"
                    + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT_4 = // '/'
                                                                    // forward
                                                                    // slashes
                                                                    // are
                                                                    // reserved
                                                                    // for
                                                                    // delimiter
                                                                    // prefixes
            Pattern.compile("(?<name>[^=]+)" + " e=(?<endDate>[^=]+)" + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = // '/' forward
                                                                  // slashes are
                                                                  // reserved
                                                                  // for
                                                                  // delimiter
                                                                  // prefixes
            Pattern.compile("(?<name>[^=]+)" + " i=(?<level>[^=]+)" + "(?<tagArguments>(?: t=[^=]+)*)"); // variable
                                                                                                         // number
                                                                                                         // of
                                                                                                         // tags
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT_2 = // '/'
                                                                    // forward
                                                                    // slashes
                                                                    // are
                                                                    // reserved
                                                                    // for
                                                                    // delimiter
                                                                    // prefixes
            Pattern.compile("(?<name>[^=]+)" + "(?<tagArguments>(?: t=[^=]+)*)"); // variable
                                                                                  // number
                                                                                  // of
                                                                                  // tags
    private static final Pattern START_TASK_DATA_ARGS_FORMAT = // '/' forward
                                                               // slashes are
                                                               // reserved for
                                                               // delimiter
                                                               // prefixes
            Pattern.compile("(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + " st=(?<startTime>[^=]+)"
                    + " i=(?<level>[^=]+)" + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern START_TASK_DATA_ARGS_FORMAT_2 = // '/' forward
                                                                 // slashes are
                                                                 // reserved for
                                                                 // delimiter
                                                                 // prefixes
            Pattern.compile("(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + " st=(?<startTime>[^=]+)"
                    + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern START_TASK_DATA_ARGS_FORMAT_3 = // '/' forward
                                                                 // slashes are
                                                                 // reserved for
                                                                 // delimiter
                                                                 // prefixes
            Pattern.compile("(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + " i=(?<level>[^=]+)"
                    + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern START_TASK_DATA_ARGS_FORMAT_4 = // '/' forward
                                                                 // slashes are
                                                                 // reserved for
                                                                 // delimiter
                                                                 // prefixes
            Pattern.compile("(?<name>[^=]+)" + " s=(?<startDate>[^=]+)" + "(?<tagArguments>(?: t=[^=]+)*)");
    private static final Pattern ADD_TAGS_ARGS_FORMAT = // '/' forward slashes
                                                        // are reserved for
                                                        // delimiter prefixes
            Pattern.compile("(?<targetIndex>\\S+)(?<tagArguments>(?: t=[^=]+)*)"); // variable
                                                                                   // number
                                                                                   // of
                                                                                   // tags
    private static final Pattern DATE_TIME_FORMAT = Pattern
            .compile("(?<date>[0-9 ]+[./-][0-9 ]+[./-][0-9]+)" + "(?<time>(?: [^/]+))");
    private static final Pattern DATE_TIME_FORMAT_2 = Pattern
            .compile("(?<time>(?:[^/]+))" + "(?<date> [0-9 ]+[./-][0-9 ]+[./-][0-9]+)");
    private static final Pattern DATE_TIME_FORMAT_3 = Pattern.compile("(?<date>[0-9]{8})" + "(?<time>(?: [^/]+))");
    private static final Pattern DATE_TIME_FORMAT_4 = Pattern.compile("(?<time>(?:[^/]+))" + "(?<date> [0-9]{8})");
    private static final Pattern DATE_TIME_FORMAT_5 = Pattern.compile("(?<date>[0-9]{8})");
    private static final Pattern DATE_TIME_FORMAT_6 = Pattern.compile("(?<date>[0-9 ]+[./-][0-9 ]+[./-][0-9]+)");

    private static final Pattern TASK_REMINDER_ARGS_FORMAT = Pattern
            .compile("(?<targetIndex>[0-9]+)" + " s=(?<startDate>[^=]+)");
    
    private static final Pattern SAVE_DIRECTORY_ARGS_FORMAT = Pattern.compile("(?<filePath>.+).xml");
    
    private static final Pattern SAVE_RESET_DIRECTORY_ARGS_FORMAT = Pattern.compile(SetDirCommand.COMMAND_WORD_RESET);

    private static final String XML_FILE_EXTENSION = ".xml";
    
    private static final Pattern TASK_EDIT_DATA_ARGS_FORMAT = Pattern.compile("(?<targetIndex>\\S+)(?<arguments>.*)");
    private static final CharSequence NAME = "n=";
    private static final CharSequence START_DATE = "s=";
    private static final CharSequence START_TIME = "st=";
    private static final CharSequence END_DATE = "e=";
    private static final CharSequence END_TIME = "et=";
    private static final CharSequence IMPORTANCE = "i=";
    private static final CharSequence TAG = "t=";
    
    private static final int TASK_NAME = 0;
    private static final int TASK_START_DATE = 1;
    private static final int TASK_START_TIME = 2;
    private static final int TASK_END_DATE = 3;
    private static final int TASK_END_TIME = 4;
    private static final int TASK_IMPORTANCE = 5;
    private static final int TOTAL_NUMBER_OF_ARGUMENTS = 6;
    
    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public final Command parseCommand(final String userInput) {
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
        
        case UndoCommand.COMMAND_WORD:
            return prepareUndo(arguments);
            
        case RedoCommand.COMMAND_WORD:
            return prepareRedo(arguments);

        case AddTagCommand.COMMAND_WORD:
            return prepareAddTag(arguments);

        case DelTagCommand.COMMAND_WORD:
            return prepareDelTag(arguments);

        case RemindCommand.COMMAND_WORD:
            return prepareRemind(arguments);

        case MarkCompleteCommand.COMMAND_WORD:
            return prepareMarkComplete(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ShowCommand.COMMAND_WORD:
            return prepareShow(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case SetDirCommand.COMMAND_WORD:
        	return prepareSetDir(arguments);

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
    private Command prepareAdd(final String args) {
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
        if (!matcher.matches() && !matcher2.matches() && !matcher3.matches() && !matcher4.matches()
                && !matcher5.matches() && !matcher6.matches() && !matcher7.matches() && !matcher8.matches()
                && !matcher9.matches() && !matcher10.matches() && !matcher11.matches() && !matcher12.matches()
                && !matcher13.matches() && !matcher14.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            if (matcher.matches()) {
                return new AddCommand(matcher.group("name"), matcher.group("startDate"), matcher.group("startTime"),
                        matcher.group("endDate"), matcher.group("endTime"), matcher.group("level"),
                        getTagsFromArgs(matcher.group("tagArguments")));
            } else if (matcher2.matches()) {
                return new AddCommand(matcher2.group("name"), matcher2.group("startDate"), matcher2.group("startTime"),
                        matcher2.group("endDate"), matcher2.group("endTime"),
                        getTagsFromArgs(matcher2.group("tagArguments")));
            } else if (matcher3.matches()) {

                String argStartDate = dateParse(matcher3.group("startDate"));
                String argStartTime = timeParse(matcher3.group("startDate"));
                String argEndDate = dateParse(matcher3.group("endDate"));
                String argEndTime = timeParse(matcher3.group("endDate"));

                return new AddCommand(matcher3.group("name"), argStartDate, argStartTime, argEndDate, argEndTime,
                        matcher3.group("level"), getTagsFromArgs(matcher3.group("tagArguments")));
            } else if (matcher4.matches()) {
                String argStartDate = dateParse(matcher4.group("startDate"));
                String argStartTime = timeParse(matcher4.group("startDate"));
                String argEndDate = dateParse(matcher4.group("endDate"));
                String argEndTime = timeParse(matcher4.group("endDate"));

                return new AddCommand(matcher4.group("name"), argStartDate, argStartTime, argEndDate, argEndTime,
                        getTagsFromArgs(matcher4.group("tagArguments")));
            } else if (matcher5.matches()) {
                return new AddCommand(matcher5.group("name"), matcher5.group("endDate"), matcher5.group("endTime"),
                        matcher5.group("level"), getTagsFromArgs(matcher5.group("tagArguments")));
            } else if (matcher6.matches()) {
                return new AddCommand(matcher6.group("name"), matcher6.group("endDate"), matcher6.group("endTime"),
                        getTagsFromArgs(matcher6.group("tagArguments")));
            } else if (matcher7.matches()) {
                String argEndDate = dateParse(matcher7.group("endDate"));
                String argEndTime = timeParse(matcher7.group("endDate"));
                return new AddCommand(matcher7.group("name"), argEndDate, argEndTime, matcher7.group("level"),
                        getTagsFromArgs(matcher7.group("tagArguments")));
            } else if (matcher8.matches()) {
                String argEndDate = dateParse(matcher8.group("endDate"));
                String argEndTime = timeParse(matcher8.group("endDate"));
                return new AddCommand(matcher8.group("name"), argEndDate, argEndTime,
                        getTagsFromArgs(matcher8.group("tagArguments")));
            } else if (matcher9.matches()) {
                return new AddCommand(matcher9.group("name"), matcher9.group("level"),
                        getTagsFromArgs(matcher9.group("tagArguments")));
            } else if (matcher10.matches()) {
                return new AddCommand(matcher10.group("name"), getTagsFromArgs(matcher10.group("tagArguments")));

            } else if (matcher11.matches()) {
                return new AddCommand(matcher11.group("name"), matcher11.group("startDate"),
                        matcher11.group("startTime"), "", "", matcher11.group("level"),
                        getTagsFromArgs(matcher11.group("tagArguments")));
            } else if (matcher12.matches()) {
                return new AddCommand(matcher12.group("name"), matcher12.group("startDate"),
                        matcher12.group("startTime"), "", "", getTagsFromArgs(matcher12.group("tagArguments")));
            } else if (matcher13.matches()) {
                String argStartDate = dateParse(matcher13.group("startDate"));
                String argStartTime = timeParse(matcher13.group("startDate"));
                return new AddCommand(matcher13.group("name"), argStartDate, argStartTime, "", "",
                        matcher13.group("level"), getTagsFromArgs(matcher13.group("tagArguments")));
            } else {
                String argStartDate = dateParse(matcher14.group("startDate"));
                String argStartTime = timeParse(matcher14.group("startDate"));
                return new AddCommand(matcher14.group("name"), argStartDate, argStartTime, "", "",
                        getTagsFromArgs(matcher14.group("tagArguments")));
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Checks whether the string contains a time
     *
     * @param the string that the user inputs in s= or e= which
     *        may contain date and time together
     * @return the time
     */
    // @@author A0139579J
    private String timeParse(final String dateTimeString) {
        Matcher matcher1 = DATE_TIME_FORMAT.matcher(dateTimeString.trim());
        Matcher matcher2 = DATE_TIME_FORMAT_2.matcher(dateTimeString.trim());
        Matcher matcher3 = DATE_TIME_FORMAT_3.matcher(dateTimeString.trim());
        Matcher matcher4 = DATE_TIME_FORMAT_4.matcher(dateTimeString.trim());
        Matcher matcher5 = DATE_TIME_FORMAT_5.matcher(dateTimeString.trim());
        Matcher matcher6 = DATE_TIME_FORMAT_6.matcher(dateTimeString.trim());

        if (matcher1.matches()) {
            return matcher1.group("time");
        }

        if (matcher2.matches()) {
            return matcher2.group("time");
        }

        if (matcher3.matches()) {
            return matcher3.group("time");
        }

        if (matcher4.matches()) {
            return matcher4.group("time");
        }

        if (matcher5.matches()) {
            return "";
        }

        if (matcher6.matches()) {
            return "";
        }

        return dateTimeString;
    }
    
    /**
     * Checks whether the string contains a date
     *
     * @param the string that the user inputs in s= or e= which
     *        may contain date and time together
     * @return the date
     */
    // @@author A0139579J
    private String dateParse(final String dateTimeString) {
        Matcher matcher1 = DATE_TIME_FORMAT.matcher(dateTimeString.trim());
        Matcher matcher2 = DATE_TIME_FORMAT_2.matcher(dateTimeString.trim());
        Matcher matcher3 = DATE_TIME_FORMAT_3.matcher(dateTimeString.trim());
        Matcher matcher4 = DATE_TIME_FORMAT_4.matcher(dateTimeString.trim());

        if (matcher1.matches()) {
            return matcher1.group("date");
        }

        if (matcher2.matches()) {
            return matcher2.group("date");
        }

        if (matcher3.matches()) {
            return matcher3.group("date");
        }

        if (matcher4.matches()) {
            return matcher4.group("date");
        }

        return dateTimeString;
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(final String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t=", "").split(" t="));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    // @@author A0139579J
    private Command prepareEdit(final String args) {
        final Matcher matcher = TASK_EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        String[] argumentsForEdit = new String[TOTAL_NUMBER_OF_ARGUMENTS];

        String arguments = matcher.group("arguments");

        if (arguments.length() == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        if (("float").equals(arguments.trim())) {
            argumentsForEdit[TASK_START_DATE] = "";
            argumentsForEdit[TASK_START_TIME] = "";
            argumentsForEdit[TASK_END_DATE] = "";
            argumentsForEdit[TASK_END_TIME] = "";

            try {
                return new EditCommand(index.get(), argumentsForEdit, null);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(e.getMessage());
            }
        }

        Pattern editArguments = scanArgumentsAndBuildRegex(arguments);

        if (editArguments == null) {
            return new IncorrectCommand(
                    String.format(EditCommand.MESSAGE_INVALID_ARGUMENTS, EditCommand.MESSAGE_USAGE));
        }

        Matcher matcher2 = editArguments.matcher(arguments);
        if (matcher2.matches()) {
            if (arguments.contains(NAME)) {
                argumentsForEdit[TASK_NAME] = matcher2.group("name");
            }
            if (arguments.contains(START_DATE) && !arguments.contains(START_TIME)) {
                if (("nil").equals(matcher2.group("startDate"))) {
                    argumentsForEdit[TASK_START_DATE] = "";
                    argumentsForEdit[TASK_START_TIME] = "";
                } else {
                    argumentsForEdit[TASK_START_DATE] = dateParse(matcher2.group("startDate"));
                    argumentsForEdit[TASK_START_TIME] = timeParse(matcher2.group("startDate"));

                    Time getTime = null;
                    try {
                        getTime = new Time(argumentsForEdit[TASK_START_TIME]);
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                    if (("").equals(argumentsForEdit[TASK_START_TIME]) | ("").equals(getTime.getTime()))
                        argumentsForEdit[TASK_START_TIME] = null;

                }
            } else if (arguments.contains(START_DATE) && arguments.contains(START_TIME)) {
                if (("nil").equals(matcher2.group("startDate")) && ("nil").equals(matcher2.group("startTime"))) {
                    argumentsForEdit[TASK_START_DATE] = "";
                    argumentsForEdit[TASK_START_TIME] = "";
                } else {
                    argumentsForEdit[TASK_START_DATE] = matcher2.group("startDate");
                    argumentsForEdit[TASK_START_TIME] = matcher2.group("startTime");
                }
            } else if (arguments.contains(START_TIME)) {
                if (("nil").equals(matcher2.group("startTime"))) {
                    argumentsForEdit[TASK_START_TIME] = "";
                } else {
                    argumentsForEdit[TASK_START_TIME] = matcher2.group("startTime");
                }
            }

            if (arguments.contains(END_DATE) && !arguments.contains(END_TIME)) {
                if (("nil").equals(matcher2.group("endDate"))) {
                    argumentsForEdit[TASK_END_DATE] = "";
                    argumentsForEdit[TASK_END_TIME] = "";
                } else {
                    argumentsForEdit[TASK_END_DATE] = dateParse(matcher2.group("endDate"));
                    argumentsForEdit[TASK_END_TIME] = timeParse(matcher2.group("endDate"));
                    Time getTime = null;
                    try {
                        getTime = new Time(argumentsForEdit[TASK_END_TIME]);
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                    if (("").equals(argumentsForEdit[TASK_END_TIME]) | ("").equals(getTime.getTime()))
                        argumentsForEdit[TASK_END_TIME] = null;
                }
            } else if (arguments.contains(END_DATE) && arguments.contains(END_TIME)) {
                if (("nil").equals(matcher2.group("endDate")) && ("nil").equals(matcher2.group("endTime"))) {
                    argumentsForEdit[TASK_END_DATE] = "";
                    argumentsForEdit[TASK_END_TIME] = "";
                } else {
                    argumentsForEdit[TASK_END_DATE] = matcher2.group("endDate");
                    argumentsForEdit[TASK_END_TIME] = matcher2.group("endTime");
                }
            } else if (arguments.contains(END_TIME)) {
                if (("nil").equals(matcher2.group("endTime"))) {
                    argumentsForEdit[TASK_END_TIME] = "";
                } else {
                    argumentsForEdit[TASK_END_TIME] = timeParse(matcher2.group("endTime"));
                }
            }

            if (arguments.contains(IMPORTANCE)) {
                if (("nil").equals(matcher2.group("level"))) {
                    argumentsForEdit[TASK_IMPORTANCE] = "";
                } else {
                    argumentsForEdit[TASK_IMPORTANCE] = matcher2.group("level");
                }
            }
        }
        try {
            return new EditCommand(index.get(), argumentsForEdit, getTagsFromArgs(matcher2.group("tagArguments")));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Builds the regex pattern based on the user's input of parameters
     *
     * @param full command args string
     *            
     * @return the regex pattern
     */
    // @@author A0139579J
    private Pattern scanArgumentsAndBuildRegex(final String arguments) {
        String regex = "";

        if (arguments.contains(NAME)) {
            regex += " n=(?<name>[^=]+)";
        }
        if (arguments.contains(START_DATE)) {
            regex += " s=(?<startDate>[^=]+)";
        }
        if (arguments.contains(START_TIME)) {
            regex += " st=(?<startTime>[^=]+)";
        }
        if (arguments.contains(END_DATE)) {
            regex += " e=(?<endDate>[^=]+)";
        }

        if (arguments.contains(END_TIME)) {
            regex += " et=(?<endTime>[^=]+)";
        }

        if (arguments.contains(IMPORTANCE)) {
            regex += " i=(?<level>[^=]+)";
        }

        regex += "(?<tagArguments>(?: t=[^=]+)*)";

        Pattern editArguments = Pattern.compile(regex);

        Matcher matcher = editArguments.matcher(arguments);

        if (matcher.matches()) {
            return editArguments;
        } else {
            return null;
        }
    }
    
    //@@author A0139481Y
    /**
     * Parses arguments in the context of the add tag command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddTag(final String args) {
        final Matcher matcher = ADD_TAGS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        try {
            return new AddTagCommand(index.get(), getTagsFromArgs(matcher.group("tagArguments")));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    //@@author A0139481Y
    /**
     * Parses arguments in the context of the delete tag command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelTag(final String args) {
        final Matcher matcher = ADD_TAGS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelTagCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelTagCommand.MESSAGE_USAGE));
        }
        try {
            return new DelTagCommand(index.get(), getTagsFromArgs(matcher.group("tagArguments")));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the Mark Complete task command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    // @@author A0139579J
    private Command prepareMarkComplete(final String args) {
        final Matcher matcher = INDEX_NUMBER_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher2 = INDEX_NUM_TO_INDEX_NUM_ARGS_FORMAT.matcher(args.trim());

        if (!matcher.matches() && !matcher2.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCompleteCommand.MESSAGE_USAGE));
        }

        Set<Integer> indexNumSet;

        if (matcher2.matches()) {
            Integer front = Integer.valueOf(matcher2.group("first"));
            Integer back = Integer.valueOf(matcher2.group("last"));

            if (back < front | front.equals(back)) {
                return new IncorrectCommand(String.format(MarkCompleteCommand.MESSAGE_INVALID_ARGUMENTS,
                        MarkCompleteCommand.MESSAGE_USAGE));
            }
            Integer[] indexNumInteger = new Integer[back - front + 1];
            int index = 0;
            for (Integer i = front; i <= back; i++) {

                indexNumInteger[index] = i;
                index++;
            }

            indexNumSet = new HashSet<Integer>(Arrays.asList(indexNumInteger));

        } else {
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
     * Parses arguments in the context of the Remind command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    // @@author A0139579J
    private Command prepareRemind(String arguments) {
        final Matcher matcher = TASK_REMINDER_ARGS_FORMAT.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = parseIndex(matcher.group("targetIndex"));
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }

        int targetIndex = Integer.parseInt(matcher.group("targetIndex"));

        return new RemindCommand(targetIndex, matcher.group("startDate"));
    }

    /**
     * Parses arguments in the context of the sort task command.
     * 
     * @param args full command args string
     * @return the prepared command
     * @author A0148044J
     */
    //@@author A0148044J
    private Command prepareSort(final String args) {
        System.out.println(args.trim());
        final Matcher matcher = SORT_TASK_LIST_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] keywords = matcher.group("keywords").split("\\s+");
        String type = null;
        boolean defaultOrder = true;
        if (keywords[0].equalsIgnoreCase("n") || keywords[0].equalsIgnoreCase("Name")) {
            type = "Name";
        } else if (keywords[0].equalsIgnoreCase("s") || keywords[0].equalsIgnoreCase("start")) {
            type = "Start Time";
        } else if (keywords[0].equalsIgnoreCase("e") || keywords[0].equalsIgnoreCase("end")) {
            type = "End Time";
        } else if (keywords[0].equalsIgnoreCase("i") || keywords[0].equalsIgnoreCase("importance")) {
            type = "Importance";
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        if (keywords.length == 2) {
            if (keywords[1].equalsIgnoreCase("DESC") || keywords[1].equalsIgnoreCase("descending")
                    || keywords[1].equalsIgnoreCase("descend")) {
                defaultOrder = ("Importance".equals(type)) ? true : false;
            } else if (keywords[1].equalsIgnoreCase("ASC") || keywords[1].equalsIgnoreCase("ascending")
                    || keywords[1].equalsIgnoreCase("ascend")) {
                defaultOrder = ("Importance".equals(type)) ? false : true;
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }
        return new SortCommand(type, defaultOrder);
    }
    
    //@@author A0139481Y
    /**
     * Parses arguments in the context of the undo command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUndo(String args) {
        if(args.trim().length() == 0) {
            return new UndoCommand();
        }
        
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }

        return new UndoCommand(index.get());
    }
    
    //@@author A0135797M
    /**
     * Parses arguments in the context of the redo command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareRedo(String args) {
        if(args.trim().length() == 0) {
            return new RedoCommand();
        }
        
        Optional<Integer> index = parseIndex(args);
        if(index.isPresent()){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }

        return new RedoCommand();
    }
    
    //@@author

    /**
     * Parses arguments in the context of the delete task command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    // @@author generated
    private Command prepareDelete(final String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    // @@author generated
    private Command prepareSelect(final String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(final String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
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
    //@@author A0148044J
    private Command prepareFind(final String args) {
        final Matcher matcher1 = NORMAL_KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher2 = LOGIC_KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        boolean logicRelation = false;
        String[] keywords = null;
        List<String> keywordSet = new ArrayList<String>();
        if (!matcher1.matches() && !matcher2.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        if (matcher2.matches()) {
            logicRelation = true;
            String arguments = matcher2.group("arguments").trim();
            System.out.println(arguments);
            if (INVALID_LOGIC_SEARCH_ARGS1.matcher(arguments).matches()
                    || INVALID_LOGIC_SEARCH_ARGS2.matcher(arguments).matches()
                    || INVALID_LOGIC_SEARCH_ARGS3.matcher(arguments).matches()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_LOGIC_SEARCH));
            }
            String singleChar;
            String stackChar = "";
            boolean foundLeftBracket;
            Stack<String> expressionStack = new Stack<String>();
            Stack<String> keywordStack = new Stack<String>();
            for (int index = 0; index < arguments.length(); index++) {
                singleChar = arguments.substring(index, index + 1);
                foundLeftBracket = false;
                if (singleChar.matches("[(]")) {
                    expressionStack.push(singleChar);
                    keywordSet.add(singleChar);
                } else if (singleChar.matches("[)]")) {
                    while (!expressionStack.empty()) {
                        if (expressionStack.peek().matches("[(]")) {
                            while (!keywordStack.empty()) {
                                stackChar = stackChar.concat(keywordStack.pop());
                            }
                            try {
                                if (!stackChar.matches("\\s+")) {
                                    keywordSet.add((convertKeywordsIntoDefinedFormat(stackChar)));
                                }
                            } catch (IllegalValueException ive) {
                                return new IncorrectCommand(ive.getMessage());
                            }
                            keywordSet.add(singleChar);
                            expressionStack.pop();
                            stackChar = "";
                            foundLeftBracket = true;
                            break;
                        } else {
                            keywordStack.push(expressionStack.pop());
                        }
                    }
                    if (!foundLeftBracket) {
                        return new IncorrectCommand(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_BRACKET_USAGE));
                    }
                } else if (singleChar.matches("[|&]")) {
                    while (!expressionStack.empty() && !expressionStack.peek().matches("[(]")) {
                        keywordStack.push(expressionStack.pop());
                    }
                    while (!keywordStack.empty()) {
                        stackChar = stackChar.concat(keywordStack.pop());
                    }
                    try {
                        if (!stackChar.matches("\\s+")) {
                            keywordSet.add((convertKeywordsIntoDefinedFormat(stackChar)));
                        }
                    } catch (IllegalValueException ive) {
                        return new IncorrectCommand(ive.getMessage());
                    }
                    keywordSet.add(singleChar);
                    stackChar = "";
                } else {
                    expressionStack.push(singleChar);
                }
            }
            while (!expressionStack.empty()) {
                keywordStack.push(expressionStack.pop());
            }
            while (!keywordStack.empty()) {
                stackChar = stackChar.concat(keywordStack.pop());
            }
            if (stackChar.contains("(")) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_BRACKET_USAGE));
            }
            try {
                keywordSet.add((convertKeywordsIntoDefinedFormat(stackChar)));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else {
            logicRelation = false;
            keywords = matcher1.group("keywords").split("\\s+");
            try {
                for (String keyword : keywords) {
                    keywordSet.add(convertKeywordsIntoDefinedFormat(keyword));
                }
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
        try {
            return new FindCommand(logicRelation, keywordSet);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
    /*
     * // keywords delimited by whitespace final String[] keywords =
     * matcher.group("keywords").split("\\s+"); final int type;
     * if(keywords[0].contains(START_DATE)) { type = 1; keywords[0] =
     * keywords[0].replace(START_DATE, "").replace("'"," "); } else
     * if(keywords[0].contains(END_DATE)) { type = 2; keywords[0] =
     * keywords[0].replace(END_DATE, "").replace("'"," "); } else
     * if(keywords[0].contains(IMPORTANCE)) { type = 3; keywords[0] =
     * keywords[0].replace(IMPORTANCE, ""); } else if(keywords[0].contains(TAG))
     * { type = 4; keywords[0] = keywords[0].replace(TAG, ""); } else { type =
     * 0; } final Set<String> keywordSet = new
     * HashSet<>(Arrays.asList(keywords)); try { return new FindCommand(type,
     * keywordSet); } catch (IllegalValueException ive) { return new
     * IncorrectCommand(ive.getMessage()); } }
     */

    private String convertKeywordsIntoDefinedFormat(final String keyword) throws IllegalValueException {
        String convertedKeyword = null;
        if (keyword.contains(NAME)) {
            convertedKeyword = keyword.replace(NAME, " Name: ").trim().replace("'", " ");
        } else if (keyword.contains(START_DATE)) {
            convertedKeyword = keyword.replace(START_DATE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("date", convertedKeyword);
            convertedKeyword = " Start Date: " + convertedKeyword;
        } else if (keyword.contains(START_TIME)) {
            convertedKeyword = keyword.replace(START_TIME, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("time", convertedKeyword);
            convertedKeyword = " Start Time: " + convertedKeyword;
        } else if (keyword.contains(END_DATE)) {
            convertedKeyword = keyword.replace(END_DATE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("date", convertedKeyword);
            convertedKeyword = " End Date: " + convertedKeyword;
        } else if (keyword.contains(END_TIME)) {
            convertedKeyword = keyword.replace(END_TIME, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("time", convertedKeyword);
            convertedKeyword = " End Time: " + convertedKeyword;
        } else if (keyword.contains(IMPORTANCE)) {
            convertedKeyword = keyword.replace(IMPORTANCE, "").trim().replace("'", " ");
            convertedKeyword = Task.formatInput("importance", convertedKeyword);
            convertedKeyword = " Importance: " + convertedKeyword;
        } else if (keyword.contains(TAG)) {
            convertedKeyword = keyword.replace(TAG, "").trim().replace("'", " ");
            convertedKeyword = " Tags: [" + convertedKeyword + "]";
        } else {
            convertedKeyword = keyword.trim().replace("'", " ");
        }
        return convertedKeyword;
    }

    private Command prepareShow(final String arguments) {
        String keyword = arguments.trim();
        if (keyword.equalsIgnoreCase("today") || keyword.equalsIgnoreCase("tod")) {
            return new ShowCommand("today");
        } else if (keyword.equalsIgnoreCase("Monday") || keyword.equalsIgnoreCase("Mon")) {
            return new ShowCommand("Monday");
        } else if (keyword.equalsIgnoreCase("Tuesday") || keyword.equalsIgnoreCase("Tue")) {
            return new ShowCommand("Tuesday");
        } else if (keyword.equalsIgnoreCase("Wednesday") || keyword.equalsIgnoreCase("Wed")) {
            return new ShowCommand("Wednesday");
        } else if (keyword.equalsIgnoreCase("Thursday") || keyword.equalsIgnoreCase("Thu")) {
            return new ShowCommand("Thursday");
        } else if (keyword.equalsIgnoreCase("Friday") || keyword.equalsIgnoreCase("Fri")) {
            return new ShowCommand("Friday");
        } else if (keyword.equalsIgnoreCase("Saturday") || keyword.equalsIgnoreCase("Sat")) {
            return new ShowCommand("Saturday");
        } else if (keyword.equalsIgnoreCase("Sunday") || keyword.equalsIgnoreCase("Sun")) {
            return new ShowCommand("Sunday");
        } else if (keyword.equalsIgnoreCase("Event") || keyword.equalsIgnoreCase("Eve")) {
            return new ShowCommand("Event");
        } else if (keyword.equalsIgnoreCase("Floating") || keyword.equalsIgnoreCase("Flo")) {
            return new ShowCommand("Floating");
        } else if (keyword.equalsIgnoreCase("Deadline") || keyword.equalsIgnoreCase("Dea")) {
            return new ShowCommand("Deadline");
        } else if (keyword.equalsIgnoreCase("Red")) {
            return new ShowCommand("Red");
        } else if (keyword.equalsIgnoreCase("Yellow") || keyword.equalsIgnoreCase("Yel")) {
            return new ShowCommand("Yellow");
        } else if (keyword.equalsIgnoreCase("Green") || keyword.equalsIgnoreCase("Gre")) {
            return new ShowCommand("Green");
        } else if (keyword.equalsIgnoreCase("None") || keyword.equalsIgnoreCase("Non")) {
            return new ShowCommand("None");
        } else if (keyword.equalsIgnoreCase("Complete") || keyword.equalsIgnoreCase("Com")) {
            return new ShowCommand("Complete");
        } else if (keyword.equalsIgnoreCase("Incomplete") || keyword.equalsIgnoreCase("Inc")) {
            return new ShowCommand("Incomplete");
        } else if (keyword.equalsIgnoreCase("Expire") || keyword.equalsIgnoreCase("Exp")) {
            return new ShowCommand("Expire");
        } else if (keyword.equalsIgnoreCase("Valid") || keyword.equalsIgnoreCase("Val")) {
            return new ShowCommand("Valid");
        }
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    /**
     * Parses arguments in the context of the list task command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    // @@author generated
    private Command prepareList(final String arguments) {
        if (arguments.length() == 0) {
            return new ListCommand();
        } else {
            try {
                return new ListCommand(arguments);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        }
    }
    /**
     * Parses arguments in the context of the save as command.
     * 
     * @param full command args string
     * @return the prepared command
     */
    //@@author A0135797M
    private Command prepareSetDir(String args) {
        final Matcher resetMatcher = SAVE_RESET_DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        if (resetMatcher.matches()) {
            return new SetDirCommand(Config.DEFAULT_XML_FILE_PATH);
        }
        
        final Matcher matcher = SAVE_DIRECTORY_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDirCommand.MESSAGE_USAGE));
        }
        
        return new SetDirCommand(matcher.group("filePath") + XML_FILE_EXTENSION);
    }

}