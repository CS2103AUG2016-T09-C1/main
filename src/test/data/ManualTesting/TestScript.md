# Test Script for Manual Testing

* [Loading sample data](#load)
* [Features](#features)
	* [Getting help: `help`](#help)
	* [Adding a task: `add`](#add)
	* [Listing tasks: `list`](#list)
	* [Finding specific tasks: `find`](#find)
	* [Sorting the tasks by using a specific criteria: `sort`](#sort)
	* [Showing the various filtered lists: `show`](#show)
	* [Editing a task: `edit`](#edit)
	* [Modifying the taglist: `addtag` / `deltag`](#tag)
	* [Deleting a task: `del`](#del)
	* [Selecting a task : `sel`](#sel)
	* [Undoing previous action: `undo`](#undo)
	* [Redoing previous action: `redo`](#redo)
	* [Clearing all tasks : `clr`](#clr)
	* [Marking a task as completed : `done`](#done)
	* [Setting a reminder for your task : `rem`](#reminder)
	* [Saving your tasklist to a specific directory : `saveas`](#setdir)
	* [Exiting the program : `exit`](#exit)
	* [Autocompleting with command history](#autocomplete)
	* [Displaying expired and overdue tasks](#overdue)
       * [Changing the theme and size of inbx_0](#preferences)

## Load sample data

1. Copy the `SampleData.xml` file into the `data\` folder (if the `data` directory does not exist inside the root directory where the app is located, create it).
2. Rename the file to `tasklist.xml`.
3. Start `Inbx0.jar` by double clicking it.
4. The application should display a list of tasks loaded from the sample data.

**Result:** The task list is loaded. When the app is opened for the first time, a list of overdue tasks that are still not done will appear in a pop up window. This window can be dismissed either by pressing the X which closes the window or by typing in <kbd>Alt</kbd> + <kbd>F4</kbd>.

## Open help

1. Type `help`

**Result:** A new window appears with a command summary page, listing out all the commands available for use.

## Add Command

### Add new floating task (without any timings)

1. Type `add Wash the dishes`

**Result:** A new floating task for "Wash the dishes" is created at the bottom of the list, and will be automatically be selected.

### Add new task with deadline

1. Type `add Finish 2101 Report e=28/11/2016 6pm i=r`

**Result:** A new task "Finish 2101 Report" with deadline 6pm on the 28th November 2016 is created at the bottom of the list. The task will be highlighted in red.

### Add new event

1. Type `add CS2101 Oral Presentation s=tmr 10am e=tmr noon i=r`.

**Result:** A new task "CS2101 Oral Presentation" with start time of 10am next monday to 12 noon is created at the bottom of the list. The task will be highlighted in red.

## List Command

### List all uncompleted tasks

1. Type `show red`.
2. Type `list`.

**Result:** The task list will first show all the tasks with importance red. `List` will then show all uncompleted tasks.

### List overdue tasks

1. Type `list overdue`.

**Result:** The task list will show all overdue tasks which are still not done.

### List tasks associated with a date.

1. Type `list 24 oct`.

**Result:** Only tasks with 24th October in either start date or end date will be shown (3 such tasks).

## Find Command

### Find tasks with a certain tag

1. Type `find t=urgent`.

**Result:** Only tasks that are tagged "urgent" are shown (2 such tasks).

### Show only floating tasks

1. Type `show flo`.

**Result:** Only floating tasks are shown (10 such tasks).

### Show uncompleted tasks

1. Type `show unc`.

**Result:** Only tasks with isCompleted = false are shown (42 such tasks).

### List overdue tasks

1. Type `list overdue`.

**Result:** Only overdue tasks are shown (9 such tasks).

## Adding and deleting tags

### Add tags to task

1. Type `addtag 1 t=important`.

**Result:** A new tag "important" will be appended to the 1st task's taglist.

### Add multiple tags to task

1. Type `addtag 1 t=fun t=fake`.

**Result:** Two new tags "fun" and "fake" will be appended to the 1st task's taglist.

### Delete tag from a task

1. Type `deltag 1 t=fun`.

**Result:** The tag "fun" will be removed from 1st task's taglist.

### Delete multiple tags from a task

1. Type `deltag 1 t=important t=fake`.

**Result:** The tags "important" and "fake" will be removed from 1st task's taglist.

### Select a task in task list

1. Type `select 2`.

**Result:** Task with index 2 will be selected

### Sort list by earliest start date 

1. Type `sort s asc`.

**Result:**  The floating task will be at bottom then followed by the earliest start date task first, order until the last task is the latest

### Sort list by earliest start date 

1. Type `sort s desc`.

**Result:** The floating tasks will be at the top, then ordered by decreasing latest (until the last one is the earliest). 

### Sort list by name (ascending order)

1. Type `sort name asc`.

**Result:** The list is sorted from a to z (a at the top, z at the bottom).

### Sort list by name (descending order)

1. Type `sort name desc`.

**Result:** The list is sorted from z to a (z at the top, a at the bottom).

### Update the task name

1. Type `edit 1 n=New task`

**Result:** The first task is renamed to New task.

### Update the task deadline

1. Type `edit 1 e= 20 Nov 5pm`

**Result:** The first task's deadline is updated to 20 Nov, 5pm.

### Update the task period

1. Type `edit 1 s= 20 Nov 12 pm e= 20 Nov 3pm`

**Result:** The first task's period is updated to 20 Nov, 12pm to 3pm. 


### Remove the deadline of the task

1. Ensure that the first task has a deadline.
2. Type `edit 1 e=nil`.

**Result:** The first task's deadline is removed. It is also removed from the calendar.

### Remove the period of the task

1. Ensure that the first task has a period.
2. Type `edit 1 float`.

**Result:**  The first task's period is removed.


### Find tasks by name

1. Type `find go`.

**Result:** Tasks that contains the word "go" or part of it (al*go*rithm) will be shown (5 such tasks).

### Find tasks by tags

1. Type `find t=important`.

**Result:** Tasks that are tagged with "important" are shown (6 such tasks). Note that find command does not discriminate tags and names.

### Mark an uncompleted task as completed

1. Type `add "Slack"`.
2. Type `done **X**` (whereby X is the index of the task for "Slack").

**Result:** The task "Slack" is marked as completed.

### Mark a completed task as completed

1. Type `find go lifting`
2. Type `complete **X**` (whereby X is the index of the task for "Go lifting").

**Result:** The task list is unaffected. A message "Task is already completed" is shown.

### Undo a previous action

1. Type `add "Wrong Task"`.
2. Type `undo`.

**Result:** The task list is restored back to the state before step 1 was executed (i.e. "Wrong Task" should not exist after step 2).

### Undo when we first start the program

1. Ensure that we just started the program and did not do anything yet.
2. Type `undo`.

**Result:** The task list is unaffected. A message "There is no past command to undo" is shown.

### Redo something that we previously undo

1. Type `add Some new task`.
2. Type `undo`.
3. Type `redo`.

**Result:** The "Some new task" is added back to the list even though we undo it in step 2.

### Redo when we did not undo anything

1. Ensure that we never execute any undo.
2. Type `redo`.

**Result:** The task list is unaffected. A message "There is no past command to redo" is shown.

### Delete a certain task

1. Type `del 3`

**Result:** The third task is deleted.

### Clear the entire list

1. Type `clr all`

**Result:** The entire task list is empty.

### Create a copy of the tasklist anywhere

1. Type `saveas C:\Dropbox\`

**Result:** The files inside `data\` should be relocated to `C:\Dropbox\`. The status bar changes to `C:\Dropbox\SampleData.xml`.
