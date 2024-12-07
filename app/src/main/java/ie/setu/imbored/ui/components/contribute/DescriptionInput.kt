package ie.setu.imbored.ui.components.contribute

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun DescriptionInput(
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit // Updated parameter name
) {
    var description by remember { mutableStateOf("") }

    OutlinedTextField(
        value = description,
        onValueChange = {
            description = it
            onDescriptionChange(it)
        },
        label = { Text("Enter Description") },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun DescriptionInputPreview() {
    ImBoredJPCTheme {
        DescriptionInput(onDescriptionChange = {})
    }
}
