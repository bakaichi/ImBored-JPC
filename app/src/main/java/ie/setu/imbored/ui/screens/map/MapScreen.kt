package ie.setu.imbored.ui.screens.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ie.setu.imbored.ui.components.general.CustomMarker
import ie.setu.imbored.ui.screens.report.ReportViewModel
import ie.setu.imbored.ui.theme.ImBoredJPCTheme
import timber.log.Timber

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),
    isShowAllActivities: MutableState<Boolean>
) {
    val uiSettings by remember { mutableStateOf(MapUiSettings(
        myLocationButtonEnabled = true,
        compassEnabled = true,
        mapToolbarEnabled = true
    )) }

    val properties by remember {
        mutableStateOf(MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = true
        ))
    }

    val activities = reportViewModel.uiActivities.collectAsState().value

    val currentLocation = mapViewModel.currentLatLng.collectAsState().value

    Timber.i("MAP LAT/LNG COORDINATES $currentLocation ")

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 14f)
    }
    LaunchedEffect(isShowAllActivities.value) {
        if (isShowAllActivities.value) {
            reportViewModel.getAllActivities()
        } else {
            reportViewModel.getActivities()
        }
    }
    LaunchedEffect(currentLocation){
        mapViewModel.getLocationUpdates()
        cameraPositionState.animate(CameraUpdateFactory.newLatLng(currentLocation))
        cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation, 14f)
    }

    Column{
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            properties = properties
        ) {
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Current Location",
                snippet = "This is My Current Location"
            )
            activities.forEach {
                val position = LatLng(it.latitude,it.longitude)
                MarkerComposable (
                    state = MarkerState(position = position),
                    title = it.title,
                    snippet = it.description
                ) { CustomMarker() }
            }

        }
    }}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    ImBoredJPCTheme {
        MapScreen( isShowAllActivities = remember { mutableStateOf(false) })
    }
}

