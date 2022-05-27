package com.chandistudios.taskrapid

import android.content.Context
import androidx.room.Room
import com.chandistudios.taskrapid.data.repository.TaskRepository
import com.chandistudios.taskrapid.data.repository.TaskTypeRepository
import com.chandistudios.taskrapid.data.room.TaskRapidDatabase

/**
 * A simple singleton dependency graph
 * */
object Graph {
    // acts as the Dependency Injection; good for decoupling
    lateinit var database: TaskRapidDatabase
        private set

    lateinit var appContext: Context
    val taskRepository by lazy {
        TaskRepository(
            taskDao = database.taskDao()
        )
    }

    val taskTypeRepository by lazy {
        TaskTypeRepository(
            taskTypeDao = database.taskTypeDao()
        )
    }

    fun provide(context: Context) {
        appContext = context
        database = Room.databaseBuilder(context, TaskRapidDatabase::class.java, "task_rapid.db")
            .fallbackToDestructiveMigration()   // it deletes everything and rebuilds the db
            .build()                            // NB! DO NOT use in a production app!
    }
}