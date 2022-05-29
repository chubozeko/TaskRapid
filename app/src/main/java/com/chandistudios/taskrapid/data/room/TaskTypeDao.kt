package com.chandistudios.taskrapid.data.room

import androidx.room.*
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskTypeDao {

    // get all task types
    @Query(value = "SELECT * FROM task_types LIMIT 15")
    abstract fun taskTypes(): Flow<List<TaskType>>
    // get task type with taskId
    @Query(value = "SELECT * FROM task_types WHERE id = :id")
    abstract fun getTaskTypeWithId(id: Long): Flow<TaskType?>
    // get task type with taskName
    @Query(value = "SELECT * FROM task_types WHERE name = :name")
    abstract suspend fun getTaskTypeWithName(name: String): TaskType?

    // suspend fun => to make it async
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: TaskType): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<TaskType>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: TaskType)

    @Delete
    abstract suspend fun delete(entity: TaskType): Int
}