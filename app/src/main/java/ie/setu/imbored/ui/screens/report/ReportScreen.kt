package ie.setu.imbored.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import ie.setu.imbored.R
import ie.setu.imbored.ui.components.general.Centre
import ie.setu.imbored.ui.components.general.ShowError
import ie.setu.imbored.ui.components.general.ShowLoader
import ie.setu.imbored.ui.components.report.ReportText
import timber.log.Timber

@Composable
fun ReportScreen(modifier: Modifier = Modifier,
                 onClickDetails: (String) -> Unit,
                 reportViewModel: ReportViewModel = hiltViewModel()) {

    val activities = reportViewModel.uiActivities.collectAsState().value
    val isError = reportViewModel.iserror.value
    val error = reportViewModel.error.value
    val isLoading = reportViewModel.isloading.value

    Timber.i("RS : Activity List = $activities")

//    LaunchedEffect(Unit) {
//        reportViewModel.getActivities()
//    }

    Column {
        Column(
            modifier = modifier.padding(
                start = 15.dp,
                end = 15.dp
            ),
        ) {
            if(isLoading) ShowLoader("Loading Activities...")
            ReportText()
//            if(!isError)
//                onRefreshList(onClick = { reportViewModel.getActivities() })
            if (activities.isEmpty() && !isError)
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            if (!isError) {
                ActivityCardList(
                    activities = activities,
                    onClickDetails = onClickDetails,
                    onDeleteActivity = { activity: ActivityModel ->
                        reportViewModel.deleteActivity(activity)
                    }
                )
            }
            if (isError) {
                if (error != null) {
                    ShowError(
                        headline = "${error.message} error...",
                        subtitle = error.toString(),
                        onClick = { reportViewModel.getActivities() }
                    )
                }
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
