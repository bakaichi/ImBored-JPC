package ie.setu.imbored.data.repository

import ie.setu.imbored.data.room.ActivityDAO
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val activityDAO: ActivityDAO
) {
    fun getAll(): Flow<List<ActivityModel>> = activityDAO.getAllActivities()

    suspend fun insert(activity: ActivityModel) = activityDAO.insert(activity)

    suspend fun update(activity: ActivityModel) = activityDAO.update(activity)

    suspend fun delete(activity: ActivityModel) = activityDAO.delete(activity)

    fun get(id: Int): Flow<ActivityModel> = activityDAO.getActivity(id)

    suspend fun updateDescription(id: Int, description: String) {
        activityDAO.updateDescription(id, description)
    }
}
