package ie.setu.imbored.ui.components.general

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.imbored.navigation.AppDestination
import ie.setu.imbored.navigation.bottomAppBarDestinations
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun BottomAppBarProvider(
    navController: NavHostController,
    currentScreen: AppDestination,
    userDestinations: List<AppDestination>
) {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ) {
        userDestinations.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = navigationItem == currentScreen,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = White,
                    unselectedIconColor = White,
                    unselectedTextColor = Black
                ),
                label = { Text(text = navigationItem.label) },
                icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label) },
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarScreenPreview() {
    ImBoredJPCTheme {
        BottomAppBarProvider(
            navController = rememberNavController(),
            currentScreen = bottomAppBarDestinations.get(1),
            userDestinations = bottomAppBarDestinations
        )
    }
}
