package com.newsovert.retrofit

import com.newsovert.dashboard.models.response.CategoryWiseResponse
import com.newsovert.dashboard.models.response.TopHeadLinesResponse
import com.newsovert.utils.Constant
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {
    @GET("top-headlines?country=us&apiKey=" + Constant.API_KEY)
    fun getAllNews(): Single<TopHeadLinesResponse>

    @GET("sources?apiKey=" + Constant.API_KEY)
    fun getCategoryWiseNews(): Single<CategoryWiseResponse>
}