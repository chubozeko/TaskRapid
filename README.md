# Task Rapid
Task Rapid is a mobile assistant app that keeps track of the user's tasks. This was developed with Kotlin for the Mobile Computing (521045S) course at the University of Oulu.

![](app\src\main\ic_launcher-playstore.png)

## Tools used
- Android Studio 2021.2.1 (Chipmunk)
- Android SDK 11.0 (API Level 30)
- Kotlin
- Canva (for graphics)
---

## Versions
The app was implemented as a combination of 4 homework assignments, each containing different feature requirements.

### Homework 1 (v0.0.1)
> https://github.com/chubozeko/TaskRapid/releases/tag/v0.0.1

<strong> Required Features </strong>
- Created UI layouts and Navigation between Pages
    - Login
    - Home
    - Add Task
    - Sign Up
    - View Profile
    - Edit Profile
- Login page
    - A user can login to the app, with the User Profile credentials stored with SharedPreferences
- Home page
    - Task Type Tabs
    - List of Tasks (using LazyColumn)
    - TopAppBar with “View Profile” button
- Add Task page
    - Contains the main properties of a task (layout only)
    - Accessed when the user clicks on the FAB in the Home page

<strong> Additional Features (for bonus points) </strong>
- Account Creation (Sign Up page)
    - The user can create an account on the Sign Up page.
    - These credentials are stored in `SharedPreferences`.
- User Profile (View Profile & Edit Profile)
    - The user can view and edit their profile.

<strong> Challenges / Problems / Bugs </strong>
- Scrollability
    - Struggled to make the pages scrollable using Jetpack Compose, so I just resized everything to fit on one screen.
- Using `SharedPreferences` to store credentials
    - Not the best solution to store credentials, since it would only store them on the device itself rather than an external, encrypted database.
- Editing User Profile
    - Toast messages that were used for debugging should not appear when saving the changes to the Profile (business logic fix)
- Dark Mode
    - Fix colours used in Dark Mode for better visibility
---

### Homework 2 (v0.0.2)
> https://github.com/chubozeko/TaskRapid/releases/tag/v0.0.2

<strong> Required Features </strong>
- Add Task = page used for adding a new task
    - Task Name
    - Task Description
    - Task Date & Time
    - Task Type
    - Task Icon
- Edit Task = page used to update the selected task
    - Similar layout to Add Task, but with previously loaded task details
- Updated Task List Items
    - Used `ConstraintLayout` to design task list item layout
    - Added swipeable actions
- Used `Room` Database to store Task and TaskType details.
- Added a Date and Time Picker for assigning a date and time to a task
- Changed Task Types according to importance/priority:
    - Crucial
    - High
    - Medium
    - Low

<strong> Additional Features (for bonus points) </strong>
- Select a Task Icon
    - The user can select an icon that represents their task in the Add Task and Edit Task pages.
    - This icon can be seen in the task list on the Home page.
- Swipeable List Item
    - The user can swipe the task list item to perform Task actions:
        - Swipe Right = Mark Task as Complete/Incomplete
        - Swipe Left = Delete the Task

<strong> Challenges / Problems / Bugs </strong>
- Using `Room` to store task icons
    - Tried to implement the task icon list like the TaskDao and TaskTypeDao, but struggled to save the Material Icons (`ImageVector`) in the Room database. 
    - Resolved by “hard-coding” the TaskIcon objects without Room.
- Implementing the Date & Time Pickers
    - Spent a while parsing the task dates and times in order to properly store them
- View Task page
    - Planned to add a View Task page that shows the details of the selected task, but there were issues with loading the task details due to its asynchronous nature (displays `null` values).
    - Opted to use the Edit Task page to view the current task details that could be edited.
---

### Homework 3 (v0.0.3)
> https://github.com/chubozeko/TaskRapid/releases/tag/v0.0.3

<strong> Required Features </strong>
- Added a Task Notification with options when a Task has a specified date and time
    - At Task Time
    - 5 minutes before Task
    - 10 minutes before Task
    - 15 minutes before Task
    - 30 minutes before Task
    - 45 minutes before Task
    - 1 hour before Task
- Added Bell icon for Tasks with notifications in Task list
- Integrated Maps API to add a location to the Task
    - Latitude
    - Longitude

<strong> Challenges / Problems / Bugs </strong>
- Scheduling Task Notifications with `Work Manager`
    - Struggled to schedule the upcoming tasks that have notifications activated
    - Possible solution: using the `AlarmManager`
- Incomplete implementation with Location
    - Unable to implement the Location-based capabilities in time (i.e. show a notification when the user is nearby the upcoming Task’s location)
- Duplicate Pin markers if multiple locations were selected (only the last selected location got saved)
---

### Homework 4 (v0.0.4)
<i> *Not implemented before deadline </i>
---
