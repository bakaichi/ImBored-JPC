package ie.setu.imbored.firebase.database

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

    override suspend fun insert(email: String,
                                activity: Activity)
    {
        val activityWithEmail = activity.copy(email = email)

        firestore.collection(ACTIVITY_COLLECTION)
            .add(activityWithEmail)
            .await()

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