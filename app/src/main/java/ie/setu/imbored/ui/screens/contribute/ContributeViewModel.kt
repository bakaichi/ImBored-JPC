package ie.setu.imbored.ui.screens.contribute

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.imbored.data.repository.ActivityRepository
import ie.setu.imbored.firebase.services.AuthService
import ie.setu.imbored.firebase.services.FirestoreService
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContributeViewModel @Inject constructor(
    private val repository: FirestoreService, //ActivityRepository
        private val authService : AuthService
) : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    fun insert(activity: ActivityModel) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.insert(authService.email!!,activity)
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
