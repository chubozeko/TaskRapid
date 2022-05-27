package com.chandistudios.taskrapid.data.entity

import androidx.room.*
import java.sql.Time
import java.util.*

@Entity(
    tableName = "tasks",
    indices = [
        Index("task_id", unique = true),
        Index("task_name", unique = true),
        Index("task_type_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = TaskType::class,
            parentColumns = ["id"],
            childColumns = ["task_type_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Task (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "task_id") val taskId: Long = 0,
    @ColumnInfo(name = "task_name") val taskName: String,
    @ColumnInfo(name = "task_description") val taskDescription: String?,
    @ColumnInfo(name = "task_date") val taskDate: String?,
    @ColumnInfo(name = "task_time") val taskTime: String?,
    @ColumnInfo(name = "location_x") val locationX: String?,    //  TODO (HW4)
    @ColumnInfo(name = "location_y") val locationY: String?,    //  TODO: (HW4)
    @ColumnInfo(name = "task_type_id") val taskTypeId: Long,
    @ColumnInfo(name = "task_completed") val taskCompleted: Int = 0,
    @ColumnInfo(name = "task_icon") val taskIcon: Long = 0
)
