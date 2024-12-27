package ie.setu.imbored.firebase.database

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import ie.setu.imbored.data.rules.Constants.ACTIVITY_COLLECTION
import ie.setu.imbored.data.rules.Constants.USER_EMAIL
import ie.setu.imbored.firebase.services.Activities
import ie.setu.imbored.firebase.services.Activity
import ie.setu.imbored.firebase.services.AuthService
import ie.setu.imbored.firebase.services.FirestoreService
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.toObject
import timber.log.Timber
import java.util.Date

class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Activities {

        return firestore.collection(ACTIVITY_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }

    override suspend fun get(email: String,
                             activityId: String): Activity? {
        return firestore.collection(ACTIVITY_COLLECTION)
            .document(activityId).get().await().toObject()
    }

    override suspend fun insert(email: String, activity: Activity) {
        val activityWithEmailAndImage =
            activity.copy(
                email = email,
                imageUri = auth.customPhotoUri!!.toString()
            )

        firestore.collection(ACTIVITY_COLLECTION)
            .add(activityWithEmailAndImage)
            .await()
    }

    override suspend fun updatePhotoUris(email: String, uri: Uri) {

        firestore.collection(ACTIVITY_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Timber.i("FSR Updating ID ${document.id}")
                    firestore.collection(ACTIVITY_COLLECTION)
                        .document(document.id)
                        .update("imageUri", uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Timber.i("Error $exception")
            }
    }


    override suspend fun update(email: String,
                                activity: Activity)
    {
        val activityWithModifiedDate =
            activity.copy(dateModified = Date())

        firestore.collection(ACTIVITY_COLLECTION)
            .document(activity._id)
            .set(activityWithModifiedDate).await()
    }

    override suspend fun delete(email: String,
                                activityId: String)
    {
        firestore.collection(ACTIVITY_COLLECTION)
            .document(activityId)
            .delete().await()
    }
}