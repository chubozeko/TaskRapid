package com.chandistudios.taskrapid.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
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

            date.value = "$dd/$mm/$year"
        }, year, month, day
    )
    // Only allow date selection from current day
    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

    // Load date if it has a value
    if (date.value != "") {
        datePickerDialog.datePicker.updateDate(date.value.toDate().year, date.value.toDate().month, date.value.toDate().date)
    }

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

@RequiresApi(Build.VERSION_CODES.O)
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
            var hh = ""
            var mm = ""
            if (hour < 10) hh = "0$hour" else hh = "$hour"
            if (min < 10) mm = "0$min" else mm = "$min"

            time.value = "$hh:$mm:00"
        }, hour, minute, DateFormat.is24HourFormat(context)
    )

    // Load time if it has a value
    if (time.value != "") {
        timePickerDialog.updateTime(time.value.toTime().hours, time.value.toTime().minutes)
    }

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

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): Date {
    var formattedDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    return Date(formattedDate.year, formattedDate.monthValue, formattedDate.dayOfMonth)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toTime(): Time {
    var formattedTime = LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm:ss"))
    return Time(formattedTime.hour, formattedTime.minute, formattedTime.second)
}

fun Date.formatToString(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)
}

fun Long.toDateString(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(this))
}

fun Time.formatTimeToString(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(this)
}

fun Long.toTimeString(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Time(this))
}