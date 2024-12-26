package ie.setu.imbored.ui.screens.details

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.imbored.ui.components.details.DetailsScreenText
import ie.setu.imbored.ui.components.details.ReadOnlyTextField
import ie.setu.imbored.ui.components.general.ShowLoader

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    activityId : String,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val activity = detailsViewModel.activity.value
    val errorEmptyMessage = "Description Cannot be Empty..."
    val errorShortMessage = "Description must be at least 2 characters"
    var description by rememberSaveable { mutableStateOf("") }
    var onDescriptionChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val isError = detailsViewModel.isErr.value
    val isLoading = detailsViewModel.isLoading.value

    if (isLoading) ShowLoader("Retrieving Activity Details...")

    fun validate(text: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2
        onDescriptionChanged = !(isEmptyError || isShortError)
    }

    if (isError) {
        Toast.makeText(context, "Unable to fetch Details at this Time...",
            Toast.LENGTH_SHORT).show()
    } else if (!isLoading) {
        Column(
            modifier = modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            DetailsScreenText() // Custom Text Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp)
            ) {
                // Read-only Fields
                ReadOnlyTextField(value = activity.title, label = "Title")
                ReadOnlyTextField(value = activity.category, label = "Category")
                ReadOnlyTextField(value = activity.dateTime ?: "No Date/Time", label = "Scheduled Date/Time")
                ReadOnlyTextField(value = activity.capacity.toString(), label = "Capacity")

                // Editable Description Field
                description = activity.description
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = {
                        description = it
                        validate(description)
                        activity.description = description
                    },
                    maxLines = 2,
                    label = { Text(text = "Description") },
                    isError = isEmptyError || isShortError,
                    supportingText = {
                        if (isEmptyError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorEmptyMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        } else if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    trailingIcon = {
                        if (isEmptyError || isShortError)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
                        else
                            Icon(Icons.Default.Edit, contentDescription = "edit", tint = Color.Black)
                    },
                    keyboardActions = KeyboardActions { validate(description) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Save Button
                Button(
                    onClick = {
                        detailsViewModel.updateDescription(activity)
                        onDescriptionChanged = false
                    },
                    elevation = ButtonDefaults.buttonElevation(20.dp),
                    enabled = onDescriptionChanged
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

