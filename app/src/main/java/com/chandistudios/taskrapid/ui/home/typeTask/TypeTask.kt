package com.chandistudios.taskrapid.ui.home.typeTask

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.room.TaskWithType
import com.chandistudios.taskrapid.util.viewModelProviderFactoryOf
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import com.chandistudios.taskrapid.ui.task.formatToString
import com.chandistudios.taskrapid.ui.task.toDateString
import com.chandistudios.taskrapid.ui.task.toTimeString
import java.sql.Time
import java.util.*

@Composable
fun TypeTask (
    taskTypeId: Long,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: TypeTaskViewModel = viewModel(
        key = "type_list_$taskTypeId",
        factory = viewModelProviderFactoryOf { TypeTaskViewModel(taskTypeId) }
    )
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        TaskList(taskList = viewState.tasks, navController = navController, viewModel = viewModel)
    }
}

@Composable
private fun TaskList(
    taskList: List<TaskWithType>,
    navController: NavController,
    viewModel: TypeTaskViewModel
) {
    // LazyColumn -> loads items when they are needed (i.e. in view)
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.background(color = MaterialTheme.colors.surface)
    ) {
        items(taskList) { item ->
            TaskListItem(
                task = item.task,
                type = item.type,
                onClick = {
                    val sharedPrefs: SharedPreferences = Graph.appContext.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE)
                    // save the selected task id to SharedPreferences
                    var editor = sharedPrefs.edit()
                    editor.putLong("selectedTask", item.task.taskId)
                    editor.apply()
                    navController.navigate("edittask")
                },
                modifier = Modifier
                    .fillParentMaxWidth()
                    .background(MaterialTheme.colors.surface),
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun TaskListItem(
    task: Task,
    type: TaskType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TypeTaskViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val isCompletedIcon = if (task.taskCompleted == 0) {
        Icons.Filled.CheckBoxOutlineBlank
    } else {
        Icons.Filled.CheckBox
    }
    val actionCompleteText = if (task.taskCompleted == 0) {
        "Mark as Complete"
    } else {
        "Mark as Incomplete"
    }
    val showDeleteDialog = remember { mutableStateOf(false) }

    val actionComplete = SwipeAction(
        onSwipe = {
            if (task.taskCompleted == 0) {
                coroutineScope.launch {
                    viewModel.updateTask(
                        Task(
                            taskId = task.taskId,
                            taskName = task.taskName,
                            taskDescription = task.taskDescription,
                            taskIcon = task.taskIcon,
                            taskTypeId = task.taskTypeId,
                            taskCompleted = 1,
                            taskDate = task.taskDate,
                            taskTime = task.taskTime,
                            locationX = task.locationX,
                            locationY = task.locationY,
                            taskNoti = task.taskNoti,
                            notificationTime = task.notificationTime,
                            notiTimeValue = task.notiTimeValue,
                        )
                    )
                    Toast.makeText(Graph.appContext,
                        "Task Completed!", Toast.LENGTH_SHORT).show()
                }
            }
        },
        icon = {
            Text(text = actionCompleteText)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.CheckBox,
                contentDescription = "Complete Task",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        },
        background = Color.Green
    )
    val actionIncomplete = SwipeAction(
        onSwipe = {
            if (task.taskCompleted == 1) {
                coroutineScope.launch {
                    viewModel.updateTask(
                        Task(
                            taskId = task.taskId,
                            taskName = task.taskName,
                            taskDescription = task.taskDescription,
                            taskIcon = task.taskIcon,
                            taskTypeId = task.taskTypeId,
                            taskCompleted = 0,
                            taskDate = task.taskDate,
                            taskTime = task.taskTime,
                            locationX = task.locationX,
                            locationY = task.locationY,
                            taskNoti = task.taskNoti,
                            notificationTime = task.notificationTime,
                            notiTimeValue = task.notiTimeValue,
                        )
                    )
                    Toast.makeText(Graph.appContext,
                        "Task marked as 'Incomplete'", Toast.LENGTH_SHORT).show()
                }
            }
        },
        icon = {
            Text(text = actionCompleteText)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.CheckBoxOutlineBlank,
                contentDescription = "Incomplete Task",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        },
        background = Color.Yellow
    )
    val actionDelete = SwipeAction(
        onSwipe = { showDeleteDialog.value = true },
        icon = {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete Task",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Delete Task")
        },
        background = MaterialTheme.colors.error
    )

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text(text = "Delete '${task.taskName}'") },
            text = { Text(text = "Are you sure you want to delete this task?") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.deleteTask(task)
                        }
                        showDeleteDialog.value = false
                        Toast.makeText(Graph.appContext,
                            "Task '${task.taskName}' has been Deleted!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                ) { Text(text = "Yes") }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog.value = false }
                ) { Text(text = "No") }
            }
        )
    }

    SwipeableActionsBox(
        startActions = when(task.taskCompleted) {
            0 -> listOf(actionComplete)
            else -> listOf(actionIncomplete)
        },
        endActions = listOf(actionDelete),
        swipeThreshold = 128.dp
    ) {
        ConstraintLayout(
            modifier = modifier.fillMaxWidth()
                .clickable { onClick() }
        ) {
            val (divider, taskName, taskType, completedIcon, icon, date, tTime, notiIcon) = createRefs()
            Divider(
                Modifier.constrainAs(divider) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
            )
            // task icon
            Icon(
                imageVector = Graph.taskIconRepository.getTaskIconWithId(task.taskIcon).icon,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
            )
            // title
            Text(
                text = task.taskName,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.constrainAs(taskName) {
                    top.linkTo(parent.top, 4.dp)
                    bottom.linkTo(taskType.top, 2.dp)
                    start.linkTo(icon.end, 8.dp)
                    width = Dimension.preferredWrapContent
                }
            )
            // task type
            Text(
                text = type.name,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.constrainAs(taskType) {
                    top.linkTo(taskName.bottom, 2.dp)
                    bottom.linkTo(parent.bottom, 4.dp)
                    start.linkTo(icon.end, 8.dp)
                    width = Dimension.preferredWrapContent
                }
            )
            // time
            Text(
                text = when {
                    task.taskTime != null -> { task.taskTime }
                    else -> Date().time.toTimeString()
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(4.dp)
                    .constrainAs(tTime) {
                        bottom.linkTo(parent.bottom, 4.dp)
                        end.linkTo(notiIcon.start, 16.dp)
                    }
            )
            // date
            Text(
                text = when {
                    task.taskDate != null -> { task.taskDate }
                    else -> Date().formatToString()
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(4.dp)
                    .constrainAs(date) {
                        bottom.linkTo(tTime.top, 2.dp)
                        end.linkTo(notiIcon.start, 16.dp)
                    }
            )
            // notification icon
            Icon(
                imageVector = Icons.Filled.NotificationsActive,
                contentDescription = null,
                modifier = Modifier
                    .alpha( task.taskNoti.toFloat() )
                    .size( when(task.taskNoti) { 1 -> 32.dp else -> 0.dp } )
                    .padding( when(task.taskNoti) { 1 -> 4.dp else -> 0.dp } )
                    .constrainAs(notiIcon) {
                        top.linkTo(parent.top, 4.dp)
                        bottom.linkTo(parent.bottom, 4.dp)
                        end.linkTo(completedIcon.start, 8.dp)
                    }
            )
            // task completed icon
            Icon(
                imageVector = isCompletedIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .constrainAs(completedIcon) {
                        top.linkTo(parent.top, 4.dp)
                        bottom.linkTo(parent.bottom, 4.dp)
                        end.linkTo(parent.end, 8.dp)
                    }
            )
        }
    }

}
