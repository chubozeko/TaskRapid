package com.chandistudios.taskrapid

import android.app.Application

class TaskRapidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)     // providing the application context
    }
}