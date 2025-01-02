package ie.setu.imbored.firebase.services

import android.net.Uri
import ie.setu.imbored.models.ActivityModel
import kotlinx.coroutines.flow.Flow

typealias Activity = ActivityModel
typealias Activities = Flow<List<Activity>>

interface FirestoreService {

    suspend fun getAll(email: String) : Activities
    suspend fun get(email: String, activityId: String) : Activity?
    suspend fun insert(email: String, activity: Activity)
    suspend fun update(email: String, activity: Activity)
    suspend fun delete(email: String, activityId: String)
    suspend fun updatePhotoUris(email: String, uri: Uri)
    suspend fun getAllActivities(): Activities
}