package com.chandistudios.taskrapid.ui.task.add

import android.os.Build
import android.text.format.Time
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.ui.task.*
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTask(
    onBackPress: () -> Unit,
    navController: NavController,
    viewModel: AddTaskViewModel = viewModel(),
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Surface {
        val name = rememberSaveable { mutableStateOf("") }
        val description = rememberSaveable { mutableStateOf("") }
        val icon = rememberSaveable { mutableStateOf<Long>(0) }
        val date = rememberSaveable { mutableStateOf("") }
        val time = rememberSaveable { mutableStateOf("") }
        val taskType = rememberSaveable { mutableStateOf("") }

        val locationX = rememberSaveable { mutableStateOf("") }
        val locationY = rememberSaveable { mutableStateOf("") }
        val taskType = rememberSaveable { mutableStateOf("") }
        val addNotiTime = rememberSaveable { mutableStateOf(false) }
        val notiTime = rememberSaveable { mutableStateOf("") }
        val notiTimeValue = rememberSaveable { mutableStateOf<Long>(0) }

        val context = LocalContext.current

        val latlng = navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<LatLng>("location_data")
            ?.value
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Task Location: ",
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                    OutlinedTextField(
//                        value = description.value,
//                        onValueChange = { data -> description.value = data },
//                        label = { Text(text = "Task Description")},
//                        singleLine = false,
//                        maxLines = 2
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
                    if (latlng == null) {
                        OutlinedButton(
                            onClick = { navController.navigate("map") },
                            modifier = Modifier.height(55.dp))
                        {
                            Text(text = "Select location...")
                        }
                    } else {
                        Text(text = "Lat: ${latlng.latitude}; \nLong: ${latlng.longitude}")
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
                Spacer(modifier = Modifier.height(12.dp))
                /*TODO (HW3): Add reminder notification options*/
                if (time.value != "") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = addNotiTime.value,
                            onCheckedChange = { data -> addNotiTime.value = data }
                        )
                        Text(text = "Add reminder notification")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (addNotiTime.value) {
                    NotificationDropdown(
                        notiTimes = viewState.notiTimes,
                        notiTimeValue = notiTimeValue
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (name.value != "" && taskType.value != "") {
                            Log.i("AddTask",
                                "Notification time: " + parseNotificationTime(time.value, notiTimeValue.value) +
                            " ; Task Time: " + time.value.toTime().time.toTimeString() +
                            " ; timezone offset: " + getGMTOffset())

                            coroutineScope.launch {
                                viewModel.saveTask(
                                    Task(
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
                                        ),
                                        taskNoti = when(addNotiTime.value) {
                                            true -> 1
                                            else -> 0
                                        },
                                        notificationTime = parseNotificationTime(time.value, notiTimeValue.value),
                                        notiTimeValue = notiTimeValue.value
                                    )
                                )
                            }
                            Toast.makeText(context,"Task '${name.value}' has been Added!", Toast.LENGTH_SHORT).show()
                            onBackPress()
                        } else {
                            Toast.makeText(context,"Task Name and Type CANNOT be empty!", Toast.LENGTH_LONG).show()
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

