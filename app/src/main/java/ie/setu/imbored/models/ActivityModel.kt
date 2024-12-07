package ie.setu.imbored.models

import java.text.DateFormat
import java.util.Date
import kotlin.random.Random

data class ActivityModel(val id: Int = Random.nextInt(1, 100000),
var title: String = "",
var description: String = "",
var category: String = "",
var dateTime: String? = null,
var capacity: Int = 0,
var contributionAmount: Int = 0,
val dateContributed: Date = Date()
)

val fakeActivities = List(30) { i ->
    ActivityModel(
        id = 12345 + i,
        title = "Activity $i",  // Replace with meaningful title
        description = "Description of Activity $i. This is an interesting activity to participate in.",
        category = if (i % 2 == 0) "Social" else "Sports",
        dateTime = DateFormat.getDateTimeInstance().format(Date())
    )
}