package com.android.finder

import android.app.Application
import android.content.Context
import android.util.Log

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.e("App", instance.toString())
    }

}