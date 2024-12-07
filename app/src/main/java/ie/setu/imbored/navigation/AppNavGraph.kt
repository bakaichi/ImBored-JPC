package ie.setu.imbored.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.ui.screens.ScreenContribute
import ie.setu.imbored.ui.screens.ScreenReport

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
    activities: SnapshotStateList<ActivityModel>
) {
    NavHost(
        navController = navController,
        startDestination = Report.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Contribute.route) {
            ScreenContribute(modifier = modifier, activities = activities)
        }
        composable(route = Report.route) {
            ScreenReport(modifier = modifier, activities = activities)
        }
    }
}
