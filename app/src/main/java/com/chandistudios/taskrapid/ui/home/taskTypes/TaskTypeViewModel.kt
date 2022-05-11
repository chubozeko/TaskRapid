package com.chandistudios.taskrapid.ui.home.taskTypes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.data.entity.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class TaskTypeViewModel : ViewModel() {
    private val _state = MutableStateFlow(TaskTypeViewState())
    private val _selectedTask = MutableStateFlow<Task?>(null)
    val state: StateFlow<TaskTypeViewState>
        get() = _state

    fun onTaskSelected(task: Task) {
        _selectedTask.value = task
    }

    init {
        val list = InitTempTasks()
//        val list = mutableListOf<Task>()
//        for (x in 1..10) {
//            list.add(
//                Task(
//                    taskId = x.toLong(),
//                    taskName = "Task #$x",
//                    taskCompleted = Random.nextBoolean(),
//                    taskType = when (Random.nextInt(0, 2)) {
//                        1 -> "Completed"
//                        else -> "Upcoming"
//                    },
//                    taskDate = Date(),
//                    creationTime = Date(),
//                    creatorId = Random(10).nextLong(),
//                    locationX = null,
//                    locationY = null,
//                    taskDescription = "",
//                    taskTime = null
//                )
//            )
//        }

        viewModelScope.launch {
            _state.value = TaskTypeViewState(tasks = list)
            _selectedTask.value = null
        }
    }
}

data class TaskTypeViewState(
    val tasks: List<Task> = emptyList(),
    val selectedTask: Task? = null
)

fun InitTempTasks (): MutableList<Task> {
    val temp_tasks = mutableListOf<Task>()
    temp_tasks.add(
        Task(taskId = 1, taskName = "Buy Groceries", taskCompleted = false,
        taskType = "Upcoming", taskDate = Date(), creationTime = Date(),
        creatorId = Random(10).nextLong(),
        locationX = null, locationY = null, taskDescription = "", taskTime = null)
    )
    temp_tasks.add(
        Task(taskId = 1, taskName = "Flight", taskCompleted = true,
            taskType = "Completed", taskDate = Date("18/12/2021"), creationTime = Date(),
            creatorId = Random(10).nextLong(),
            locationX = null, locationY = null, taskDescription = "", taskTime = null)
    )
    temp_tasks.add(
        Task(taskId = 1, taskName = "Book Accommodation", taskCompleted = true,
            taskType = "Completed", taskDate = Date("05/09/2021"), creationTime = Date(),
            creatorId = Random(10).nextLong(),
            locationX = null, locationY = null, taskDescription = "", taskTime = null)
    )
    temp_tasks.add(
        Task(taskId = 1, taskName = "Lunch with Greg", taskCompleted = false,
            taskType = "Upcoming", taskDate = Date("23/02/2022"), creationTime = Date(),
            creatorId = Random(10).nextLong(),
            locationX = null, locationY = null, taskDescription = "", taskTime = null)
    )
    temp_tasks.add(
        Task(taskId = 1, taskName = "Rent Car", taskCompleted = true,
            taskType = "Completed", taskDate = Date("20/04/2022"), creationTime = Date(),
            creatorId = Random(10).nextLong(),
            locationX = null, locationY = null, taskDescription = "", taskTime = null)
    )
    temp_tasks.add(
        Task(taskId = 1, taskName = "Birthday Party", taskCompleted = false,
            taskType = "Upcoming", taskDate = Date("16/05/2022"), creationTime = Date(),
            creatorId = Random(10).nextLong(),
            locationX = null, locationY = null, taskDescription = "", taskTime = null)
    )
    temp_tasks.add(
        Task(taskId = 1, taskName = "Go to Gym", taskCompleted = false,
            taskType = "Upcoming", taskDate = Date(), creationTime = Date(),
            creatorId = Random(10).nextLong(),
            locationX = null, locationY = null, taskDescription = "", taskTime = null)
    )
    return temp_tasks
}

