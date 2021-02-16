package com.newsovert.dashboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.progressindicator.ProgressIndicator
import com.newsovert.R
import com.newsovert.dashboard.activities.MainActivity.Companion.ARG_OBJECT
import com.newsovert.dashboard.adapters.HomeAdapter
import com.newsovert.dashboard.models.response.TopHeadLinesResponse
import com.newsovert.dashboard.viewmodels.HomeViewModel
import com.newsovert.utils.MyNewsApp
import com.newsovert.utils.NetworkResult
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var progressBar: ProgressIndicator
    lateinit var img_treding:ImageView
    lateinit var txt_trending:TextView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var homeAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home2, container, false)
        MyNewsApp.instance.dashboardComponent.inject(this)
        init(root)
        initRecylerview(root)
        initViewModel()
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
//            val textView: TextView = view.findViewById(android.R.id.text1)
//            textView.text = getInt(ARG_OBJECT).toString()
        }
    }

    fun init(view: View) {
        progressBar = view.findViewById(R.id.progressBar)
        img_treding = view.findViewById(R.id.img_treding)
        txt_trending = view.findViewById(R.id.txt_trending)
        homeAdapter = HomeAdapter(requireActivity())
    }

    fun initRecylerview(root: View) {
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setItemAnimator(DefaultItemAnimator())
            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
            setHasFixedSize(true)
            adapter = homeAdapter
        }

    }

    fun initViewModel() {
        homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        //homeViewModel.getTopHeadlines()
        homeViewModel.getTopHeadlines().observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> progressBar.visibility = View.VISIBLE
                is NetworkResult.Success -> {
                    Log.e("OnSuccess", it.data.toString())
                    progressBar.visibility = View.GONE
                    showUI(it.data.articles.get(it.data?.articles?.size-1))
                    homeAdapter.setData(it.data.articles)
                }
                is NetworkResult.Error -> {
                    Log.e("OnError", it.exception.toString())
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    fun showUI(top: TopHeadLinesResponse.Articles) {
        Glide.with(requireActivity()).load(top?.urlToImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_treding)
        txt_trending.text = top.title
    }
}