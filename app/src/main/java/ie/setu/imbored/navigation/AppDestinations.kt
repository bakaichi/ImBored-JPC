package ie.setu.imbored.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.ui.graphics.vector.ImageVector

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

val bottomAppBarDestinations = listOf(Contribute, Report)
val allDestinations = listOf(Report, Contribute)
