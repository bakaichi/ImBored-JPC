package ie.setu.imbored.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.models.fakeActivities
import ie.setu.imbored.ui.components.report.ActivityCardList
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.imbored.ui.components.general.ShowError
import ie.setu.imbored.ui.components.general.ShowLoader

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    onClickDetails: (String) -> Unit,
    reportViewModel: ReportViewModel = hiltViewModel(),
    isShowAllActivities: MutableState<Boolean>
) {
    val activities = reportViewModel.uiActivities.collectAsState().value
    val isError = reportViewModel.iserror.value
    val error = reportViewModel.error.value
    val isLoading = reportViewModel.isloading.value

    // Filter activities based on query (search)
    val filteredActivities = activities.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(isShowAllActivities.value) {
        if (isShowAllActivities.value) {
            reportViewModel.getAllActivities()
        } else {
            reportViewModel.getActivities()
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        when {
            isLoading -> {
                ShowLoader("Loading Activities...")
            }
            isError -> {
                ShowError(
                    headline = "Error loading activities",
                    subtitle = error?.message ?: "Unknown error",
                    onClick = { reportViewModel.getActivities() }
                )
            }
            filteredActivities.isEmpty() -> {
                Text(
                    text = "No activities found.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            else -> {
                ActivityCardList(
                    activities = filteredActivities,
                    onClickDetails = onClickDetails,
                    onDeleteActivity = { activity ->
                        reportViewModel.deleteActivity(activity)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    ImBoredJPCTheme {
        PreviewReportScreen(
            modifier = Modifier,
            activities = fakeActivities
        )
    }
}

@Composable
fun PreviewReportScreen(
    modifier: Modifier = Modifier,
    activities: List<ActivityModel>
) {
    Column(modifier = modifier.padding(start = 24.dp, end = 24.dp)) {
        if (activities.isEmpty()) {
            Text(
                text = "No activities to display",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            ActivityCardList(
                activities = activities,
                onDeleteActivity = {},
                onClickDetails = {}
            )
        }
    }
}