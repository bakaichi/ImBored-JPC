package ie.setu.imbored.ui.components.general

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.imbored.navigation.AppDestination
import ie.setu.imbored.navigation.bottomAppBarDestinations
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@Composable
fun BottomAppBarProvider(
    navController: NavHostController,
    currentScreen: AppDestination
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        bottomAppBarDestinations.forEach { destination ->
            NavigationBarItem(
                selected = currentScreen.route == destination.route,
                label = { Text(destination.label) },
                icon = { Icon(destination.icon, contentDescription = destination.label) },
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarPreview() {
    ImBoredJPCTheme {
        BottomAppBarProvider(
            navController = rememberNavController(),
            currentScreen = bottomAppBarDestinations.first()
        )
    }
}
