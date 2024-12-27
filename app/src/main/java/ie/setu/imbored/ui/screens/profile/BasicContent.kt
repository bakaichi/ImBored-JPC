package ie.setu.imbored.ui.screens.profile

import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.imbored.ui.components.general.HeadingLogoComponent

@Composable
fun BasicContent(
    displayName: String,
    email: String
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        HeadingLogoComponent()
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = displayName,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = email,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}