package com.chandistudios.taskrapid.ui.home.typeTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.Graph
//import com.chandistudios.taskrapid.data.entity.Task
//import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.repository.TaskRepository
import com.chandistudios.taskrapid.data.room.TaskWithType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TypeTaskViewModel(
    private val taskTypeId: Long,
    private val taskRepository: TaskRepository = Graph.taskRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(TypeTaskViewState())
    val state: StateFlow<TypeTaskViewState>
        get() = _state

    init {
        viewModelScope.launch {
            taskRepository.getTasksWithType(taskTypeId).collect { list ->
                _state.value = TypeTaskViewState(
                    tasks = list
                )
            }
        }
    }
}

data class TypeTaskViewState(
    val tasks: List<TaskWithType> = emptyList()
)

//fun InitTempTasks (): MutableList<Task> {
//    val temp_tasks = mutableListOf<Task>()
//    temp_tasks.add(
//        Task(taskId = 1, taskName = "Buy Groceries", taskCompleted = false,
//        taskType = "Upcoming", taskDate = Date(), creationTime = Date(),
//        creatorId = Random(10).nextLong(),
//        locationX = null, locationY = null, taskDescription = "", taskTime = null)
//    )
//    temp_tasks.add(
//        Task(taskId = 1, taskName = "Flight", taskCompleted = true,
//            taskType = "Completed", taskDate = Date("18/12/2021"), creationTime = Date(),
//            creatorId = Random(10).nextLong(),
//            locationX = null, locationY = null, taskDescription = "", taskTime = null)
//    )
//    temp_tasks.add(
//        Task(taskId = 1, taskName = "Book Accommodation", taskCompleted = true,
//            taskType = "Completed", taskDate = Date("05/09/2021"), creationTime = Date(),
//            creatorId = Random(10).nextLong(),
//            locationX = null, locationY = null, taskDescription = "", taskTime = null)
//    )
//    temp_tasks.add(
//        Task(taskId = 1, taskName = "Lunch with Greg", taskCompleted = false,
//            taskType = "Upcoming", taskDate = Date("23/02/2022"), creationTime = Date(),
//            creatorId = Random(10).nextLong(),
//            locationX = null, locationY = null, taskDescription = "", taskTime = null)
//    )
//    temp_tasks.add(
//        Task(taskId = 1, taskName = "Rent Car", taskCompleted = true,
//            taskType = "Completed", taskDate = Date("20/04/2022"), creationTime = Date(),
//            creatorId = Random(10).nextLong(),
//            locationX = null, locationY = null, taskDescription = "", taskTime = null)
//    )
//    temp_tasks.add(
//        Task(taskId = 1, taskName = "Birthday Party", taskCompleted = false,
//            taskType = "Upcoming", taskDate = Date("16/05/2022"), creationTime = Date(),
//            creatorId = Random(10).nextLong(),
//            locationX = null, locationY = null, taskDescription = "", taskTime = null)
//    )
//    temp_tasks.add(
//        Task(taskId = 1, taskName = "Go to Gym", taskCompleted = false,
//            taskType = "Upcoming", taskDate = Date(), creationTime = Date(),
//            creatorId = Random(10).nextLong(),
//            locationX = null, locationY = null, taskDescription = "", taskTime = null)
//    )
//    return temp_tasks
//}

