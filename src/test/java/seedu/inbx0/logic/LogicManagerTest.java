package seedu.inbx0.logic;

import com.google.common.eventbus.Subscribe;

import seedu.inbx0.commons.core.EventsCenter;
import seedu.inbx0.commons.events.model.TaskListChangedEvent;
import seedu.inbx0.commons.events.ui.JumpToListRequestEvent;
import seedu.inbx0.commons.events.ui.ShowHelpRequestEvent;
import seedu.inbx0.logic.Logic;
import seedu.inbx0.logic.LogicManager;
import seedu.inbx0.logic.commands.*;
import seedu.inbx0.model.TaskList;
import seedu.inbx0.model.reminder.UniqueReminderList;
import seedu.inbx0.model.Model;
import seedu.inbx0.model.ModelManager;
import seedu.inbx0.model.ReadOnlyTaskList;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;
import seedu.inbx0.model.task.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inbx0.commons.core.Messages.*;

public class LogicManagerTest {

     /* See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskList latestSavedTaskList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(final TaskListChangedEvent abce) {
        latestSavedTaskList = new TaskList(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(final JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
	public final void setUp() {
        model = new ModelManager();
        logic = new LogicManager(model);
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskList = new TaskList(model.getTaskList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
	public final void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
	public final void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskList, List)
     */
    private void assertCommandBehavior(final String inputCommand, final String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(final String inputCommand, final String expectedMessage,
                                       final ReadOnlyTaskList expectedTaskList,
                                       final List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskList, model.getTaskList());
        assertEquals(expectedTaskList, latestSavedTaskList);
    }


    @Test
	public final void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
	public final void execute_help() throws Exception {
        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
        assertTrue(helpShown);
    }

    @Test
	public final void execute_exit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Test
	public final void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandBehavior("clr all", ClearCommand.MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
    }


    @Test
	public final void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add Valid Name e=tmr s=next week t=valid", expectedMessage);
        assertCommandBehavior(
                "add Valid Name i=red s=9th Oct st=9am ", expectedMessage);
        assertCommandBehavior(
                "add Valid Name t=valid s=tomorrow st=9am i=green", expectedMessage);
    }

    @Test
	public final void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add Valid Name s=invalid st=12pm e=today et=4pm i=r", Date.MESSAGE_DATE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name s=today st=invalid e=today et=4pm i=r", Time.MESSAGE_TIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name s=today st=12pm e=today et=4pm i=invalid", Importance.MESSAGE_IMPORTANCE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name s=today st=12pm e=today et=4pm i=r t=invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
	public final void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
	public final void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
	public final void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskList expectedAB = helper.generateAddressBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(final String commandWord, final String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(final String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new TaskList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskList(), taskList);
    }

    @Test
	public final void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("sel", expectedMessage);
    }

    @Test
	public final void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("sel");
    }

    @Test
	public final void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("sel 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
	public final void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("del", expectedMessage);
    }

    @Test
	public final void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("del");
    }

    @Test
	public final void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("del 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
	public final void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
	public final void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
	public final void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
	public final void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        private Task adam() throws Exception {
            Name name = new Name("Do CS2103");
            Date startDate = new Date("12 Nov");
            Time startTime = new Time("9am");
            Date endDate = new Date("13 Nov");
            Time endTime = new Time("10am");
            Importance level = new Importance("G");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            UniqueReminderList reminders = new UniqueReminderList();
            return new Task(name, startDate, startTime, endDate, endTime, level, tags, reminders);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        private Task generateTask(final int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new Date("24th Oct"),
                    new Time(seed + "am"),
                    new Date("26th Nov") ,
                    new Time(seed + "pm"),
                    new Importance("g"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    new UniqueReminderList()
            );
        }

        /** Generates the correct add command based on the task given */
       private String generateAddCommand(final Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" s=").append(p.getStartDate());
            cmd.append(" st=").append(p.getStartTime());
            cmd.append(" e=").append(p.getEndDate());
            cmd.append(" et=").append(p.getEndTime());
            cmd.append(" i=").append(p.getLevel());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t=").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskList with auto-generated tasks.
         */
        private TaskList generateAddressBook(final int numGenerated) throws Exception{
            TaskList taskList = new TaskList();
            addToTaskList(taskList, numGenerated);
            return taskList;
        }

        /**
         * Generates an TaskList based on the list of Tasks given.
         */
        private TaskList generateTaskList(final List<Task> tasks) throws Exception{
            TaskList taskList = new TaskList();
            addToTaskList(taskList, tasks);
            return taskList;
        }

        /**
         * Adds auto-generated Task objects to the given TaskList
         * @param taskList The TaskList to which the Tasks will be added
         */
        private void addToTaskList(final TaskList taskList, final int numGenerated) throws Exception{
            addToTaskList(taskList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskList
         */
        private void addToTaskList(final TaskList taskList, final List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskList.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        private void addToModel(final Model model, final int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        private void addToModel(final Model model, final List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        private List<Task> generateTaskList(final int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        private List<Task> generateTaskList(final Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        private Task generateTaskWithName(final String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Date("9/1/23"),
                    new Time("1am"),
                    new Date("10/12/34"),
                    new Time("1pm"),
                    new Importance("R"),
                    new UniqueTagList(new Tag("tag")),
                    new UniqueReminderList()
            );
        }
    }
}
