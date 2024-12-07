package ie.setu.imbored.ui.components.contribute

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun TitleInput(
    modifier: Modifier = Modifier,
    onTitleChange: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }

    OutlinedTextField(
        value = title,
        onValueChange = {
            title = it
            onTitleChange(title)
        },
        label = { Text("Enter Title") },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TitleInputPreview() {
    ImBoredJPCTheme {
        TitleInput(onTitleChange = {})
    }
}
