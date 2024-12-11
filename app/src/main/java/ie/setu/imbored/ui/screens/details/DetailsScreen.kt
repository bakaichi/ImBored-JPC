package ie.setu.imbored.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailsScreen(
    id: Int,
    modifier: Modifier = Modifier,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val activity = detailsViewModel.activity.value
    var description by rememberSaveable { mutableStateOf("") }

    // Update description when activity changes
    LaunchedEffect(activity.description) {
        description = activity.description ?: ""
    }

    // Validation states
    val errorEmptyDescription = "Description Cannot be Empty..."
    val errorShortDescription = "Description must be at least 2 characters"
    var onDescriptionChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    // Validation function
    fun validateDescription(text: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2
        onDescriptionChanged = !(isEmptyError || isShortError)
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Activity Details",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        TextField(
            value = activity.title,
            onValueChange = {},
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        // Description Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = {
                description = it
                validateDescription(description)
            },
            maxLines = 2,
            label = { Text(text = "Description") },
            isError = isEmptyError || isShortError,
            supportingText = {
                if (isEmptyError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorEmptyDescription,
                        color = MaterialTheme.colorScheme.error
                    )
                } else if (isShortError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorShortDescription,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (isEmptyError || isShortError) {
                    Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
                } else {
                    Icon(Icons.Default.Edit, contentDescription = "edit", tint = Color.Black)
                }
            },
            keyboardActions = KeyboardActions { validateDescription(description) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            )
        )

        TextField(
            value = activity.category,
            onValueChange = {},
            label = { Text("Category") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = activity.dateTime ?: "Not Specified",
            onValueChange = {},
            label = { Text("Scheduled Date/Time") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = activity.capacity.toString(),
            onValueChange = {},
            label = { Text("Capacity") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button
        Button(
            onClick = {
                detailsViewModel.updateDescription(description)
                onDescriptionChanged = false
            },
            elevation = ButtonDefaults.buttonElevation(20.dp),
            enabled = onDescriptionChanged,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Save, contentDescription = "Save")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Save")
        }
    }
}
