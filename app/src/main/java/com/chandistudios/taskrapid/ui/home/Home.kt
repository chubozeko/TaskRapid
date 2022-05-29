package com.chandistudios.taskrapid.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.R
import com.chandistudios.taskrapid.ui.home.typeTask.TypeTask
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val viewState by viewModel.state.collectAsState()

    val selectedTaskType = viewState.selectedTaskType

    if (viewState.taskTypes.isNotEmpty() && selectedTaskType != null) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(
                selectedTaskType = selectedTaskType,
                taskTypes = viewState.taskTypes,
                onTaskTypeSelected = viewModel::onTypeSelected,
                navController = navController
            )
        }
    }
}

@Composable
fun HomeContent(
    selectedTaskType: TaskType,
    taskTypes: List<TaskType>,
    onTaskTypeSelected: (TaskType) -> Unit,
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "addtask") },
                contentColor = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.primary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor,
                navController = navController,
            )

            TaskTypeTabs(
                taskTypes = taskTypes,
                selectedTaskType = selectedTaskType,
                onTaskTypeSelected = onTaskTypeSelected,
            )

            TypeTask (
                taskTypeId = selectedTaskType.id,
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color,
    navController: NavController,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            // Search Tasks
            IconButton( onClick = { /* TODO (HW4)*/ } ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search for Task")
            }
            // View Profile
            IconButton( onClick = { navController.navigate(route = "viewprofile") } ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "View Profile")
            }
        }
    )
}

@Composable
private fun TaskTypeTabs(
    taskTypes: List<TaskType>,
    selectedTaskType: TaskType,
    onTaskTypeSelected: (TaskType) -> kotlin.Unit
) {
    val selectedIndex = taskTypes.indexOfFirst { it == selectedTaskType }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 24.dp,
        indicator = emptyTabIndicator,
        modifier = Modifier.fillMaxWidth(),
    ) {
        taskTypes.forEachIndexed { index, type ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onTaskTypeSelected(type) }
            ) {
                ChoiceChipContent(
                    text = type.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colors.secondary.copy(alpha = 0.95f)
            else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> MaterialTheme.colors.primaryVariant.copy(alpha = 0.75f)
            else -> MaterialTheme.colors.onSurface
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}