package com.chandistudios.taskrapid.ui.home.typeTask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.data.room.TaskWithType
import com.chandistudios.taskrapid.util.viewModelProviderFactoryOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TypeTask (
    taskTypeId: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: TypeTaskViewModel = viewModel(
        key = "type_list_$taskTypeId",
        factory = viewModelProviderFactoryOf { TypeTaskViewModel(taskTypeId) }
    )
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        TaskList(taskList = viewState.tasks)
    }
}

@Composable
private fun TaskList(
    taskList: List<TaskWithType>,
) {
    // LazyColumn -> loads items when they are needed (i.e. in view)
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(taskList) { item ->
            TaskListItem(
                task = item.task,
                type = item.type,
                onClick = { /**TODO: viewtask */ },
                modifier = Modifier.fillParentMaxWidth(),
            )
        }
    }
}

@Composable
private fun TaskListItem(
    task: Task,
    type: TaskType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, taskName, taskType, icon, date) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )
        // title
        Text(
            text = task.taskName,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.constrainAs(taskName) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
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
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f
                )
                top.linkTo(taskName.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        // date
        Text(
            text = when {
                task.taskDate != null -> { task.taskDate } // .formatToString()
                else -> Date().formatToString()
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(date) {
                linkTo(
                    start = taskType.end,
                    end = parent.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                centerVerticallyTo(taskType)
                top.linkTo(taskName.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                }
        )
        // icon
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {

        }
    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(this)
}

private fun Long.toDateString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(this))
}