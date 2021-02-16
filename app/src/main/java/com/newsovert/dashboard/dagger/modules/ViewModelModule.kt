package com.newsovert.dashboard.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learncoding.cleanarchitecture.viewmodels.ViewModelFactory
import com.learncoding.cleanarchitecture.viewmodels.ViewModelKey
import com.newsovert.dashboard.viewmodels.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(homeViewModel: HomeViewModel):ViewModel


    @Binds
    @Singleton
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory):ViewModelProvider.Factory



}