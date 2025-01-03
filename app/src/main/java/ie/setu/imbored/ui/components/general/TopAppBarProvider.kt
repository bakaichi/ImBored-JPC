package ie.setu.imbored.ui.components.general

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ie.setu.imbored.navigation.AppDestination
import ie.setu.imbored.navigation.Contribute
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProvider(
    navController: NavController,
    currentScreen: AppDestination,
    canNavigateBack: Boolean,
    email: String,
    name: String,
    isShowAllActivities: MutableState<Boolean>,
    currentSortField: MutableState<String>,
    isAscending: MutableState<Boolean>,
    onToggleChange: (Boolean) -> Unit,
    onSortChange: (String, Boolean) -> Unit,
    navigateUp: () -> Unit = {}
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Column {
                Text(
                    text = currentScreen.label,
                    color = Color.White
                )
                Row {
                    if (name.isNotEmpty())
                        Text(
                            text = name,
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    if (email.isNotEmpty())
                        Text(
                            text = " ($email)",
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            } else {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            // Toggle Button
            ToggleButton(
                isChecked = isShowAllActivities,
                onCheckedChange = onToggleChange
            )

            // Sorting
            IconButton(onClick = { isMenuExpanded = true }) {
                Icon(
                    imageVector = if (isAscending.value) Icons.Default.ArrowUpward
                    else Icons.Default.ArrowDownward,
                    contentDescription = "Sort Order",
                    tint = Color.White
                )
            }

            // Dropdown Menu for Sorting
            androidx.compose.material3.DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false }
            ) {
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text("Sort by Date Added") },
                    onClick = {
                        currentSortField.value = "Date Added"
                        onSortChange(currentSortField.value, isAscending.value)
                        isMenuExpanded = false
                    }
                )
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text("Sort by Date Modified") },
                    onClick = {
                        currentSortField.value = "Date Modified"
                        onSortChange(currentSortField.value, isAscending.value)
                        isMenuExpanded = false
                    }
                )
                androidx.compose.material3.DropdownMenuItem(
                    text = {
                        Text(
                            if (isAscending.value) "Descending Order" else "Ascending Order"
                        )
                    },
                    onClick = {
                        isAscending.value = !isAscending.value
                        onSortChange(currentSortField.value, isAscending.value)
                        isMenuExpanded = false
                    }
                )
            }
        }
    )
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    val isShowAllActivities = mutableStateOf(false)
    val currentSortField = mutableStateOf("Date Added")
    val isAscending = mutableStateOf(true)

    ImBoredJPCTheme {
        TopAppBarProvider(
            navController = rememberNavController(),
            currentScreen = Contribute,
            canNavigateBack = true,
            email = "example@imbored.com",
            name = "Test User",
            isShowAllActivities = isShowAllActivities,
            currentSortField = currentSortField,
            isAscending = isAscending,
            onToggleChange = { isChecked -> isShowAllActivities.value = isChecked },
            onSortChange = { field, ascending ->
                currentSortField.value = field
                isAscending.value = ascending
            }
        )
    }
}
