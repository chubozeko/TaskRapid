package com.chandistudios.taskrapid.ui.task

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.chandistudios.taskrapid.data.entity.NotiTime
import com.chandistudios.taskrapid.data.entity.TaskIcon
import com.chandistudios.taskrapid.data.entity.TaskType

@Composable
fun TypeDropdown(
    taskTypes: List<TaskType>,
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
            taskTypes.forEach { dropDownOption ->
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

@Composable
fun IconDropdown(
    taskIcons: List<TaskIcon>,
    icon: MutableState<Long>
) {
    var expanded by remember { mutableStateOf(false) }
    val ddIcon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }
    var currentIcon: ImageVector = Icons.Filled.Task
    taskIcons.forEach { ic ->
        if (ic.iconId == icon.value)
            currentIcon = ic.icon
    }
    Column {
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = currentIcon,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            taskIcons.forEach { dropDownOption ->
                DropdownMenuItem(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        icon.value = dropDownOption.iconId
                        expanded = false    // close list after selecting item
                    }) {
                        Icon(
                            imageVector = dropDownOption.icon,
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = dropDownOption.iconName)
                }
            }
        }

    }
}

@Composable
fun NotificationDropdown(
    notiTimes: List<NotiTime>,
    notiTimeValue: MutableState<Long>,
//    taskNotiTime: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val ddIcon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }
    var currentTime = "Select notification time..."
    notiTimes.forEach { nt ->
        if (nt.time == notiTimeValue.value)
            currentTime = nt.name
    }
    Column {
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = currentTime)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            notiTimes.forEach { dropDownOption ->
                DropdownMenuItem(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        notiTimeValue.value = dropDownOption.time
                        expanded = false    // close list after selecting item
                    }) {
                    Text(text = dropDownOption.name)
                }
            }
        }

    }
}