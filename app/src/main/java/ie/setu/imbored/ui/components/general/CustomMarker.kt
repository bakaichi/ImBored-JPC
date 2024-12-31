package ie.setu.imbored.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import ie.setu.imbored.ui.theme.mEndGradientColor
import ie.setu.imbored.ui.theme.mStartGradientColor

@Composable
fun CustomMarker() {
    Box(
        modifier = Modifier
            .height(35.dp)
            .width(30.dp)
    ) {
        Box(modifier = Modifier
            .height(30.dp)
            .width(30.dp)
            .rotate(45f)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp))
            .border(
                2.dp,
                color = Color.White,
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp)
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        mStartGradientColor,
                        mEndGradientColor,
                    )
                )
            ),
        )
//        Image(
//            painter = painterResource(id = R.drawable.aboutus_homer),
//            contentDescription = "homer image",
//            modifier = Modifier.padding( 4.dp)
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomMarkerPreview() {
    ImBoredJPCTheme  {
        CustomMarker()
    }
}
