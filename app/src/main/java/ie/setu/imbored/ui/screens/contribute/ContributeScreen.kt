package ie.setu.imbored.ui.screens.contribute

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.models.fakeActivities
import ie.setu.imbored.ui.components.contribute.*
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@SuppressLint("AutoboxingStateCreation")
@Composable
fun ContributeScreen(
    modifier: Modifier = Modifier,
    contributeViewModel: ContributeViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Social") }
    var dateTime by remember { mutableStateOf("") }
    var capacity by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        WelcomeText()

        // Title Input
        Text(
            text = "Title",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TitleInput(
            modifier = Modifier.fillMaxWidth(),
            onTitleChange = { title = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description Input
        Text(
            text = "Description",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        DescriptionInput(
            modifier = Modifier.fillMaxWidth(),
            onDescriptionChange = { description = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Row for Category and Capacity Pickers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                CategoryPicker(
                    modifier = Modifier.fillMaxWidth(),
                    onCategoryChange = { selectedCategory = it }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Capacity",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                AmountPicker(
                    onCapacityAmountChange = { capacity = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Date & Time Picker
        Text(
            text = "Date & Time",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        DateTimePicker(
            modifier = Modifier.fillMaxWidth(),
            onDateTimeChange = { dateTime = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contribute Button
        ContributeButton(
            modifier = Modifier.fillMaxWidth(),
            activity = ActivityModel(
                title = title,
                description = description,
                category = selectedCategory,
                dateTime = dateTime,
                capacity = capacity,
            ),
            onTotalContributedChange = { /* Handle Contribution Logic */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContributeScreenPreview() {
    ImBoredJPCTheme {
        ContributeScreen(
            modifier = Modifier
        )
    }
}