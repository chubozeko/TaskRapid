package com.chandistudios.taskrapid.ui.home.taskTypes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.chandistudios.taskrapid.data.entity.Task
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskTypes (
    modifier: Modifier = Modifier
) {
    val viewModel: TaskTypeViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        TaskList(list = viewState.tasks)
    }
}

@Composable
private fun TaskList(
    list: List<Task>
) {
    // LazyColumn -> loads items once they are need (i.e. when they are in view)
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            TaskListItem(
                task = item,
                onClick = {  },
                modifier = Modifier.fillParentMaxWidth(),
            )
        }
    }
}

@Composable
private fun TaskListItem(
    task: Task,
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
//                width = Dimension.preferredWrapContent
            }
        )
        // task type
        Text(
            text = task.taskType,
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
                task.taskDate != null -> { task.taskDate.formatToString() }
                else -> Date().formatToString()
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(date) {
//                linkTo(
//                    start = taskType.end,
//                    end = parent.start,
//                    startMargin = 8.dp,
//                    endMargin = 16.dp,
//                    bias = 0f
//                )
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
//                centerVerticallyTo(taskType)
//                top.linkTo(taskName.bottom, 6.dp)
//                bottom.linkTo(parent.bottom, 10.dp)
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