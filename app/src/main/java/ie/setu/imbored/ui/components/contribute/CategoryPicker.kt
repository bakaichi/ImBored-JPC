package ie.setu.imbored.ui.components.contribute

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun CategoryPicker(
    modifier: Modifier = Modifier,
    onCategoryChange: (String) -> Unit
) {
    val possibleCategories = listOf("Social", "Sports", "Cultural", "Educational")
    var selectedCategory by remember { mutableStateOf(possibleCategories[0]) }

    ListItemPicker(
        label = { it },
        dividersColor = MaterialTheme.colorScheme.primary,
        textStyle = TextStyle(Color.Black, 20.sp),
        value = selectedCategory,
        onValueChange = {
            selectedCategory = it
            onCategoryChange(selectedCategory)
        },
        list = possibleCategories
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryPickerPreview() {
    ImBoredJPCTheme {
        CategoryPicker(onCategoryChange = {})
    }
}
