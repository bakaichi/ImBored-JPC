package ie.setu.imbored.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.imbored.firebase.services.AuthService
import ie.setu.imbored.firebase.services.FirestoreService
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService: AuthService
) : ViewModel() {

    private val _activities = MutableStateFlow<List<ActivityModel>>(emptyList())
    val uiActivities: StateFlow<List<ActivityModel>> = _activities.asStateFlow()

    private var error = mutableStateOf<Exception?>(null)
    private var iserror = mutableStateOf(false)
    private var isloading = mutableStateOf(false)

    init {
        getActivities()
    }

    fun getActivities() {
        viewModelScope.launch {
            try {
                isloading.value = true
                repository.getAll(authService.email!!).collect { items ->
                    _activities.value = items
                    iserror.value = false
                    isloading.value = false
                }
                Timber.i("DVM RVM = : ${_activities.value}")
            }
            catch(e:Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    fun deleteActivity(activity: ActivityModel)
            = viewModelScope.launch {
        repository.delete(authService.email!!,activity._id)
    }
}

