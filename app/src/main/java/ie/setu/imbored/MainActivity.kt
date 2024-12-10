package ie.setu.imbored

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.imbored.navigation.NavHostProvider
import ie.setu.imbored.navigation.Report
import ie.setu.imbored.navigation.allDestinations
import ie.setu.imbored.ui.components.general.BottomAppBarProvider
import ie.setu.imbored.ui.components.general.TopAppBarProvider
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        Timber.i("ImBored MainActivity started..")
        setContent {
            ImBoredJPCTheme {
                ImBoredApp()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImBoredApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen = allDestinations.find { it.route == currentDestination?.route } ?: Report

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null
            ) {
                navController.navigateUp()
            }},
        content = { paddingValues ->
            NavHostProvider(
                modifier = modifier,
                navController = navController,
                paddingValues = paddingValues
            )
        },
        bottomBar = {
            BottomAppBarProvider(
                navController = navController,
                currentScreen = currentBottomScreen
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ImBoredAppPreview() {
    ImBoredJPCTheme {
        ImBoredApp(modifier = Modifier)
    }
}
