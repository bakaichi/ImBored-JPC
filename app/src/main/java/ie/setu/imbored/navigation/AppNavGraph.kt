package ie.setu.imbored.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.imbored.ui.screens.contribute.ContributeScreen
import ie.setu.imbored.ui.screens.details.DetailsScreen
import ie.setu.imbored.ui.screens.home.HomeScreen
import ie.setu.imbored.ui.screens.login.LoginScreen
import ie.setu.imbored.ui.screens.map.MapScreen
import ie.setu.imbored.ui.screens.profile.ProfileScreen
import ie.setu.imbored.ui.screens.register.RegisterScreen
import ie.setu.imbored.ui.screens.report.ReportScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
    isShowAllActivities: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        // Login Screen
        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }

        // Register Screen
        composable(route = Register.route) {
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }

        // Home Screen
        composable(route = "home") {
            HomeScreen(modifier = modifier)
        }

        // Report Screen
        composable(route = Report.route) {
            ReportScreen(
                modifier = modifier,
                onClickDetails = { activityId: String ->
                    navController.navigateToActivityDetails(activityId)
                },
                isShowAllActivities = isShowAllActivities
            )
        }

        // Contribute Screen
        composable(route = "contribute") {
            ContributeScreen(modifier = modifier)
        }

        // Details Screen
        composable(
            route = Details.route,
            arguments = Details.arguments
        ) { backStackEntry ->
            val idArg = backStackEntry.arguments?.getString(Details.idArg) ?: ""
            DetailsScreen(
                modifier = modifier,
                activityId = idArg
            )
        }

        // Profile Screen with Logout Logic
        composable(route = Profile.route) {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                },
            )
        }

        // Map Screen
        composable(route = Map.route) {
            MapScreen(
                isShowAllActivities = isShowAllActivities
            )
        }
    }
}

private fun NavHostController.navigateToActivityDetails(activityId: String) {
    this.navigate("details/$activityId")
}
