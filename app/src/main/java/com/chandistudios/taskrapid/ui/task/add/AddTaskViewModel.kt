package com.chandistudios.taskrapid.ui.task.add

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.R
import com.chandistudios.taskrapid.data.entity.NotiTime
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskIcon
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.repository.TaskIconRepository
import com.chandistudios.taskrapid.data.repository.TaskNotiTimeRepository
import com.chandistudios.taskrapid.data.repository.TaskRepository
import com.chandistudios.taskrapid.data.repository.TaskTypeRepository
import com.chandistudios.taskrapid.util.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AddTaskViewModel(
    private val taskRepository: TaskRepository = Graph.taskRepository,
    private val taskTypeRepository: TaskTypeRepository = Graph.taskTypeRepository,
    private val taskIconRepository: TaskIconRepository = Graph.taskIconRepository,
    private val taskNotiTimeRepository: TaskNotiTimeRepository = Graph.taskNotiTimeRepository
): ViewModel() {
    private val _state = MutableStateFlow(AddTaskViewState())

    val state: StateFlow<AddTaskViewState>
        get() = _state

    suspend fun saveTask(task: Task) : Long {
//        createTaskAddedNotification(task)
        return taskRepository.addTask(task)
    }

    init {
//        createNotificationChannel(context = Graph.appContext)
//        setOneTimeNotification()
        viewModelScope.launch {
            // Load Task Types and Icons
            taskTypeRepository.taskTypes().collect { types ->
                _state.value = AddTaskViewState(
                    taskTypes = types,
                    taskIcons = taskIconRepository.getIconList(),
                    notiTimes = taskNotiTimeRepository.getNotiTimeList()
                )
            }

        }
    }
}

data class AddTaskViewState(
    val taskTypes: List<TaskType> = emptyList(),
    val taskIcons: List<TaskIcon> = emptyList(),
    val notiTimes: List<NotiTime> = emptyList()
)

//private fun createNotificationChannel(context: Context) {
//    // Create the NotificationChannel, but only on API 26+ because
//    // the NotificationChannel class is new and not in the support library
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "AddTaskNotiChannel"
//        val descriptionText = "Notifications when adding Tasks"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
//            description = descriptionText
//        }
//        // register the channel
//        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//}
//
//private fun setOneTimeNotification() {
//    val workManager = WorkManager.getInstance(Graph.appContext)
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .setRequiresBatteryNotLow(true)
//        .build()
//
//    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
//        .setInitialDelay(10, TimeUnit.SECONDS)
//        .setConstraints(constraints)
//        .build()
//
//    workManager.enqueue(notificationWorker)
//
//    // Monitoring the state of the work (not required)
//    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
//        .observeForever { workInfo ->
//            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
//                createSuccessNotification()
//            } else {
////                createErrorNotification()
//            }
//        }
//}
//
//private fun createSuccessNotification() {
//    val notificationId = 1  // notificationId should be unique for every notification
//    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
//        .setSmallIcon(R.drawable.taskrapid_logo)
//        .setContentTitle("Success! Download complete")
//        .setContentText("Your countdown completed successfully")
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//    // display the notification
//    with(NotificationManagerCompat.from(Graph.appContext)) {
//        notify(notificationId, builder.build())
//    }
//}
//
//private fun createTaskCompletedNotification(task: Task) {
//    val notificationId = 2
//    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
//        .setSmallIcon(R.drawable.taskrapid_logo)
//        .setContentTitle("New task added!")
//        .setContentText("'${task.taskName}' is due on ${task.taskDate}")
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
//
//    with(NotificationManagerCompat.from(Graph.appContext)) {
//        notify(notificationId, builder.build())
//    }
//}
