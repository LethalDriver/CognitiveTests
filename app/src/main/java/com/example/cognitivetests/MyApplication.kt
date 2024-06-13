package com.example.cognitivetests

import android.app.Application
import com.example.cognitivetests.utils.httpClientModule
import com.example.cognitivetests.utils.serviceModule
import com.example.cognitivetests.utils.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        startKoin{
            androidContext(this@MyApplication)
            modules(listOf(httpClientModule, viewModelModule, serviceModule))
        }
        super.onCreate()
    }
}