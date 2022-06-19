package com.chandistudios.taskrapid.ui.home.typeTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.data.entity.Task
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

    suspend fun updateTask(task: Task) { // : Unit
        return taskRepository.updateTask(task)
    }

    suspend fun deleteTask(task: Task) { // : Unit
        return taskRepository.deleteTask(task)
    }

    init {
        viewModelScope.launch {
            taskRepository.getTasksWithType(taskTypeId).collect { list ->
                _state.value = TypeTaskViewState(
                    tasks = list
                )
            }
            taskRepository.getAllTasks().collect { list ->
                _state.value = TypeTaskViewState(
                    allTasks = list
                )
            }
        }
    }
}

data class TypeTaskViewState(
    val tasks: List<TaskWithType> = emptyList(),
    val allTasks: List<TaskWithType> = emptyList()
)

