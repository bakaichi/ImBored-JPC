package ie.setu.imbored.ui.screens.contribute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.imbored.data.repository.ActivityRepository
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContributeViewModel @Inject constructor(
    private val repository: ActivityRepository
) : ViewModel() {
    fun insert(activity: ActivityModel) = viewModelScope.launch {
        repository.insert(activity)
    }
}
