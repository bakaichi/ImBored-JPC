package ie.setu.imbored.data.room

import androidx.room.*
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDAO {
    @Query("SELECT * FROM activity_table")
    fun getAllActivities(): Flow<List<ActivityModel>>

    @Insert
    suspend fun insert(activity: ActivityModel)

    @Update
    suspend fun update(activity: ActivityModel)

    @Delete
    suspend fun delete(activity: ActivityModel)

    @Query("SELECT * FROM activity_table WHERE id=:id")
    fun getActivity(id: Int): Flow<ActivityModel>

    @Query("UPDATE activity_table SET description=:description WHERE id = :id")
    suspend fun updateDescription(id: Int, description: String)
}
