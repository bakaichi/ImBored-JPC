package ie.setu.imbored.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.imbored.R
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.models.fakeActivities
import ie.setu.imbored.ui.components.general.Centre
import ie.setu.imbored.ui.components.report.ActivityCardList
import ie.setu.imbored.ui.components.report.ReportText
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun ScreenReport(
    modifier: Modifier = Modifier,
    activities: SnapshotStateList<ActivityModel>
) {
    if (activities.isEmpty()) {
        Centre(Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.empty_list),
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            modifier = modifier.padding(top = 80.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            ReportText()
            ActivityCardList(activities = activities)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    ImBoredJPCTheme {
        ScreenReport(
            modifier = Modifier,
            activities = fakeActivities.toMutableStateList()
        )
    }
}
