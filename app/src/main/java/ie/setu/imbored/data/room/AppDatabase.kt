package ie.setu.imbored.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.imbored.models.ActivityModel

@Database(entities = [ActivityModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDAO(): ActivityDAO
}
