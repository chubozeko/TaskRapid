package com.chandistudios.taskrapid.ui.task.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.repository.TaskRepository
import com.chandistudios.taskrapid.data.repository.TaskTypeRepository
import com.chandistudios.taskrapid.ui.task.add.AddTaskViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditTaskViewModel(
    val taskRepository: TaskRepository = Graph.taskRepository,
    val taskTypeRepository: TaskTypeRepository = Graph.taskTypeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(EditTaskViewState())

    val state: StateFlow<EditTaskViewState>
        get() = _state

    suspend fun updateTask(task: Task) { // : Unit
        return taskRepository.updateTask(task)
    }

    init {
        viewModelScope.launch {
            // Load Task Types
            taskTypeRepository.taskTypes().collect { types ->
                _state.value = EditTaskViewState(taskTypes = types)
            }
        }
    }
}

data class EditTaskViewState(
    val taskTypes: List<TaskType> = emptyList()
)