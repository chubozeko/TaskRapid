package com.chandistudios.taskrapid.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.R
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.repository.TaskRepository
import com.chandistudios.taskrapid.data.repository.TaskTypeRepository
import com.chandistudios.taskrapid.ui.task.getDateTimeDifference
import com.chandistudios.taskrapid.ui.task.toDate
import com.chandistudios.taskrapid.ui.task.toDateTime
import com.chandistudios.taskrapid.ui.task.toTime
import com.chandistudios.taskrapid.util.NotificationWorker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val taskTypeRepository: TaskTypeRepository = Graph.taskTypeRepository,
    private val taskRepository: TaskRepository = Graph.taskRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    private val _selectedTaskType = MutableStateFlow<TaskType?>(null)

    val state: StateFlow<HomeViewState>
        get() = _state

    fun onTypeSelected(taskType: TaskType) {
        _selectedTaskType.value = taskType
    }

    init {
        // To run coroutines on another thread, we use combine()
        viewModelScope.launch {
            combine(
                taskTypeRepository.taskTypes().onEach { list ->
                    if (list.isNotEmpty() && _selectedTaskType.value == null) {
                        // Select the 1st type
                        _selectedTaskType.value = list[0]
                    }
                },
                taskRepository.getAllTasks().onEach { },
//                taskRepository.getIncompleteTasks().onEach { },
                _selectedTaskType
            ) { types, tasks, selectedType ->
                var taskList: List<Task> = emptyList()
                tasks.forEach { t -> taskList = listOf(t.task) }
                HomeViewState(
                    taskTypes = types,
                    selectedTaskType = selectedType,
                    pendingTasks = taskList
                )
            }.collect { _state.value = it }
        }

        loadTaskTypesFromDb()
        createNotificationChannel(context = Graph.appContext)
        /* TODO (HW3): get the duration of the task with the soonest notification (smallest time until noti) */
//        findNextTaskNotification(state.value.pendingTasks)
    }

    private fun loadTaskTypesFromDb() {
        val list = mutableListOf(
//            TaskType(name="All Tasks"),
            TaskType(name="Crucial"),
            TaskType(name="High"),
            TaskType(name="Medium"),
            TaskType(name="Low"),
        )
        viewModelScope.launch {
            list.forEach { type -> taskTypeRepository.addTaskType(type) }
        }
    }
}

data class HomeViewState(
    val taskTypes: List<TaskType> = emptyList(),
    val selectedTaskType: TaskType? = null,
    val pendingTasks: List<Task>? = emptyList()
)

private fun createNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "HomeNotiChannel"
        val descriptionText = "Notifications in the Home page"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        // register the channel
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun setOneTimeNotificationWithDuration(timeInMillis: Long, task: Task?, pendingTasks: List<Task>?) {
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(timeInMillis, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .setInputData(
            Data.Builder().putLong("notiTime",
                when (task) {
                    null -> 0L
                    else -> getDateTimeDifference(
                        "${task.taskDate} ${task.notificationTime}".toDateTime(),
                        Calendar.getInstance().timeInMillis
                    )
                })
                .build()
        )
        .build()

    workManager.enqueue(notificationWorker)


    // Monitoring the state of the work (not required)
    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                if (task != null)
                    createSuccessNotification(task)
                workManager.cancelAllWork()
                findNextTaskNotification(pendingTasks)
            } else {
                workManager.cancelAllWork()
                findNextTaskNotification(pendingTasks)
            }
        }
}

private fun createSuccessNotification(task: Task?) {
    val notificationId = 1  // notificationId should be unique for every notification
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.taskrapid_logo)
        .setContentTitle("Task '${task?.taskName}' is due at ${task?.taskTime}")
        .setContentText("${task?.taskDescription}")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    // display the notification
    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}

private fun createTaskCompletedNotification(task: Task) {
    val notificationId = 2
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.taskrapid_logo)
        .setContentTitle("New task added!")
        .setContentText("'${task.taskName}' is due on ${task.taskDate}")
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun findNextTaskNotification(pendingTasks: List<Task>?) {
    /* TODO (HW3): get the duration of the task with the soonest notification (smallest time until noti) */
    if (!pendingTasks.isNullOrEmpty()) {
        val earliestTask = getTaskWithEarliestNotification(pendingTasks)

        Log.i("Notifications", "Earliest Task: ${earliestTask?.taskName} in ${getNotificationDelay(earliestTask)} ms")
        setOneTimeNotificationWithDuration(getNotificationDelay(earliestTask), earliestTask, pendingTasks)
    } else {
        Log.i("Notifications", "Currently [${pendingTasks?.size}] Tasks pending (check again in a minute...)")
        setOneTimeNotificationWithDuration(60000, null, pendingTasks)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getTaskWithEarliestNotification(tasks: List<Task>?): Task? {
    var minDateTimeDiff: Long = 0
    var earliestTaskIndex = 0
    tasks?.forEach { task ->
        if (task.taskNoti == 1) {
            val diff = getDateTimeDifference(
                "${task.taskDate} ${task.notificationTime}".toDateTime(),
                Calendar.getInstance().timeInMillis
            )
            Log.i("Notifications",
                "Task: ${task.taskName} ; Task NotiTime ms: " +
                        "${task.taskDate} ${task.notificationTime}".toDateTime() + " ; Current Time ms: " +
                        Calendar.getInstance().timeInMillis + ";")

            if (diff < minDateTimeDiff && diff > 0) {
                earliestTaskIndex = tasks.indexOf(task)
                minDateTimeDiff = diff
            }
        }
    }
    return tasks?.get(earliestTaskIndex)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getNotificationDelay(task: Task?): Long {
    return getDateTimeDifference(
        "${task?.taskDate} ${task?.notificationTime}".toDateTime(),
        Calendar.getInstance().timeInMillis
    )
}