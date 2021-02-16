package com.newsovert.dashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.newsovert.R
import com.newsovert.dashboard.models.response.TopHeadLinesResponse
import com.newsovert.dashboard.viewmodels.HomeViewModel
import com.newsovert.utils.MyNewsApp
import com.newsovert.utils.NetworkResult
import javax.inject.Inject

class DetailNewsActivity : AppCompatActivity() {
    lateinit var txt_title: TextView
    lateinit var txt_description: TextView
    lateinit var txt_author: TextView
    lateinit var txt_published_date: TextView
    lateinit var img_topic: ImageView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        MyNewsApp.instance.dashboardComponent.inject(this)
        init()
        setBackActionArrow()
        initViewModel()
        getBundleData()
    }


    fun init() {
        txt_title = findViewById(R.id.txt_title)
        txt_description = findViewById(R.id.txt_description)
        txt_author = findViewById(R.id.txt_author)
        txt_published_date = findViewById(R.id.txt_published_date)
        img_topic = findViewById(R.id.img_topic)
    }

    fun setBackActionArrow(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun initViewModel() {
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
//        homeViewModel.getTopHeadlines()
        homeViewModel.getTopHeadlines().observe(this, {
            when (it) {
                is NetworkResult.Success -> {
                    Log.e("OnSuccess", it.data.toString())
                }
                is NetworkResult.Error -> Log.e("OnError", it.exception.toString())
            }
        })
    }

    fun getBundleData() {
        val newsData = intent?.getSerializableExtra("obj") as TopHeadLinesResponse.Articles
        showData(newsData)
    }

    fun showData(newsData: TopHeadLinesResponse.Articles) {
        txt_title.text = newsData?.title
        txt_description.text = newsData?.description
        txt_published_date.text = newsData?.publishedAt
        txt_author.text = newsData?.author
        Glide.with(this).load(newsData?.urlToImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_topic)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}