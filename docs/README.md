# Jung User Guide

Jung is a desktop app for managing tasks, optimized for use via a Command Line Interface (CLI) while still having the **benefits of a Graphical User Interface** (GUI). If you can type fast, Jung can get your task management done faster than traditional GUI apps.
Jung speaks Singlish and has a bit of an attitude - don't take it personally!

![Screenshot of Jung - Task Management Assistant](https://github.com/zpnoob/ip/blob/master/docs/Ui.png?raw=true)

## Quick Start
1. Ensure you have Java 17 or above installed in your Computer
2. Download the latest jung.jar from [here](https://github.com/zpnoob/jung/releases)
3. Copy the file to the folder you want to use as your home folder for Jung
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar jung.jar` command to run the application.
5. Type the command in the command box and press Enter to execute it.
6. Refer to the [Features](#Features) below for details of each command.

## Features
**Notes about the command format:** 
+ Words in `UPPER_CASE` are the parameters to be supplied by the user. 
eg in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo buy groceries`
+ Items in square brackets are optional. eg `find KEYWORD` can be used as `find book`
 or `find meeting`
+ Date and time should be in the format `d/M/yyyy HHmm`. eg `15/9/2025 1430` represents 15th September 2025, 2.30pm



### Adding a todo: `todo`

Adds a simple todo task to your task list.

Format: `todo description`

Examples: 
+ `todo buy groceries`
+ `todo finish homework`

Expected Output:
```
Aiya okay lah, I help you add this task: 
  [T][ ] buy groceries
Now you got 1 task only. Not bad ah, still manageable.
```

### Adding a deadline: `deadline`

Adds a task with a specific deadline to your task list.

Format: `deadline DESCRIPTION /by DATE_TIME`

Examples: 
+ `deadline submit assignment /by 15/3/2024 2359`
+ `deadline pay bills /by 30/12/2024 1800`
+ `deadline book flight /by 1/1/2025 0900`

Expected Output: 
```
Haiz, another one ah? Fine lor, added:
  [D][ ] submit assignment (by: 15 Mar 2024, 11:59 PM)
Still okay lah. Now you got 2 tasks.
```

### Adding an Event: `event`

Format:`event DESCRIPTION /from START_TIME /to END_TIME`

Examples: 
+ `event team meeting /from 15/3/2024 1400 /to 15/3/2024 1600`
+ `event conference /from 1/4/2024 0900 /to 1/4/2024 1700`

Expected Output: 
```
Wah, you very productive today hor? Added:
  [E][ ] team meeting (from: 15 Mar 2024, 2:00 PM to: 15 Mar 2024, 4:00 PM)
Still okay lah. Now you got 3 tasks.
```

### Listing all tasks: `list`
Shows all tasks in your task list.

Format: `list`

Expected output: 
```aiignore
Okay here are your tasks lah:
1. [T][ ] buy groceries
2. [D][ ] submit assignment (by: 15 Mar 2024, 11:59 PM)
3. [E][ ] team meeting (from: 15 Mar 2024, 2:00 PM to: 15 Mar 2024, 4:00 PM)
```

### Marking a task as done: `mark`
Marks the specified task as completed.

Format:`mark INDEX`
+ Marks the task as the specified `INDEX` as done.
+ The index refers to the index number shown in the displayed task list.
+ The index **must be a positive integer 1,2,3,...**

Examples: 
+ `list` followed by `mark 2` marks the 2nd task in the task list as done.

Expected Output: 
```
Steady lah! This task is done:
  [D][X] submit assignment (by: 15 Mar 2024, 11:59 PM)
```

### Unmarking a task: `unmark`
Marks the specified task as not completed.

Format: `unmark INDEX`
+ Marks the task at the specified `INDEX` as not done
+ The index refers to the index number shown in the displayed task list.
+ The index **must be a positive integer 1, 2, 3, …**

Examples: 
+ `list` followed by `unmark 2` marks the 2nd task in the task list as not done.

Expected Output:
```aiignore
Haiz, back to not done ah:
  [D][ ] submit assignment (by: 15 Mar 2024, 11:59 PM)
```

### Deleting a task: `delete`
Deletes the specified task from the task list.
Format: `delete INDEX`
+ Deletes the task at the specified `INDEX`.
+ The index refers to the index number shown in the displayed task list.
+ The index **must be a positive integer 1, 2, 3, …**
Examples: 
+ `list` followed by a `delete 2` deletes the 2nd task in the task list.

Expected Output:
```aiignore
Okay lor, deleted this task:
  [D][X] submit assignment (by: 15 Mar 2024, 11:59 PM)
Now you got 2 tasks left. Can finish one!
```

### Finding tasks: `find`
Finds tasks whose descriptions contain the given keyword.

Format: `find KEYWORD`
+ The search is case-insensitive. e.g. `meeting` will match `Meeting`
+ Tasks matching the keyword will be displayed.
Examples: 
+ `find book` returns `booking` and `book club`
+ `find Meeting` returns `team meeting` and `client meeting`

Expected Output:
```aiignore
Found some tasks that match your search:
1. [E][ ] team meeting (from: 15 Mar 2024, 2:00 PM to: 15 Mar 2024, 4:00 PM)
2. [T][ ] book meeting room
```

### Undoing the last action: `undo`
Reverses the most recent action that modified the task list.

Format: `undo`
+ Only actions that modify the task list can be undone (add, delete, mark, unmark).
+ You can only undo the most recent action.

Examples: 
+ After accidentally deleting a task with `delete 1`, you can use `undo` to restore it.
+ After marking a task as done with `mark 2`, you can use `undo` to unmark it.

Expected Output:
```aiignore
Okay lah, I help you undo that mistake:
Undone: removed task: [T][X] buy groceries
```

### Exiting the program: `bye`
Exits the program.

Format:`bye`

Expected Output:
```aiignore
Bye bye! Don't miss me too much ah!
```
**Note: Entering `bye` will simply close the GUI with no output**

## Error Handling
Jung will show helpful error messages when you make mistakes:

### Invalid command format: 
```aiignore
Input: todo
Output: Aiyo, your todo description missing leh! Try: todo [what you want to do]
```

### Invalid date format:
```aiignore
Input: deadline homework /by tomorrow
Output: Wah lau, your date format wrong lah! Please use d/M/yyyy HHmm (like 15/3/2024 1430). Don't anyhow type!
```

### Invalid task number: 
```aiignore
Input: mark 999
Output: That task number doesn't exist lah! Check your list first, then try again.
```

### Invalid event times
```aiignore
Input: event meeting /from 15/3/2024 1600 /to 15/3/2024 1400
Output: Aiyo! Start time cannot be later than end time lah!
```

## Data Storage
+ Jung automatically saves your tasks to a file called `jung.txt` in the `data` folder.
+ Tasks are saved automatically after every command that changes the task list
+ If the data file is corrupted, Jung will skip the corrupted entries and inform you.

## FAQ
## FAQ

**Q: Can I edit the data file directly?**  
A: Jung task data are saved as a text file at `[JAR file location]/data/jung.txt`. Advanced users are welcome to update data directly by editing that file, but be careful with the format!

**Q: Can I use Jung without the GUI?**  
A: Jung is designed to be used with its GUI interface. While it uses command-line style commands, the GUI provides a better user experience.

**Q: What happens if I enter an invalid date like Feb 30?**  
A: Jung will detect invalid dates and show you an error message asking you to check your calendar.

**Q: Why does Jung speak Singlish?**  
A: Because lah! Jung has personality one, not like those boring task managers. Don’t worry, you’ll get used to it! 😊


## Command Summary

| Action   | Format                                                                 | Examples                                                                 |
|----------|------------------------------------------------------------------------|--------------------------------------------------------------------------|
| Add Todo | `todo DESCRIPTION`                                                     | e.g., `todo buy groceries`                                               |
| Add Deadline | `deadline DESCRIPTION /by DATE_TIME`                               | e.g., `deadline submit report /by 15/3/2024 2359`                        |
| Add Event | `event DESCRIPTION /from START_TIME /to END_TIME`                     | e.g., `event meeting /from 15/3/2024 1400 /to 15/3/2024 1600`            |
| List     | `list`                                                                 |                                                                          |
| Mark     | `mark INDEX`                                                           | e.g., `mark 3`                                                           |
| Unmark   | `unmark INDEX`                                                         | e.g., `unmark 1`                                                         |
| Delete   | `delete INDEX`                                                         | e.g., `delete 2`                                                         |
| Find     | `find KEYWORD`                                                         | e.g., `find book`                                                        |
| Undo     | `undo`                                                                 |                                                                          |
| Exit     | `bye`                                                                  |                                                                          |

