package com.chandistudios.taskrapid.data.entity

import java.sql.Time
import java.util.*

data class Task (
    //  TODO: HW2
    val taskId: Long,
    val taskName: String,
    val taskDescription: String?,
    val taskDate: Date?,
    val taskTime: Time?,
    val taskCompleted: Boolean,
    val taskType: String,
    val locationX: String?,
    val locationY: String?,
    val creationTime: Date,
    val creatorId: Long,
)
