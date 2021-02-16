package com.newsovert.retrofit

import android.content.Context
import com.newsovert.utils.Constant
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient() {

    companion object {


        fun create(context: Context): NewsApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .client(okHttp(context))
                .baseUrl(Constant.BASE_URL)
                .build()
            return retrofit.create(NewsApi::class.java)
        }

        fun okHttp(context: Context): OkHttpClient {
            val cacheSize = (5 * 1024 * 1024).toLong()
            val myCache = Cache(context.cacheDir, cacheSize)
            return OkHttpClient.Builder().cache(myCache).build()
        }

    }

}