package com.chandistudios.taskrapid.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.data.entity.TaskType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    private val _selectedTaskType = MutableStateFlow<TaskType?>(null)

    val state: StateFlow<HomeViewState>
        get() = _state

    fun onCategorySelected(TaskType: TaskType) {
        _selectedTaskType.value = TaskType
    }

    init {
        val categories = MutableStateFlow<List<TaskType>>(
            mutableListOf(
                TaskType(1, "All"),
                TaskType(2, "Upcoming"),
                TaskType(3, "Complete")
            )
        )

        viewModelScope.launch {
            combine(
                categories.onEach { category ->
                    if (categories.value.isNotEmpty() && _selectedTaskType.value == null) {
                        _selectedTaskType.value = category[0]
                    }
                },
                _selectedTaskType
            ) { categories, selectedCategory ->
                HomeViewState(
                    taskCategories = categories,
                    selectedTaskType = selectedCategory
                )
            }.collect { _state.value = it }
        }
    }
}

data class HomeViewState(
    val taskCategories: List<TaskType> = emptyList(),
    val selectedTaskType: TaskType? = null
)