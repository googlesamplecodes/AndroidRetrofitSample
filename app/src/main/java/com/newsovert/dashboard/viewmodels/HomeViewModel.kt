package com.newsovert.dashboard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsovert.dashboard.models.response.CategoryWiseResponse
import com.newsovert.dashboard.models.response.TopHeadLinesResponse
import com.newsovert.dashboard.repository.HomeRepository
import com.newsovert.utils.NetworkResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(val homeRepository: HomeRepository) : ViewModel() {

    fun getTopHeadlines(): MutableLiveData<NetworkResult<TopHeadLinesResponse>> {
        return homeRepository.fetchTopHeadlines()
    }

    fun getCatewiseNews():MutableLiveData<NetworkResult<CategoryWiseResponse>>{
        return homeRepository.fetchCategorywiseNews()
    }

    override fun onCleared() {
        super.onCleared()
        homeRepository.clear()
    }

}