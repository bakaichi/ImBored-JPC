package ie.setu.imbored.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ie.setu.imbored.ui.components.contribute.AmountPicker
import ie.setu.imbored.ui.components.contribute.CategoryPicker
import ie.setu.imbored.ui.components.contribute.DateTimePicker
import ie.setu.imbored.ui.components.details.DetailsScreenText
import ie.setu.imbored.ui.components.details.ReadOnlyTextField
import ie.setu.imbored.ui.components.general.ShowLoader

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    val activity = detailsViewModel.activity.value

    var description by rememberSaveable { mutableStateOf(activity.description) }
    var category by rememberSaveable { mutableStateOf(activity.category) }
    var capacity by rememberSaveable { mutableStateOf(activity.capacity.toString()) }
    var dateTime by rememberSaveable { mutableStateOf(activity.dateTime ?: "") }

    var onDescriptionChanged by rememberSaveable { mutableStateOf(false) }
    var onCategoryChanged by rememberSaveable { mutableStateOf(false) }
    var onCapacityChanged by rememberSaveable { mutableStateOf(false) }
    var onDateTimeChanged by rememberSaveable { mutableStateOf(false) }

    var isDescriptionError by rememberSaveable { mutableStateOf(false) }
    val errorEmptyMessage = "Description Cannot be Empty..."
    val errorShortMessage = "Description must be at least 2 characters"

    fun validateDescription(text: String) {
        isDescriptionError = text.isEmpty() || text.length < 2
        onDescriptionChanged = !isDescriptionError
    }

    val context = LocalContext.current
    val isError = detailsViewModel.isErr.value
    val isLoading = detailsViewModel.isLoading.value

    if (isLoading) ShowLoader("Retrieving Activity Details...")

    if (isError) {
        Toast.makeText(context, "Unable to fetch Details at this Time...", Toast.LENGTH_SHORT).show()
    } else if (!isLoading) {
        Column(
            modifier = modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            DetailsScreenText()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp)
            ) {
                // Title
                ReadOnlyTextField(value = activity.title, label = "Title")

                Spacer(modifier = Modifier.height(16.dp))

                // Category and Capacity pickers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Category",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        CategoryPicker(
                            onCategoryChange = {
                                category = it
                                onCategoryChanged = true
                                activity.category = category
                            }
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Capacity",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        AmountPicker(
                            onCapacityAmountChange = {
                                capacity = it.toString()
                                onCapacityChanged = true
                                activity.capacity = it
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Date/Time editable
                Text(
                    text = "Scheduled Date/Time",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                DateTimePicker(
                    onDateTimeChange = {
                        dateTime = it
                        onDateTimeChanged = true
                        activity.dateTime = dateTime
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Editable Description Field
                description = activity.description
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = {
                        description = it
                        validateDescription(description)
                        activity.description = description
                    },
                    maxLines = 2,
                    label = { Text(text = "Description") },
                    isError = isDescriptionError,
                    supportingText = {
                        if (isDescriptionError) {
                            Text(
                                text = if (description.isEmpty()) errorEmptyMessage else errorShortMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Save Button
                Button(
                    onClick = {
                        detailsViewModel.updateDescription(activity)
                        navController.popBackStack()
                        onDescriptionChanged = false
                        onCategoryChanged = false
                        onCapacityChanged = false
                        onDateTimeChanged = false
                    },
                    enabled = onDescriptionChanged || onCategoryChanged || onCapacityChanged || onDateTimeChanged
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
