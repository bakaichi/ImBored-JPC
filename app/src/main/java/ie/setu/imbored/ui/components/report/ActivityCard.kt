package ie.setu.imbored.ui.components.report

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import showDeleteAlert
import java.text.DateFormat
import java.util.*

@Composable
fun ActivityCard(activity: ActivityModel, onClickDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Event,
                        contentDescription = "Activity Icon",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = activity.title,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                    )
                }
                Text(
                    text = "Category: ${activity.category}",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Capacity: ${activity.capacity}",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = "Scheduled on: ${activity.dateTime}",
                    style = MaterialTheme.typography.labelMedium
                )
                if (expanded) {
                    Text(
                        text = "Description: ${activity.description}",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    // Delete Button
                    FilledTonalIconButton(onClick = { showDeleteConfirmDialog = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Activity")
                    }
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Show less" else "Show more"
                )
            }
        }

        // Show Confirmation Dialog
        if (showDeleteConfirmDialog) {
            showDeleteAlert(
                onDismiss = { showDeleteConfirmDialog = false },
                onDelete = {
                    onClickDelete()
                    showDeleteConfirmDialog = false
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ActivityCardPreview() {
    ImBoredJPCTheme {
        ActivityCard(
            activity = ActivityModel(
                title = "Pub Crawl",
                description = "A community-organized pub crawl to meet new friends.",
                category = "Social",
                dateTime = DateFormat.getDateTimeInstance().format(Date())
            ),
            onClickDelete = {}
        )
    }
}
