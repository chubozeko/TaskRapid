package com.chandistudios.taskrapid.ui.task.add

import android.widget.DatePicker
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chandistudios.taskrapid.TaskRapidAppState
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.rememberTaskRapidAppState
import com.chandistudios.taskrapid.ui.home.HomeViewModel
import com.chandistudios.taskrapid.ui.login.Login
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

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
        val icon = rememberSaveable { mutableStateOf("") }
        val date = rememberSaveable { mutableStateOf("") }
        val time = rememberSaveable { mutableStateOf("") }
        val taskType = rememberSaveable { mutableStateOf("") }

        val locationX = rememberSaveable { mutableStateOf("") }
        val locationY = rememberSaveable { mutableStateOf("") }
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
                    // TODO (HW2): Add Icon selection menu
//                    IconDropdown(
//                        viewState = viewState,
//                        icon = icon
//                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Icon")
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Default")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(8.dp)) {
                        Text(text = "Task Date")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(8.dp)) {
                        Text(text = "Task Time")
                    }
                }
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
                        viewState = viewState,
                        type = taskType
                    )
                }
                /*TODO (HW2): Add calendar event options*/
                /*TODO (HW3): Add reminder notification options*/
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveTask(
                                Task(
                                    taskName = name.value,
                                    taskDescription = description.value,
                                    taskIcon = 0,
                                    taskDate = Date().time, // date.value
                                    taskTime = Date().time.toString(),
                                    locationX = locationX.value,
                                    locationY = locationY.value,
                                    taskTypeId = getTaskTypeId(viewState.taskTypes, taskType.value)
                                )
                            )
                        }
                        onBackPress()
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

@Composable
private fun TypeDropdown(
    viewState: AddTaskViewState,
    type: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val ddIcon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column() {
        OutlinedTextField(
            value = type.value,
            onValueChange = { type.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Task Type") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = ddIcon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            viewState.taskTypes.forEach { dropDownOption ->
                DropdownMenuItem(onClick = {
                    type.value = dropDownOption.name
                    expanded = false    // close list after selecting item
                }) {
                    Text(text = dropDownOption.name)
                }
            }
        }
    }
}

//@Composable
//private fun IconDropdown(
//    viewState: AddTaskViewState,
//    icon: MutableState<ImageVector>
//) {
//    var expanded by remember { mutableStateOf(false) }
//    val ddIcon = if (expanded) {
//        Icons.Filled.ArrowDropUp // requires androidx.compose.material:material-icons-extended dependency
//    } else {
//        Icons.Filled.ArrowDropDown
//    }
//    Column {
//        Icon(
//            imageVector = icon.value,
//            contentDescription = null,
////            modifier = Modifier.fillMaxWidth(),
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            viewState.iconName.forEach { dropDownOption ->
//                DropdownMenuItem(onClick = {
//                    icon.value = dropDownOption.iconName
//                    expanded = false    // close list after selecting item
//                }) {
//                    Text(text = dropDownOption.iconName)
//                }
//            }
//        }
//
//    }
//}
