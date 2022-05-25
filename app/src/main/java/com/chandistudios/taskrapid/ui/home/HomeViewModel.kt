package com.chandistudios.taskrapid.ui.home

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.repository.TaskTypeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskTypeRepository: TaskTypeRepository = Graph.taskTypeRepository
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
                _selectedTaskType
            ) { types, selectedType ->
                HomeViewState(
                    taskTypes = types,
                    selectedTaskType = selectedType
                )
            }.collect { _state.value = it }
        }

        loadTaskTypesFromDb()
    }

    private fun loadTaskTypesFromDb() {
        val list = mutableListOf(
//            TaskType(name="All Tasks"),   /* TODO (HW3) */
            TaskType(name="Crucial"),
            TaskType(name="High"),
            TaskType(name="Medium"),
            TaskType(name="Low"),
//            TaskType(name="Upcoming"),
//            TaskType(name="Complete")
        )
        viewModelScope.launch {
            list.forEach { type -> taskTypeRepository.addTaskType(type) }
        }
    }
}

data class HomeViewState(
    val taskTypes: List<TaskType> = emptyList(),
    val selectedTaskType: TaskType? = null
)