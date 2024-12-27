package ie.setu.imbored.ui.components.report

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import showDeleteAlert
import java.text.DateFormat
import java.util.*

@Composable
fun ActivityCard(
    activity: ActivityModel,
    onClickDelete: () -> Unit,
    onClickDetails: () -> Unit,
    photoUri: Uri
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .fillMaxWidth()
    ) {
        ActivityCardContent(
            activity = activity,
            onClickDelete = onClickDelete,
            onClickDetails = onClickDetails,
            photoUri = photoUri
        )
    }
}

@Composable
private fun ActivityCardContent(
    activity: ActivityModel,
    onClickDelete: () -> Unit,
    onClickDetails: () -> Unit,
    photoUri: Uri
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            ActivityCardHeader(
                activity = activity,
                expanded = expanded,
                onExpandToggle = { expanded = !expanded }
            )
            if (expanded) {
                ActivityCardExpandedContent(
                    activity = activity,
                    onClickDelete = { showDeleteConfirmDialog = true },
                    onClickDetails = onClickDetails
                )
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
    }

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

@Composable
private fun ActivityCardHeader(
    activity: ActivityModel,
    expanded: Boolean,
    onExpandToggle: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Event,
            contentDescription = "Activity Icon",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = activity.title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
            )
            Text(
                text = "Scheduled on: ${activity.dateTime}",
                style = MaterialTheme.typography.labelMedium

            )
        }
        IconButton(onClick = onExpandToggle) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Show less" else "Show more"
            )
        }
    }
}

@Composable
private fun ActivityCardExpandedContent(
    activity: ActivityModel,
    onClickDelete: () -> Unit,
    onClickDetails: () -> Unit
) {
    Text(
        text = "Category: ${activity.category}",
        style = MaterialTheme.typography.labelMedium
    )
    Text(
        text = "Capacity: ${activity.capacity}",
        style = MaterialTheme.typography.labelMedium
    )
    Text(
        text = "Description: ${activity.description}",
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FilledTonalButton(onClick = onClickDetails) {
            Text(text = "Details")
        }
        FilledTonalIconButton(onClick = onClickDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete Activity")
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
                capacity = 20,
                dateTime = DateFormat.getDateTimeInstance().format(Date())
            ),
            onClickDelete = {},
            onClickDetails = {},
            photoUri = Uri.EMPTY
        )
    }
}
