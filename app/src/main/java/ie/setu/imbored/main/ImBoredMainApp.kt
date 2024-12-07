package ie.setu.imbored.main

import android.app.Application
import timber.log.Timber

class ImBoredMainApp : Application() {
    override fun onCreate() {
        super.onCreate()
    Timber.plant(Timber.DebugTree())
        Timber.i("Starting Im Bored Application")
    }
}