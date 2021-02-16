package com.newsovert.utils

import android.app.Application
import com.newsovert.dashboard.dagger.component.DaggerDashboardComponent

import com.newsovert.dashboard.dagger.component.DashboardComponent
import com.newsovert.dashboard.dagger.modules.ContextModule
import com.newsovert.dashboard.dagger.modules.NetworkModule

class MyNewsApp : Application() {

    companion object{
        lateinit var instance:MyNewsApp
    }
    lateinit var dashboardComponent: DashboardComponent
    override fun onCreate() {
        super.onCreate()
        instance = this
        dashboardComponent = DaggerDashboardComponent.builder().contextModule(ContextModule(this)).build()


    }

}