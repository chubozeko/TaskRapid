package com.chandistudios.taskrapid.data.repository

import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.room.TaskDao
import com.chandistudios.taskrapid.data.room.TaskTypeDao
import kotlinx.coroutines.flow.Flow

class TaskTypeRepository(
    private val taskTypeDao: TaskTypeDao
) {
    /**
     * Retrieves all task types
     * */
    fun taskTypes(): Flow<List<TaskType>> = taskTypeDao.taskTypes()

    /**
     * Retrieves a task type with a specific id
     * */
    fun getTaskTypeWithId(id: Long) = taskTypeDao.getTaskTypeWithId(id)

    /**
     * Add a task type if it does not exist in the database
     * @return the id of the newly added/created task type
     * */
    suspend fun addTaskType(taskType: TaskType): Long {
        return when (val local = taskTypeDao.getTaskTypeWithName(taskType.name)) {
            null -> taskTypeDao.insert(taskType)    // null => taskType does not exist, INSERT
            else -> local.id    // if taskType exists, return its id
        }
    }
}