package com.newsovert.dashboard.dagger.component

import com.newsovert.dashboard.activities.DetailNewsActivity
import com.newsovert.dashboard.activities.MainActivity
import com.newsovert.dashboard.dagger.modules.*
import com.newsovert.dashboard.fragments.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class,NetworkModule::class, AppModule::class,ViewModelModule::class,HomeRepositoryModule::class])
interface DashboardComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(detailNewsActivity: DetailNewsActivity)

    fun inject(homeFragment: HomeFragment)
}