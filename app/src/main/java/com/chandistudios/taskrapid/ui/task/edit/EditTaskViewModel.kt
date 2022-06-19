package com.chandistudios.taskrapid.ui.task.edit

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.data.entity.NotiTime
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskIcon
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.repository.TaskIconRepository
import com.chandistudios.taskrapid.data.repository.TaskNotiTimeRepository
import com.chandistudios.taskrapid.data.repository.TaskRepository
import com.chandistudios.taskrapid.data.repository.TaskTypeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditTaskViewModel(
    private val taskRepository: TaskRepository = Graph.taskRepository,
    private val taskTypeRepository: TaskTypeRepository = Graph.taskTypeRepository,
    private val taskIconRepository: TaskIconRepository = Graph.taskIconRepository,
    private val taskNotiTimeRepository: TaskNotiTimeRepository = Graph.taskNotiTimeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(EditTaskViewState(selectedTask = null))

    val state: StateFlow<EditTaskViewState>
        get() = _state

    suspend fun updateTask(task: Task) { // : Unit
        return taskRepository.updateTask(task)
    }

    suspend fun deleteTask(task: Task) { // : Unit
        return taskRepository.deleteTask(task)
    }

    init {
        viewModelScope.launch {
            val sharedPrefs: SharedPreferences = Graph.appContext.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE)
            val taskId = sharedPrefs.getLong("selectedTask", -1)
            combine(
                taskRepository.getTaskWithId(taskId).onEach {  },
                taskTypeRepository.taskTypes().onEach {  },
            ) { selectedTask, types ->
                EditTaskViewState(
                    selectedTask = selectedTask,
                    taskTypes = types,
                    taskIcons = taskIconRepository.getIconList(),
                    notiTimes = taskNotiTimeRepository.getNotiTimeList()
                )
            }.collect { _state.value = it }

        }
    }
}

data class EditTaskViewState(
    val selectedTask: Task?,
    val taskTypes: List<TaskType> = emptyList(),
    val taskIcons: List<TaskIcon> = emptyList(),
    val notiTimes: List<NotiTime> = emptyList()
)