package ie.setu.imbored.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.ui.components.report.ActivityCardList
import ie.setu.imbored.ui.components.general.Centre
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import ie.setu.imbored.models.fakeActivities

@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    reportViewModel: ReportViewModel = hiltViewModel()
) {
    val activities = reportViewModel.uiActivities.collectAsState().value

    Column {
        if (activities.isEmpty()) {
            Text(
                text = "No activities to display",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            ActivityCardList(activities = activities)
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
    Column {
        if (activities.isEmpty()) {
            Text(
                text = "No activities to display",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            ActivityCardList(activities = activities)
        }
    }
}

