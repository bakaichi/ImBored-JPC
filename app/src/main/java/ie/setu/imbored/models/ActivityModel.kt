package ie.setu.imbored.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import java.text.DateFormat
import java.util.Date

@Entity(tableName = "activity_table")
data class ActivityModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @DocumentId val _id: String = "N/A",
    var imageUri: String = "",
    var title: String = "",
    var description: String = "",
    var category: String = "",
    var dateTime: String? = null,
    var capacity: Int = 0,
    var contributionAmount: Int = 0,
    val dateContributed: Date = Date(),
    var email: String = "aidas@mail.com",
    val dateModified: Date = Date()
)

val fakeActivities = List(30) { i ->
    ActivityModel(
        id = 12345 + i,
        title = "Activity $i",
        description = "Description of Activity $i. This is an interesting activity to participate in.",
        category = if (i % 2 == 0) "Social" else "Sports",
        dateTime = DateFormat.getDateTimeInstance().format(Date())
    )
}