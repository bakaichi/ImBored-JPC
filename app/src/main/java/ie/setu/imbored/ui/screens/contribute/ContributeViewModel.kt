package ie.setu.imbored.ui.screens.contribute

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.imbored.firebase.services.AuthService
import ie.setu.imbored.firebase.services.FirestoreService
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContributeViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService : AuthService
) : ViewModel() {

    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    private val _chosenLatLng = MutableStateFlow(LatLng(0.0, 0.0))
    val chosenLatLng: StateFlow<LatLng> get() = _chosenLatLng

    fun setChosenLatLng(latLng: LatLng) {
        _chosenLatLng.value = latLng
        Timber.i("ChosenLatLng set to $latLng")
    }

    fun insert(activity: ActivityModel) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.insert(authService.email!!, activity)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
            Timber.i("DVM Insert Message = : ${error.value.message} and isError ${isErr.value}")
        }
}
