package com.chandistudios.taskrapid.data.entity

import androidx.room.*
import java.sql.Time
import java.util.*

@Entity(
    tableName = "task_types",
    indices = [
        Index("name", unique = true)
    ]
)

data class TaskType (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String
)
