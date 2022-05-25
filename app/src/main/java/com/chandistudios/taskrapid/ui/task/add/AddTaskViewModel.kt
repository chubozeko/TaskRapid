package com.chandistudios.taskrapid.ui.task.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.repository.TaskRepository
import com.chandistudios.taskrapid.data.repository.TaskTypeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddTaskViewModel(
    val taskRepository: TaskRepository = Graph.taskRepository,
    val taskTypeRepository: TaskTypeRepository = Graph.taskTypeRepository
): ViewModel() {
    private val _state = MutableStateFlow(AddTaskViewState())

    val state: StateFlow<AddTaskViewState>
        get() = _state

    suspend fun saveTask(task: Task) : Long {
        return taskRepository.addTask(task)
    }

    init {
        viewModelScope.launch {
            // Load Task Types
            taskTypeRepository.taskTypes().collect { types ->
                _state.value = AddTaskViewState(taskTypes = types)
            }
        }
    }
}

data class AddTaskViewState(
    val taskTypes: List<TaskType> = emptyList()
)