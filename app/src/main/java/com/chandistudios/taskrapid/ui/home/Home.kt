package com.chandistudios.taskrapid.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.chandistudios.taskrapid.ui.home.taskTypes.TaskTypes
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val viewState by viewModel.state.collectAsState()

    val selectedTaskCategory = viewState.selectedTaskType

    if (viewState.taskCategories.isNotEmpty() && selectedTaskCategory != null) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(
                selectedTaskType = selectedTaskCategory,
                taskTypes = viewState.taskCategories,
                onTaskTypeSelected = viewModel::onCategorySelected,
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
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "addtask") },
                contentColor = Color.Blue,
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
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor,
            )

            TaskCategoryTabs(
                taskCategories = taskTypes,
                selectedTaskCategory = selectedTaskType,
                onTaskCategorySelected = onTaskTypeSelected,
            )

            TaskTypes (
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
//            IconButton( onClick = {} ) {
//                Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(R.string.search))
//            }
//            IconButton( onClick = {} ) {
//                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.account))
//            }
        }
    )
}

@Composable
private fun TaskCategoryTabs(
    taskCategories: List<TaskType>,
    selectedTaskCategory: TaskType,
    onTaskCategorySelected: (TaskType) -> kotlin.Unit
) {
    val selectedIndex = taskCategories.indexOfFirst { it == selectedTaskCategory }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 24.dp,
        indicator = emptyTabIndicator,
        modifier = Modifier.fillMaxWidth(),
    ) {
        taskCategories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onTaskCategorySelected(category) }
            ) {
                ChoiceChipContent(
                    text = category.name,
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
            selected -> Color.White /*MaterialTheme.colors.secondary.copy(alpha = 0.87f)*/
            else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> Color.Black.copy(alpha = 0.5f)
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