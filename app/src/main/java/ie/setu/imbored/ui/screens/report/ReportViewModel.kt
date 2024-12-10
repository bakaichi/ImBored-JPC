package ie.setu.imbored.ui.screens.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.imbored.data.repository.ActivityRepository
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: ActivityRepository
) : ViewModel() {
    private val _activities = MutableStateFlow<List<ActivityModel>>(emptyList())
    val uiActivities: StateFlow<List<ActivityModel>> = _activities.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { activities ->
                _activities.value = activities
            }
        }
    }

    fun deleteActivity(activity: ActivityModel) {
        viewModelScope.launch {
            repository.delete(activity)
        }
    }
}
