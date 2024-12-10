package ie.setu.imbored.ui.components.report

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.models.fakeActivities
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun ActivityCardList(
    activities: List<ActivityModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(horizontal = 4.dp)) {
        items(
            items = activities,
            key = { activity -> activity.id }
        ) { activity ->
            ActivityCard(activity = activity)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityCardListPreview() {
    ImBoredJPCTheme {
        ActivityCardList(fakeActivities.toMutableStateList())
    }
}
