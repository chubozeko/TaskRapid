package com.chandistudios.taskrapid.ui.task.add

import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chandistudios.taskrapid.Graph
import com.chandistudios.taskrapid.TaskRapidAppState
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.rememberTaskRapidAppState
import com.chandistudios.taskrapid.ui.home.HomeViewModel
import com.chandistudios.taskrapid.ui.task.DatePicker
import com.chandistudios.taskrapid.ui.task.TimePicker
import com.chandistudios.taskrapid.ui.task.TypeDropdown
import com.chandistudios.taskrapid.ui.task.IconDropdown
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

@Composable
fun AddTask(
    onBackPress: () -> Unit,
    viewModel: AddTaskViewModel = viewModel(),
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Surface {
        val name = rememberSaveable { mutableStateOf("") }
        val description = rememberSaveable { mutableStateOf("") }
        val icon = rememberSaveable { mutableStateOf<Long>(0) }
        val date = rememberSaveable { mutableStateOf("") }
//        var date by remember { mutableStateOf("") }
        val time = rememberSaveable { mutableStateOf("") }
//        var time by remember { mutableStateOf("") }
        val locationX = rememberSaveable { mutableStateOf("") }
        val locationY = rememberSaveable { mutableStateOf("") }
        val taskType = rememberSaveable { mutableStateOf("") }

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = {}, // onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Add New Task: " + name.value)
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
                    OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
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
                                viewModel.saveTask(
                                    Task(
                                        taskName = name.value,
                                        taskDescription = description.value,
                                        taskIcon = icon.value,
                                        taskDate = date.value, // Date().time.toString()
                                        taskTime = Date().time.toString(),
                                        locationX = locationX.value,
                                        locationY = locationY.value,
                                        taskTypeId = getTaskTypeId(
                                            viewState.taskTypes,
                                            taskType.value
                                        )
                                    )
                                )
                            }
                            onBackPress()
                        } else {
                            Toast.makeText(context,"Task Name CANNOT be empty!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Save Task")
                }
            }
        }
    }
}

private fun getTaskTypeId(types: List<TaskType>, taskType: String): Long {
    return types.first { type -> type.name == taskType }.id
}

