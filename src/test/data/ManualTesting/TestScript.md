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
	* [Autocompleting with command history](#autocomplete)
    * [Changing the theme and size of inbx_0](#preferences)

## Load sample data

1. Copy the `SampleData.xml` file into the `data\` folder (if the `data` directory does not exist inside the root directory where the app is located, create it).
2. Rename the file to `tasklist.xml`.
3. Start `Inbx0.jar` by double clicking it.
4. The application should display a list of tasks loaded from the sample data.

**Result:** The task list is loaded. When the app is opened for the first time, a list of overdue tasks that are still not done will appear in a pop up window. This window can be dismissed either by pressing the X which closes the window or by typing in <kbd>Alt</kbd> + <kbd>F4</kbd>.

## Open help

1. Type `help`

**Result:** A new window appears with a command summary page, listing out all the commands available for use. Can be dismissed by pressing <kbd>Enter</kbd>.

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

### List overdue tasks

1. Type `list overdue`.

**Result:** Only overdue tasks are shown (5 such tasks).

## Find Command

### Find tasks with a certain name

1. Type `find meeting`.

**Result:** Only tasks that have the word "meeting" are shown (3 such tasks).

### Find tasks with a certain tag

1. Type `find t=CS2103`.

**Result:** Only tasks that are tagged "urgent" are shown (2 such tasks).

### Find tasks using logic operators

1. Type `find EE2021 & i=r`.

**Result:** Only tasks that have the name EE2021 in them and are of importance red are shown (3 such tasks).

2. Type `find meeting or buy`.

**Result:** Only tasks that have the name "meeting" or "buy" in them red are shown (15 such tasks).

## Sort Command

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

## Show Command

### Show uncompleted tasks

1. Type `show inc`.

**Result:** Only tasks with isCompleted = false are shown (59 such tasks).

### Show high priority tasks

1. Type `show red`.

**Result:** Only tasks with importance = red are shown (11 such tasks).

### Show tasks happening on a day of the upcoming week.

1. Type `show momday`.

**Result:** Only tasks occuring on Monday are shown (3 such tasks).

### Show different categories of tasks.

1. Type `show floating`.

**Result:** Only floating tasks (no end dates / times) are shown (29 such tasks).

2. Type `show event`.

**Result:** Only event tasks are shown (10 such tasks).

3. Type `show deadline`.

**Result:** Only deadline tasks (no start dates / times) are shown (20 such tasks).

## Edit command

### Edit the task name

1. Type `edit 1 n=clear the table`

**Result:** The first task is renamed to "clear the table".

### Edit the task deadline

1. Type `edit 1 e= 26 Nov 3pm`

**Result:** The first task's deadline is edited to 26 Nov, 3pm.

### Edit the task period

1. Type `edit 2 s= 24 oct 12 pm e= 25 oct 10am`

**Result:** The second task's period is edited to 24 Oct 12pm to 25 Oct 10am.

### Edit the importance

1. Type `edit 4 i=g`

**Result:** The fourth task's importance is edited to green.  

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

## Delete Command

### Delete a task

1. Type `del 1`

**Result:** The first task is deleted. The list is updated.

2. Type `del 1`

**Result:** The first task in the list is deleted. The list is updated.

3. Type `del 100`.

**Result:** There will be an error message "The task index provided is invalid".

## Select Command

### Select a task in task list

1. Type `sel 2`.

**Result:** Task with index 2 will be selected. The information panel at the bottom will show the task details.

2. Type `sel 100`.

**Result:** There will be an error message "The task index provided is invalid".

## Undo Command

### Undo a previous action

1. Type `add abcd1234`.
2. Type `undo`.

**Result:** The task list first added a task called "abcd1234" to the list. Undo removes the task from the list.

### Undo multiple actions

1. Type `del 1`.
2. Type `del 2`.
2. Type `undo 2`.

**Result:** The first and second tasks gets deleted. Undo restores them back into the list.

## Redo Command

### Redo something that we previously undo

1. Type `add Some new task`.
2. Type `undo`.
3. Type `redo`.

**Result:** The "Some new task" is added back to the list even though we undo it in step 2.

### Undo multiple actions

1. Type `add Some task 1234`.
2. Type `add Some task 5678`.
3. Type `undo 2`.
2. Type `redo 2`.

**Result:** The "Some task 1234" and "Soem task 5678" tasks are added back to the list even though we undo them in step 3.

## Clear Command

### Clear the entire list

1. Type `clr all`

**Result:** The entire task list is empty.

2. Type `undo` to restore the tasklist back.

### Mark an uncompleted task as completed

1. Type `add "Slack"`.
2. Type `done **X**` (whereby X is the index of the task for "Slack").

**Result:** The task "Slack" is marked as completed.

## Done Command

### Mark a completed task as completed

1. Type `done 1`.

**Result:** The first task in the list is marked as completed and removed from the list view.

### Mark multiple tasks as completed

1. Type `done 1 4 5`.

**Result:** The first, fourth and fifth task in the list is marked as completed and removed from the list view.

2. Type `show com`.

**Result:** The completed  tasks will be shown. (4 such tasks)

## Reminder Command

### Set a reminder
1. Type `list`
2. Type `rem 1 s=now`.

**Result:** A reminder is set for the 1st task to display a reminder "now". A popup window appears showing the task details. It can be dismissed by pressing the OK button or pressing <kbd>Enter</kbd>.

### Set multiple reminders
1. Type `rem 1 s=1 hour`.
2. Type `rem 1 s=2 hour`.

**Result:** A reminder is set for the 1st task to display a reminder "1 hour from now". Another reminder is set to display a reminder "2 hours from now". The information panel at the bottom will show both the reminders.

## Saveas Command

### Create a copy of the tasklist anywhere

1. Type `saveas C:\Dropbox\tasklist.xml`

**Result:** The xml file inside `data\` should be saved to `C:\Dropbox\`.

## Command History / Autocomplete

1. Use the up and down arrow keys to cycle through pass commands that have been typed in.

## Color Preferences

1. Use <kbd>Ctrl</kbd>  + any number from 1 to 8 on the keyboard to change the color of the app to your liking.

# End of test script