package ie.setu.imbored.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object Report : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "Report"
    override val route = "report"
}

object Contribute : AppDestination {
    override val icon = Icons.Filled.AddCircle
    override val label = "Contribute"
    override val route = "contribute"
}

object Details : AppDestination {
    override val icon = Icons.Default.Info
    override val label = "Details"
    const val idArg = "id"
    override val route = "details/{$idArg}"
    val arguments = listOf(navArgument(idArg) { type = NavType.IntType })
}


val bottomAppBarDestinations = listOf(Contribute, Report)
val allDestinations = listOf(Report, Contribute, Details)
