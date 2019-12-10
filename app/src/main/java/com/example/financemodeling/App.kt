package com.example.financemodeling

import android.app.Application
import com.example.financemodeling.di.components.AppComponent
import com.example.financemodeling.di.components.DaggerAppComponent

class App: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()

    }
}