package com.newsovert.dashboard.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.newsovert.R
import com.newsovert.dashboard.fragments.HomeFragment
import com.newsovert.dashboard.models.response.CategoryWiseResponse
import com.newsovert.dashboard.models.response.Sources
import com.newsovert.dashboard.models.response.TopHeadLinesResponse
import com.newsovert.dashboard.viewmodels.HomeViewModel
import com.newsovert.utils.MyNewsApp
import com.newsovert.utils.NetworkResult
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2
    lateinit var categoryWiseResponse: CategoryWiseResponse

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var homeViewModel: HomeViewModel

    companion object {
        val ARG_OBJECT = "object"
        lateinit var listCategory: MutableList<String>
    }

    val name by lazy {
        null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        MyNewsApp.instance.dashboardComponent.inject(this)
        init()
        initViewModel()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun init() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun initViewPager2(category: CategoryWiseResponse) {
        listCategory = getUniqueCategory()
        demoCollectionAdapter = DemoCollectionAdapter(this, category.Sources)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = demoCollectionAdapter
        val tabLayout = findViewById(R.id.tab_layout) as TabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //  val tabName = categoryWiseResponse.Sources.get(position).category
            if (position >= listCategory.size) {
                return@TabLayoutMediator
            }
            tab.text = listCategory.get(position)
        }.attach()

    }

    fun initViewModel() {
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getCatewiseNews().observe(this, {
            when (it) {

                is NetworkResult.Success -> {
                    Log.e("OnSuccess", it.data.toString())
                    categoryWiseResponse = it.data
                    initViewPager2(categoryWiseResponse)
                }
                is NetworkResult.Error -> {
                    Log.e("OnError", it.exception.toString())

                }
            }
        })
    }

    fun getUniqueCategory(): MutableList<String> {
        val categoryList: MutableList<String> = mutableListOf()
        for (key in categoryWiseResponse.Sources) {
            if (!categoryList.contains(key.category))
                categoryList.add(key.category)
        }
        return categoryList
    }

    class DemoCollectionAdapter(fragment: MainActivity, val sources: MutableList<Sources>) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = listCategory?.size ?: 0

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            val fragment = HomeFragment()
            fragment.arguments = Bundle().apply {
                // Our object is just an integer :-P
                putInt(ARG_OBJECT, position + 1)
            }
            return fragment
        }
    }

}