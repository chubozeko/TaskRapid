package com.chandistudios.taskrapid.ui.task.add

import android.widget.DatePicker
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chandistudios.taskrapid.TaskRapidAppState
import com.chandistudios.taskrapid.data.entity.Task
import com.chandistudios.taskrapid.data.entity.TaskType
import com.chandistudios.taskrapid.ui.login.Login
import com.google.accompanist.insets.systemBarsPadding
import java.util.*
import kotlin.random.Random

@Composable
fun AddTask(
    onBackPress: () -> Unit
) {
    Surface {
        val name = rememberSaveable { mutableStateOf("") }
        val description = rememberSaveable { mutableStateOf("") }
        val icon = rememberSaveable { mutableStateOf("") }
        val date = rememberSaveable { mutableStateOf("") }
        val time = rememberSaveable { mutableStateOf("") }
        val locationX = rememberSaveable { mutableStateOf("") }
        val locationY = rememberSaveable { mutableStateOf("") }
        val taskType = rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
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
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(
                        text = "Select Task Icon: ",
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
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
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(
                        text = "Task Stage: ",
                        maxLines = 1,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    /*TODO: Use a dropdown menu for Task Type [HW2]*/
                    OutlinedTextField(
                        value = taskType.value,
                        onValueChange = { data -> taskType.value = data },
                        placeholder = { Text(text = "Upcoming/Complete")},
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                }
                /*TODO: Add calendar event options [HW2]*/
                /*TODO: Add reminder notification options [HW3]*/
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onBackPress, //{ /*TODO: HW2*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Save Task")
                }

//                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

