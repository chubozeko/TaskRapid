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
     * Retrieve all incomplete tasks
     * */
    fun getIncompleteTasks(): Flow<List<Task>> = taskDao.getIncompleteTasks()

    /**
     * Retrieve the task using their taskId
     * */
    fun getTaskWithId(taskId: Long): Flow<Task?> = taskDao.getTaskWithId(taskId)

    /**
     * Retrieve the tasks with a specific task type
     * @return a flow containing the list of tasks of a type using [taskTypeId]
     * */
    fun getTasksWithType(taskTypeId: Long): Flow<List<TaskWithType>> {
        return taskDao.getTasksWithType(taskTypeId)
//        if (taskTypeId.equals(5)) {
//            return taskDao.getAllTasks()
//        } else {
//            return taskDao.getTasksWithType(taskTypeId)
//        }
    }

    /**
     * Retrieve all the tasks
     * @return a flow containing the list of tasks of all types
     * */
    fun getAllTasks(): Flow<List<TaskWithType>> {
        return taskDao.getAllTasks()
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