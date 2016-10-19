# User Guide

* [About](#about)
* [Quick Start](#quick-start)
* [Features](#features)
* [Command Summary](#command-summary)
* [FAQ](#faq)

##**About**
Inbx_0 (pronounced as Inbox Zero) is a personal task managing assistant that helps to keep your tasks in order. It will help you manage your email inbox easily so as to keep your inbox uncluttered. 

Unlike all the other task managers out there, Inbx\_0 is a simple program that runs on single-line commands. Inbx_0 will execute your commands based on what you type immediately. No more looking for buttons and menus to get the program to do what you want. So let's get started!

## **Quick Start**

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `inbx_0.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager
3. Double-click the Task Manager
4. Double-click the  Task Manager.jar file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/GUI.png" width="600">

5. Type a command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`add`**:`add EE2020` :  adds a task called `EE2020 Midterms` to the Task Manager
   * **`list`** : lists all tasks
   * **`delete`**`1` : deletes the 1st task shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>



## **Features**

All the features in Inbx_0 are performed by typing in commands in the command line. There is a certain command format for each command and the general format can be described as such: 

**Command Format**
1.  Words in `UPPER_CASE` are the parameters that are compulsory and will be taken in by the app (e.g. NAME, DATE). 
<br>
2. Items in `SQUARE_BRACKETS` are optional and will not be required in order to perform the command (e.g. [tags], [date]).
<br>
3. Items with `...` after them can have multiple instances. This means that there can be more than one occurrence of the particular item. For example, “[t/TAG]...” means that you can have multiple tags. 
<br>
4.  The order of parameters is fixed and needs to be strictly followed.

Common Parameters used: <br>
TASK: Name of the task <br>
s/START_DATE: The date that the task will start on <br>
st/START_TIME: The time of the start of the task <br>
e/END_DATE: The date of the task that the task will end on <br>
et/END_TIME: The time of the end of the task <br>
i/IMPORTANCE: The priority of the task. Can be `green`, `yellow` or `red`, in that order of importance <br>
t/TAGS: Tags that are assigned to the task <br>

>  For Dates and Times, the program utiilises natural language processing and can take in multiple formats such as "tmr, next week, next wed, 3 days later, noon, 8am, 1400" <br>
The IMPORTANCE parameter takes in these formats "red, green, yellow, r, g, y, R, G, Y, Red".

The rest of the guide will be using the general command format to describe what is needed to execute each command.

#### <a id="help"></a>1. Getting help: `help`
Format: `help`

Looking for help? If you ever need a reminder on how to input certain commands or have some troubleshooting issues, you can easily access the ‘help’ command which will guide you to the right direction.

To access the help command, type the following into the command line:
> help

This will open up a help window that will direct you back to this User Guide if you need a refresher.

<br><br>
 
#### <a id="add"></a>2. Adding a task: `add`
Let's get started by adding tasks to the tasklist! You may use any of the below formats to get started.

1) Adds a floating task.<br>
Format: `add TASK [i/IMPORTANCE] [t/TAGS]...` 

> Floating tasks are tasks without without any start dates or end dates. You can use this to keep track of tasks which may not be follow
a time schedule.

Examples: 
* `add Buy Groceries` <br>
* `add Wash dishes i/green` <br>
<br>

2) Adds a task with deadlines.<br>
Format: `add TASK e/[END_DATE] [END_TIME] [i/IMPORTANCE] [t/TAGS]` 

Examples: 
* `add Do CS2103 Homework e/tomorrow i/red`
* `add Finish Project Paper e/1 March 12am i/green t/For GEH1027` <br>
<br>

> * Under the e/ parameter, it requires either the END_DATE or END_TIME. By providing only END_DATE, the END_TIME would not be specified.
> *	If the END_DATE is not provided, the end date will be automatically assigned as the current date.

3) Adds a task with starting point only.<br>
Format: `add TASK s/[START_DATE] [START_TIME][i/IMPORTANCE] [t/TAGS]`

Examples: 
* `add Started Gym Training s/10012016 i/red`
* `add On Diet s/18/10/2016 i/red t/health` <br>
<br>

> * Under the s/ parameter, it requires either the START_DATE or START_TIME. By providing only START_DATE, the START_TIME would not be specified.
> *	If the START_DATE is not provided, the start date will be automatically assigned as the current date.

4) Adds an event.<br>
Format: `add TASK s/[START_DATE] [START_TIME] e/[END_DATE] [END_TIME] [i/IMPORTANCE] [t/TAGS]`

> Events are tasks with a starting and ending point.

Examples:
* `add Doctor's appointment s/2 July 2016 5pm e/2 July 2016 7:30pm i/green t/painful`
* `add SO’s Birthday s/29 Feb 12am e/1 March 12am i/green t/flowers t/chocolates`

<br><br>

#### <a id="list"></a>3. Listing tasks: `list`
1) Listing all the tasks. <br>
Format: `list`

The List Command shows a list of all tasks in the task manager so that you can take a look at your tasks all at one Groceries.

 There will be index numbers allocated at the side of each task which will be needed for other operations such as deleting a task or selecting a task.

2) Listing tasks due before a specific date. <br>
Format: `list due DATE`

You will be able to view all of today’s tasks and  tasks that are due before a specific date. By keying in the following, it will display a list of all the tasks due before the input date in the task manager.

> list due DATE

Examples:
* `list due tomorrow` shows every task from now to the end of tomorrow <br>
* `list due 1st Jan` shows every task from now till the end of 1st January 2017 <br>

<br><br>

Want to find out what important things that you need to do? Or when is you next coming exam? If you need to find certain tasks by keywords, you can execute `find` command. 

#### <a id="find"></a>4. Finding specific tasks: `find`
1. Normal search
 
>Format: `find KEYWORD [MORE_KEYWORDS]...`

> * Normal search will display a task that matches at least one keyword (i.e. `OR` search).
> * You can use `INDICATOR/KEYWORD` to specify the keyword belong to a specific attributes, available INDICATOR: n/, s/, st/, e/, et/, i/
> * You can use `'` to replace the whitespace if one search keyword contains whitespace, or inbx_0 treat it as multiple keywords
 eg: `do'CS2103'homework` will match `do CS2103 homework' 
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
> * Keywords are non case-sensitive, ie, `homework` will match `HOMEWORK`
> * The order of the keywords does not matter. e.g. `Movie Night` will match `Night Movie`
> * Only full words will be matched e.g. `visit` will not match `visiting`

Example: 
`find meeting CS2103quiz`

 Return `MEETING` or `CS2103Quiz` but not `meeting with CS2103 tutor` or `quiz` 

2. Logic operation search

> Format:  `[LOGIC_OPERATOR] KEYWORD [MORE_KEYWORDS] [MORE_LOGIC_OPERATOR]...`

> * Logic operation search will display a task only when it fits the logic expression
> * LOGIC_OPERATOR: `|` means OR, `&` means AND, `(` and `)` use for group the logic operation
> * You can use `INDICATOR/KEYWORD` to specify the keyword belong to a specific attributes, available INDICATOR: n/, s/, st/, e/, et/, i/
> * You can use any valid logic operation format
> * valid logic operation format: 
>  1. Bracket must be closed properly
>  2.  Keywords must not contains whitespace and any keywords cannot followed by another keyword without `LOGIC_OPERATOR` in between
>  3.  `|` `&` `(` cannot followed by '|' '&' )' without `keywords` in between

Examples:

`find homework | assignment`: 
Return  `homework` or `assignment`

`find homework & e/tmr`:
Return `homework` that ends` tomorrow`

`find (homework | assignment) & s/tmr & i/r`:
Return `homework` or `meeting` that start on `tomorrow` with high importance `Red`


<br><br>

#### <a id="sort"></a>4. Sort the tasks by specific sorting criteria: `sort`
> Format: sort `[Name]`/`[Start Time]`/`[End Time]`/`[Importance]` `[ASCENDING]`/`[]DESCENDING]`

> * Sort the tasks according to name, start time, end time or importance in descending or ascending order
> * acceptable input for 
> * `NAME`: n, Name, `Start Time`: s, start, `End Time`: e, end, `Importance`: i, importance
> *  `ASCENDING`: ASC, ascending, ascend, `DECENDING`: DESC, descending, descend
> * All sort keywords are non-case sensitive, ie, `Importance` is `importance` 
> * Default sorting order is `ASCENDING` for Name, Start Time and End Time but `DESCENDING` for Importance

Example:

`sort i`:
Sort the lists such that the importance of the tasks decrease

`sort n DESC`:
Sort the lists such that the name of the tasks is in the reverse of natural alphabet order 

#### <a id="edit"></a>5. Editing a task: `edit`
1) Editing any parameter of a task <br>
Format: `edit INDEX [n/NAME] [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [i/IMPORTANCE]`

Made a spelling mistake or your event was postponed? You can use the Edit Command to swiftly rectify any tasks on the task list.

This can be done by typing the following:

> edit INDEX [n/NAME] [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [i/IMPORTANCE] [t/TAG]...

By specifying the parameters of the task and typing the corrected parameters, you will be able to change multiple parameters in that
single task. Do note that one of the optional parameters is necessary.

Examples
* `list today`<br>
  `edit 2 s/tomorrow`<br>
  Changes the 2nd task in today’s list to start tomorrow

* `find meeting`<br> 
  `edit 1 n/Business Lunch st/1pm`<br>
  Changes the name of 1st task in the results of the `find` command to ‘Business Lunch at 1 pm’  

2) Converting a task to a floating task. <br>
Format: `edit INDEX float`

Use the keyword "float" to convert any task into a floating task by removing the starting and ending dates and times.

This can be done by typing the following:

> edit INDEX float

Examples:
* `edit 2 float` will convert the 2nd task in the list into a floating task.

<br><br>

#### <a id="tag"></a>6. Tagging a task: `tag`
Format: `tag INDEX t/TAGS [t/MORE_TAGS]`

If you wish to add additional tags to any existing tasks, you can do it by typing the following: <br>

> tag INDEX t/TAGS [t/MORE_TAGS]

Note that adding tags and editing tags are totally different. Edit tags will remove all tags and add in the editted tag. This tag command will just append the new tag behind any existing tag names.

> The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list today`<br>
  `tag 2 t/tonight`<br>
  Adds the "tonight" tag to the 2nd task in the list.

<br><br>

#### <a id="del"></a>7. Deleting a task: `del`
Format: `del INDEX`

If you have wish to remove a particular task from the list, you can do it by typing the following: <br>

> del INDEX

This will remove the task from task list.

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list today`<br>
  `del 2`<br>
  Deletes the 2nd task in today’s list.

* `find meeting`<br> 
  `del 1`<br>
  Deletes the 1st task in the results of the `find` command for ‘meeting’.

<br><br>

#### <a id="sel"></a>8. Select a task : `sel`
Format: `sel INDEX`

In order to view more details on a task that you have created, you can select the task identified by the index number in the last listing. The Select Command can be performed by typing:

> sel INDEX

This will automatically display the selected task and you will be able to see whether you have placed any reminders on the task. You will also be able to view tags that are associated with the task.

> Selects the task and loads the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `sel 2`<br>
  Selects the 2nd task in the task list.
  
* `find project`<br>
  `sel 1`<br>
  Selects the 1st task in the results of the `find` command.

<br><br>

#### <a id="undo"></a>9. Undo previous action: `undo`
Format: `undo`

Undos the previous action done in the task manager if you've made a mistake. <br>

> Cannot undo CLEAR commands!

<br><br>

#### <a id="clr"></a>10. Clearing all tasks : `clr`
Format: `clr`

Tasks can easily become obsolete and checking off tasks individually can be quite a hassle. The Clear command will help you to remove all tasks and can be accessed by typing the following:

> clr [DATE]

Adding the date in the command line is optional and by default it will clear the whole task list. By including the date, this will enable you to clear tasks from specific dates.

> Please ensure that the tasks are the ones that you want to clear before initiating the Clear Command.

<br><br>

#### <a id="done"></a>11. Mark a task as `done`
1) Mark selected tasks as done
Format: `done INDEX [INDEX]...`

If you have finished a certain task and wish to mark it as finished you can give a `done` label to the specified task in the task list by typing the following: <br>

> done INDEX [INDEX]...

This will label a task as `done`, when task is shown, you will be reminded that you have finished the specified task. If there are more than one tasks that need to be
marked, you can enter more index numbers with all of them separated with a whitespace. The tasks will be marked complete accordingly

> The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
  If the task is already marked completed for one of the index numbers given, it will remind you that one of the tasks is already completed

Example: 
* `find today` <br>
  `done 2 4 7` <br>
 Marks the 2nd, 4th and 7th tasks as completed in today’s list
 
2) Mark tasks in consecutive index numbers as done
Format: `done FIRST_INDEX to LAST_INDEX`

If you wish to mark several tasks in a row as completed, you can type the following:

> done FIRST_INDEX to LAST_INDEX

This will allow multiple tasks specified by the first index to the last index to be marked as completed.

> The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
  If the task is already marked completed for one of the index numbers given, it will remind you that one of the tasks is already completed
  The last index cannot be smaller than the first index and the last index cannot be equal to the first index
  
Example: 
* `list next week` <br>
  `done 1 to 5` <br>
 Marks the 1st to 5th tasks as completed in next week’s list
  
<br><br>

#### <a id="exit"></a>12. Exiting the program : 
Format: `exit`

After using Inbx_0, you can easily exit the program by typing the following in the command line:

> exit

This will initiate a final save and after which, the program will close automatically.  

<br><br>

#### Saving the data 
Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## **Command Summary**

Command | Format  
-------- | :-------- 
[Help](#help) | `help`
[Add](#add) | `add NAME [i/IMPORTANCE] [t/TAGS]`
&nbsp; | `add NAME e/END_DATE et/END_TIME i/IMPORTANCE [t/TAGS]...`
&nbsp; | `add NAME s/START_DATE st/START_TIME e/END_DATE et/END_TIME i/IMPORTANCE [t/TAGS]...`
[List](#list) | `list [DATE]`
&nbsp; | `list i/[IMPORTANCE]`
[Find](#find) | Normal search: `find KEYWORD [MORE_KEYWORDS]...`
| Logic operation search: `find KEYWORD [LOGIC_OPERATOR] [MORE_KEYWORDS] [MORE_LOGIC_OPERATOR]...`
[Sort](#sort) | `sort [n|s|e|i] [ASC\DESC]`
[Edit](#edit) | `edit INDEX [n/NAME] [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [i/IMPORTANCE]`
[Tag](#tag) | `tag INDEX t/TAGS [t/MORE_TAGS]`
[Delete](#del) | `del INDEX`
[Select](#sel) | `sel INDEX`
[Undo](#undo) | `undo`
[Clear](#clr) | `clr [DATE]`
[Done](#done) | `done INDEX`
[Exit](#exit) | `exit`

## **FAQ**

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.

**Q**: How do check if I have the correct Java Version?<br>
**A**: <br>
On Windows:
 1. Click Start on the task bar.
 2. Select Control Panel (or Settings > Control Panel) from the Start menu. The Control Panel is displayed.
 3. Select Java. The Java Control Panel dialog box is displayed .
 4. NOTE: if the Control Panel is in Category mode and you cannot see the Java option, switch the Control Panel to Classic View.
 5. Click the Java tab.
 6. In the Java Application Runtime Setting box, click View. The JNLP Runtime Settings dialog box is displayed.

On Macs:
 1. Open Terminal (Navigate to **Applications** > **Utilities** > **Terminal**))
 2. Type `java -version`.
 3. The Java version and related information displays. For example:

>Users-MacBook-Pro:~ user$ java -version <br>
java version "1.6.0\_51"Java(TM) SE Runtime Environment (build 1.6.0_51-b11-457-11M4509) <br>
Java HotSpot(TM) 64-Bit Server VM (build 20.51-b01-457, mixed mode)

On Linux:
 1. Open Terminal (this can be different depending on which version of Linux you are running. For example, in Ubuntu, navigate to **Applications** > **Accessories** > **Terminal**). In Red Hat, right-click the desktop, and select Open in Terminal from the pull-down menu.
 2. Type `java -version`.
 3. The Java version and related information displays. For example:

>user@user-desktop:~$ java -version <br>
java version "1.7.0\_15"Java(TM) SE Runtime Environment (build 1.7.0_05-b06) <br>
Java HotSpot(TM) Client VM (build 23.1-b03, mixed mode)
