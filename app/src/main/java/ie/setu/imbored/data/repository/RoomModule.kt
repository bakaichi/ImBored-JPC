package ie.setu.imbored.data.repository

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.setu.imbored.data.room.AppDatabase
import ie.setu.imbored.data.room.ActivityDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "activity_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideActivityDAO(appDatabase: AppDatabase): ActivityDAO {
        return appDatabase.activityDAO()
    }

    @Provides
    fun provideActivityRepository(activityDAO: ActivityDAO): ActivityRepository {
        return ActivityRepository(activityDAO)
    }
}
