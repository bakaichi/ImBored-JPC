package ie.setu.imbored.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.imbored.data.repository.ActivityRepository
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ActivityRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var activity = mutableStateOf(ActivityModel())
    val id: Int = checkNotNull(savedStateHandle["id"])

    init {
        viewModelScope.launch {
            repository.get(id).collect { fetchedActivity ->
                activity.value = fetchedActivity
            }
        }
    }

    fun updateDescription(description: String) {
        viewModelScope.launch {
            repository.updateDescription(id, description)
        }
    }
}
