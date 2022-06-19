package com.chandistudios.taskrapid.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType

@Database(
    entities = [Task::class, TaskType::class],
    version = 7,
    exportSchema = false
)

abstract class TaskRapidDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskTypeDao(): TaskTypeDao
}
