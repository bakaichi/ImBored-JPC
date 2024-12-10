package ie.setu.imbored.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.imbored.ui.screens.contribute.ContributeScreen
import ie.setu.imbored.ui.screens.report.ReportScreen

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Report.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Contribute.route) {
            ContributeScreen(modifier = modifier)
        }
        composable(route = Report.route) {
            ReportScreen(modifier = modifier)
        }
    }
}
