package com.chandistudios.taskrapid.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun DatePicker(
    context: Context,
    date: MutableState<String>
) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context, { d, year, month, day ->
            var dd = ""
            var mm = ""
            if (day < 10) dd = "0$day" else dd = "$day"
            val mon = month-1
            if (mon < 10) mm = "0$mon" else mm = "$mon"

            date.value = "$dd-$mm-$year"
//            dateFormat = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-mm-yyyy"))
        }, year, month, day
    )
    // Only allow date selection from current day
    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

    Column {
        OutlinedButton(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.padding(4.dp))
        {
            if (date.value == "")
                Text(text = "Task Date")
            else
                Text(text = date.value)
        }
    }

}

@Composable
fun TimePicker(
    context: Context,
    time: MutableState<String>
) {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)
    
    val timePickerDialog = TimePickerDialog(
        context, { d, hour, min ->
            time.value = "$hour:$min"
        }, hour, minute, DateFormat.is24HourFormat(context)
    )

    Column {
        OutlinedButton(
            onClick = { timePickerDialog.show() },
            modifier = Modifier.padding(4.dp))
        {
            if (time.value == "")
                Text(text = "Task Time")
            else
                Text(text = time.value)
        }
    }

}
