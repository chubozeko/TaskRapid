package com.chandistudios.taskrapid.data.room

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import java.util.*

class TaskWithType {
    @Embedded
    lateinit var task: Task

    @Relation(parentColumn = "task_type_id", entityColumn = "id")
    lateinit var _types: List<TaskType>

    @get:Ignore
    val type: TaskType
        get() = _types[0]

    /**
     * Allow this class to be destructured by consumers
     * */
    operator fun component1() = task
    operator fun component2() = type

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is TaskWithType -> task == other.task && _types == other._types
        else -> false
    }

    override fun hashCode(): Int = Objects.hash(task, _types)

}