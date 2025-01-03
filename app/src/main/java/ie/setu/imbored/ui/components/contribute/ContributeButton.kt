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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
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
    contributeViewModel: ContributeViewModel,
    mapViewModel: MapViewModel,
    onTotalContributedChange: (Int) -> Unit
) {
    var totalContributed by remember { mutableIntStateOf(0) }

    val deviceLocation = mapViewModel.currentLatLng.collectAsState().value

    val chosenLatLng by contributeViewModel.chosenLatLng.collectAsState()

    // if location chosen by a user, use that. otherwise use device location
    val finalLatLng: LatLng = if (
        chosenLatLng.latitude != 0.0 || chosenLatLng.longitude != 0.0
    ) {
        chosenLatLng
    } else {
        deviceLocation
    }

    LaunchedEffect(mapViewModel.currentLatLng) {
        mapViewModel.getLocationUpdates()
    }

    Timber.i(
        "Contribute button LAT/LNG coordinates lat/Lng: $finalLatLng " +
                "(Chosen=$chosenLatLng, Device=$deviceLocation)"
    )

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

                // Overwrite activity’s lat/lng with final lat/lng:
                val activityWithLatLng = activity.copy(
                    latitude = finalLatLng.latitude,
                    longitude = finalLatLng.longitude
                )
                contributeViewModel.insert(activityWithLatLng)

                Timber.i("Activity added: $activityWithLatLng")
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


