package ie.setu.imbored.ui.components.contribute

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.imbored.R
import ie.setu.imbored.models.ActivityModel
import ie.setu.imbored.ui.screens.contribute.ContributeViewModel
import ie.setu.imbored.ui.screens.map.MapViewModel
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import timber.log.Timber

@Composable
fun ContributeButton(
    modifier: Modifier = Modifier,
    activity: ActivityModel,
    contributeViewModel: ContributeViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel(),
    onTotalContributedChange: (Int) -> Unit
) {
    var totalContributed by remember { mutableIntStateOf(0) }

    val locationLatLng = mapViewModel.currentLatLng.collectAsState().value
    LaunchedEffect(mapViewModel.currentLatLng){
        mapViewModel.getLocationUpdates()
    }

    Timber.i("DONATE BUTTON LAT/LNG COORDINATES " +
            "lat/Lng: " + "$locationLatLng ")


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                totalContributed += activity.contributionAmount
                onTotalContributedChange(totalContributed)

                val activityLatLng = activity.copy(
                    latitude = locationLatLng.latitude,
                    longitude = locationLatLng.longitude
                )
                contributeViewModel.insert(activityLatLng)


                Timber.i("Activity added: $activity")
                Timber.i("Total contributed amount: $totalContributed")
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Contribute")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.contributeButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " ")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    append(totalContributed.toString())
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ContributeButtonPreview() {
    ImBoredJPCTheme {
        ContributeButton(
            modifier = Modifier,
            activity = ActivityModel(),
            onTotalContributedChange = {}
        )
    }
}

