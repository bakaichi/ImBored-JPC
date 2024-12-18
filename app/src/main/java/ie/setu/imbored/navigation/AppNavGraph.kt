package ie.setu.imbored.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.imbored.ui.screens.contribute.ContributeScreen
import ie.setu.imbored.ui.screens.details.DetailsScreen
import ie.setu.imbored.ui.screens.home.HomeScreen
import ie.setu.imbored.ui.screens.login.LoginScreen
import ie.setu.imbored.ui.screens.profile.ProfileScreen
import ie.setu.imbored.ui.screens.register.RegisterScreen
import ie.setu.imbored.ui.screens.report.ReportScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        // Login Screen
        composable(route = "login") {
            LoginScreen(
                navController = navController,
                onLogin = { navController.navigate("home") { popUpTo("login") { inclusive = true } } }
            )
        }

        // Register Screen
        composable(route = "register") {
            RegisterScreen(
                navController = navController,
                onRegister = { navController.navigate("home") { popUpTo("register") { inclusive = true } } }
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
                onClickDetails = { id -> navController.navigate("details/$id") }
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
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Details.idArg)
            if (id != null) {
                DetailsScreen(id = id)
            }
        }
        // Profile Screen with Logout Logic
        composable(route = "profile") {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
