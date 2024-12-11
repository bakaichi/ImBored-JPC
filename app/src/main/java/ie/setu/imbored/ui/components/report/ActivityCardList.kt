package ie.setu.imbored.ui.components.report

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.models.fakeActivities
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun ActivityCardList(
    activities: List<ActivityModel>,
    onDeleteActivity: (ActivityModel) -> Unit,
    onClickDetails: (Int) -> Unit
) {
    LazyColumn {
        items(activities) { activity ->
            ActivityCard(
                activity = activity,
                onClickDelete = { onDeleteActivity(activity) },
                onClickDetails = { onClickDetails(activity.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityCardListPreview() {
    ImBoredJPCTheme {
        ActivityCardList(
            activities = fakeActivities,
            onDeleteActivity = {},
            onClickDetails = {}
        )
    }
}
