package com.chandistudios.taskrapid.data.room

import androidx.room.*
import com.chandistudios.taskrapid.data.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskDao {

    // get task with taskId
    @Query(value = "SELECT * FROM tasks WHERE task_id = :taskId")
    abstract fun getTaskWithId(taskId: Long): Task?

    // get task with taskName
    @Query(value = "SELECT * FROM tasks WHERE task_name = :name")
    abstract suspend fun getTaskWithName(name: String): Task?

    // get all tasks
    @Query(value = "SELECT * FROM tasks LIMIT 15")
    abstract fun tasks(): Flow<List<Task>>

    // get all tasks with taskType
    @Query("""
        SELECT tasks.* 
        FROM tasks INNER JOIN task_types ON tasks.task_type_id = task_types.id
        WHERE task_type_id = :taskTypeId
    """)
    abstract fun getTasksWithType(taskTypeId: Long): Flow<List<TaskWithType>>

    // suspend fun => to make it async
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<Task>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Task)

    @Delete
    abstract suspend fun delete(entity: Task) // : Long
}