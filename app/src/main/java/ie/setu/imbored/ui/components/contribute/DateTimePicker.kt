package ie.setu.imbored.ui.components.contribute

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.view.View
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import java.util.Locale

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    onDateTimeChange: (String) -> Unit
) {
    val context = LocalContext.current
    var dateTime by remember { mutableStateOf("Select Date/Time") } //Button name
    val calendar = Calendar.getInstance()
    val dialogColor = MaterialTheme.colorScheme.primary.toArgb()

    // Show each picker
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Show Date Picker
    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                calendar.set(year, month, day)
                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                dateTime = formattedDate
                showDatePicker = false
                showTimePicker = true // Show the Time picker after selecting the date
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // set the header color
            setOnShowListener {
                val headerViewId = context.resources.getIdentifier("date_picker_header", "id", "android")
                val headerView = window?.decorView?.findViewById<View>(headerViewId)
                headerView?.setBackgroundColor(dialogColor)
            }
        }.show()
    }

    // Show Time Picker
    if (showTimePicker) {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
                dateTime = "$dateTime $formattedTime"
                showTimePicker = false
                onDateTimeChange(dateTime) // send the result back
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).apply {
            // header color
            setOnShowListener {
                val headerViewId = context.resources.getIdentifier("time_header", "id", "android")
                val headerView = window?.decorView?.findViewById<View>(headerViewId)
                headerView?.setBackgroundColor(dialogColor)
            }
        }.show()
    }

    // Button to Trigger Date/Time Picker
    Button(
        onClick = { showDatePicker = true },
        modifier = modifier
    ) {
        Text(text = dateTime)
    }
}


@Preview(showBackground = true)
@Composable
fun DateTimePickerPreview() {
    ImBoredJPCTheme {
        DateTimePicker(onDateTimeChange = {})
    }
}
