package ie.setu.imbored.ui.screens.contribute

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ie.setu.imbored.ui.screens.map.MapViewModel
import timber.log.Timber

@Composable
fun LocationPickerScreen(
    mapViewModel: MapViewModel = hiltViewModel(),
    onLocationSelected: (LatLng) -> Unit
) {
    // current device location
    val currentLatLng by mapViewModel.currentLatLng.collectAsState()

    // get a lat/lng for the marker
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLatLng, 15f)
    }

    LaunchedEffect(true) {
        mapViewModel.getLocationUpdates()
    }

    Column(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = true,
                mapToolbarEnabled = true
            ),
            properties = MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true
            ),
            onMapLoaded = {
                cameraPositionState.move(
                    CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f)
                )
            },
            // press and hold to set marker
            onMapLongClick = { latLng ->
                Timber.i("Map clicked at: $latLng")
                markerPosition = latLng
            }
        ) {
            // place marker at clicked position
            markerPosition?.let { chosenPos ->
                Marker(
                    state = MarkerState(position = chosenPos),
                    title = "Chosen Location",
                    snippet = "Press & hold another spot to reselect."
                )
            }
        }

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                // if position selected confirm
                markerPosition?.let { confirmedPos ->
                    onLocationSelected(confirmedPos)
                }
            }
        ) {
            Text(text = "Confirm Location")
        }
    }
}
