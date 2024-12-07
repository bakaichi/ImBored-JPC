package ie.setu.imbored.ui.components.contribute

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    onDateTimeChange: (String) -> Unit
) {
    var dateTime by remember { mutableStateOf("") }

    OutlinedTextField(
        value = dateTime,
        onValueChange = {
            dateTime = it
            onDateTimeChange(dateTime)
        },
        label = { Text("Select Date/Time") },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun DateTimePickerPreview() {
    ImBoredJPCTheme {
        DateTimePicker(onDateTimeChange = {})
    }
}
