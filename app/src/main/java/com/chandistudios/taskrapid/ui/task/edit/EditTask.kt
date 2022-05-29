package com.chandistudios.taskrapid.ui.task.edit

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.ui.task.*
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTask(
    onBackPress: () -> Unit,
    viewModel: EditTaskViewModel = viewModel(),
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val showDeleteDialog = remember { mutableStateOf(false) }

    Surface {
        val context = LocalContext.current

        var taskId: MutableState<Long> = rememberSaveable { mutableStateOf(0) }
        var name: MutableState<String> = rememberSaveable { mutableStateOf("") }
        var description: MutableState<String> = rememberSaveable { mutableStateOf("") }
        var icon: MutableState<Long> = rememberSaveable { mutableStateOf(0) }
        var date: MutableState<String> = rememberSaveable { mutableStateOf("") }
        var time: MutableState<String> = rememberSaveable { mutableStateOf("") }
        var locationX: MutableState<String> = rememberSaveable { mutableStateOf("") }
        var locationY: MutableState<String> = rememberSaveable { mutableStateOf("") }
        var taskType: MutableState<String> = rememberSaveable { mutableStateOf("") }

        val selectedTask: Task? = viewState.selectedTask
        if (selectedTask != null) {
            taskId = rememberSaveable { mutableStateOf(selectedTask.taskId) }
            name = rememberSaveable { mutableStateOf(selectedTask.taskName) }
            description = rememberSaveable { mutableStateOf(selectedTask.taskDescription.toString()) }
            icon = rememberSaveable { mutableStateOf<Long>(selectedTask.taskIcon) }
            date = rememberSaveable { mutableStateOf(selectedTask.taskDate.toString()) }
            time = rememberSaveable { mutableStateOf(selectedTask.taskTime.toString()) }
            locationX = rememberSaveable { mutableStateOf(selectedTask.locationX.toString()) }
            locationY = rememberSaveable { mutableStateOf(selectedTask.locationY.toString()) }
            viewState.taskTypes.forEach { type ->
                if (type.id == selectedTask.taskTypeId)
                    taskType = rememberSaveable { mutableStateOf(type.name) }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = { onBackPress() },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Edit Task: " + name.value)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { data -> name.value = data },
                    label = { Text(text = "Task Name")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { data -> description.value = data },
                    label = { Text(text = "Task Description")},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Select Task Icon: ",
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconDropdown(
                        taskIcons = viewState.taskIcons,
                        icon = icon
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                DatePicker(
                    context = context,
                    date = date
                )
                TimePicker(
                    context = context,
                    time = time
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Location: ",
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = { /*TODO (HW4)*/ }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Select location...")
                    }
                }
//                Text(
//                    text = "Lat: " + locationX.value + ", Long: " + locationY.value,
//                    maxLines = 1,
//                    style = MaterialTheme.typography.body1,
//                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Task Importance: ",
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TypeDropdown(
                        taskTypes = viewState.taskTypes,
                        type = taskType
                    )
                }
                /*TODO (HW3): Add reminder notification options*/
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (name.value != "") {
                            coroutineScope.launch {
                                viewModel.updateTask(
                                    Task(
                                        taskId = taskId.value,
                                        taskName = name.value,
                                        taskDescription = description.value,
                                        taskIcon = icon.value,
                                        taskDate = date.value,
                                        taskTime = time.value,
                                        locationX = locationX.value,
                                        locationY = locationY.value,
                                        taskTypeId = getTaskTypeId(
                                            viewState.taskTypes,
                                            taskType.value
                                        )
                                    )
                                )
                            }
                            Toast.makeText(context,
                                "Task '${name.value}' has been Updated!",
                                Toast.LENGTH_SHORT).show()
                            onBackPress()
                        } else {
                            Toast.makeText(context,"Task Name CANNOT be empty!", Toast.LENGTH_LONG).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Delete Task"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Changes")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { showDeleteDialog.value = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Task"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Delete Task")
                }

                DeleteDialog(
                    viewModel = viewModel,
                    task = selectedTask,
                    showDeleteDialog = showDeleteDialog,
                    onBackPress = onBackPress
                )
            }
        }
    }
}

private fun getTaskTypeId(types: List<TaskType>, taskType: String): Long {
    return types.first { type -> type.name == taskType }.id
}

@Composable
fun DeleteDialog(
    viewModel: EditTaskViewModel,
    task: Task?,
    showDeleteDialog: MutableState<Boolean>,
    onBackPress: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text(text = "Delete '${task?.taskName}'") },
            text = { Text(text = "Are you sure you want to delete this task?") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (task != null) {
                                viewModel.deleteTask(task)
                            }
                        }
                        showDeleteDialog.value = false
                        Toast.makeText(Graph.appContext,
                            "Task '${task?.taskName}' has been Deleted!", Toast.LENGTH_SHORT).show()
                        onBackPress()
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
}