package ie.setu.imbored

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import ie.setu.imbored.ui.screens.home.HomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ImBoredJPCTheme { HomeScreen() }
        }
    }
}
