package com.newsovert.dashboard.dagger.modules

import android.content.Context
import com.newsovert.retrofit.NewsApi
import com.newsovert.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule() {

    @Singleton
    @Provides
    fun provideRetrofitApi(): RetrofitClient {
        return RetrofitClient()
    }

    @Singleton
    @Provides
    fun provideOkHttp(context: Context): OkHttpClient {
        return RetrofitClient.okHttp(context)
    }

    @Singleton
    @Provides
    fun provideNewApi(context: Context): NewsApi {
        return RetrofitClient.create(context)
    }


}