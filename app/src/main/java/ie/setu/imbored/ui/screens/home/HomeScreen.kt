package ie.setu.imbored.ui.screens.home

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ie.setu.imbored.navigation.Login
import ie.setu.imbored.navigation.NavHostProvider
import ie.setu.imbored.navigation.Report
import ie.setu.imbored.navigation.allDestinations
import ie.setu.imbored.navigation.bottomAppBarDestinations
import ie.setu.imbored.navigation.userSignedOutDestinations
import ie.setu.imbored.ui.components.general.BottomAppBarProvider
import ie.setu.imbored.ui.components.general.TopAppBarProvider
import ie.setu.imbored.ui.components.report.SearchBar
import ie.setu.imbored.ui.screens.map.MapViewModel
import ie.setu.imbored.ui.theme.ImBoredJPCTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: Login
    var startScreen = currentBottomScreen

    val currentUser = homeViewModel.currentUser
    val isActiveSession = homeViewModel.isAuthenticated()
    val userEmail = if (isActiveSession) currentUser?.email ?: "Unknown Email" else ""
    val userName = if (isActiveSession) currentUser?.displayName ?: "User" else ""

    val searchQuery = remember { mutableStateOf("") }

    val currentSortField = remember { mutableStateOf("Date Modified") }
    val isAscending = remember { mutableStateOf(true) }

    val userDestinations = if (!isActiveSession)
        userSignedOutDestinations
    else bottomAppBarDestinations

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    if (isActiveSession) {
        startScreen = Report
        LaunchedEffect(locationPermissions.allPermissionsGranted) {
            locationPermissions.launchMultiplePermissionRequest()
            if (locationPermissions.allPermissionsGranted) {
                mapViewModel.getLocationUpdates()
            }
        }
    }

    val isShowAllActivities = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                navController = navController,
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                name = userName,
                email = userEmail,
                isShowAllActivities = isShowAllActivities,
                currentSortField = currentSortField,
                isAscending = isAscending,
                onToggleChange = { isChecked -> isShowAllActivities.value = isChecked },
                onSortChange = { field, ascending ->
                    currentSortField.value = field
                    isAscending.value = ascending
                },
                navigateUp = { navController.navigateUp() }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                // Display search bar only in the Report screen
                if (currentBottomScreen == Report) {
                    SearchBar(
                        query = searchQuery.value,
                        onQueryChange = { searchQuery.value = it },
                        onClearQuery = { searchQuery.value = "" }
                    )
                }

                NavHostProvider(
                    modifier = modifier,
                    navController = navController,
                    startDestination = startScreen.route,
                    paddingValues = PaddingValues(0.dp),
                    isShowAllActivities = isShowAllActivities,
                    searchQuery = searchQuery.value,
                    currentSortField = currentSortField.value,
                    isAscending = isAscending.value
                )
            }
        },
        bottomBar = {
            BottomAppBarProvider(
                navController = navController,
                currentScreen = currentBottomScreen,
                userDestinations = userDestinations
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    ImBoredJPCTheme  {
        HomeScreen(modifier = Modifier)
    }
}