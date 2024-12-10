import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ie.setu.imbored.R

@Composable
fun showDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource( R.string.confirm_delete)) },
        text = { Text(text = stringResource( R.string.confirm_delete_message)) },
        confirmButton = {
            Button(onClick = onDelete) { Text(text = stringResource(id = R.string.yes)) }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text(text = stringResource(id = R.string.no)) }
        }
    )
}
