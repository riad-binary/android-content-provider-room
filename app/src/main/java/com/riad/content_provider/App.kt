package com.riad.content_provider

import android.app.Application
import com.riad.content_provider.data.db.AppDatabase

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabase.getInstance(instance)
    }


}