package com.newsovert.dashboard.repository

import androidx.lifecycle.MutableLiveData
import com.newsovert.dashboard.models.response.CategoryWiseResponse
import com.newsovert.dashboard.models.response.TopHeadLinesResponse
import com.newsovert.retrofit.NewsApi
import com.newsovert.utils.NetworkResult
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeRepository @Inject constructor(val newsApi: NewsApi) {
    var compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()
    }

    fun fetchTopHeadlines(): MutableLiveData<NetworkResult<TopHeadLinesResponse>> {
        var getTopHeadLinesResponse: MutableLiveData<NetworkResult<TopHeadLinesResponse>> =
            MutableLiveData()
        compositeDisposable.add(
            newsApi.getAllNews().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getTopHeadLinesResponse.postValue(NetworkResult.Success(it))
                }, {
                    it.message?.let {
                        getTopHeadLinesResponse.postValue(NetworkResult.Error(it))
                    }

                })
        )
        return getTopHeadLinesResponse
    }

    fun fetchCategorywiseNews(): MutableLiveData<NetworkResult<CategoryWiseResponse>> {
        var categoryWiseData: MutableLiveData<NetworkResult<CategoryWiseResponse>> =
            MutableLiveData()
        compositeDisposable.add(
            newsApi.getCategoryWiseNews().subscribeOn(Schedulers.io())
                .subscribe({
                    categoryWiseData.postValue(NetworkResult.Success(it))
                }, {
                    it.message?.let {
                        categoryWiseData.postValue(NetworkResult.Error(it))
                    }

                })
        )
        return categoryWiseData
    }

    fun clear() {
        compositeDisposable.dispose()
    }

}