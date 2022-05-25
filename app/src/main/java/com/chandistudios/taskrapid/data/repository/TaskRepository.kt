package com.chandistudios.taskrapid.data.repository

import androidx.room.Query
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.room.TaskDao
import com.chandistudios.taskrapid.data.room.TaskWithType
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao
) {
    /**
     * Retrieve all the tasks
     * */
    fun tasks(): Flow<List<Task>> = taskDao.tasks()

    /**
     * Retrieve the task using their taskId
     * */
    fun getTaskWithId(taskId: Long) = taskDao.getTaskWithId(taskId)

    /**
     * Retrieve the tasks with a specific task type
     * @return a flow containing the list of tasks of a type using [taskTypeId]
     * */
    fun getTasksWithType(taskTypeId: Long): Flow<List<TaskWithType>> {
        return taskDao.getTasksWithType(taskTypeId)
    }

    /**
     * Add a task if it does not exist in the database
     *
     * @return the id of the newly added/created task
     * */
    suspend fun addTask(task: Task) = taskDao.insert(task)

    /**
     * Update a task if it exists in the database
     * */
    suspend fun updateTask(task: Task) = taskDao.update(task)

    /**
     * Delete a task if it exists in the database
     * */
    suspend fun deleteTask(task: Task) = taskDao.delete(task)
}