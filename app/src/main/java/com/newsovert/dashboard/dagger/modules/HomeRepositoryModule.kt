package com.newsovert.dashboard.dagger.modules

import com.newsovert.dashboard.repository.HomeRepository
import com.newsovert.retrofit.NewsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HomeRepositoryModule {

    @Singleton
    @Provides
    fun provideHomeRepository(newsApi: NewsApi):HomeRepository{
        return HomeRepository(newsApi)
    }

}