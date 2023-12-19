package com.andrews.weather

import android.app.Application
import com.andrews.weather.di.appModule
import com.andrews.weather.di.instrumentedTestModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            modules(appModule, instrumentedTestModule)
        }
    }
}