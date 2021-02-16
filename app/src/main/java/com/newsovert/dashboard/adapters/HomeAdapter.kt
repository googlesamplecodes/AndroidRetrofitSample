package com.newsovert.dashboard.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.newsovert.R
import com.newsovert.dashboard.activities.DetailNewsActivity
import com.newsovert.dashboard.models.response.TopHeadLinesResponse
import java.io.Serializable
import javax.inject.Inject

class HomeAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var list: List<TopHeadLinesResponse.Articles>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.row_item_home_layout, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, i: Int) {

        list?.let {
            Glide.with(context!!).load(list?.get(i)?.urlToImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgview_topic)

            holder.txt_title.text = list?.get(i)?.title
            holder.txt_description.text = list?.get(i)?.description
            holder.txt_publisheddate.text = list?.get(i)?.publishedAt
            holder.txt_status.text = list?.get(i)?.author
            holder.txt_title.setOnClickListener({
                val intent = Intent(context,DetailNewsActivity::class.java).apply {
                    putExtra("obj",list?.get(i))
                    context.startActivity(this)
                }
            })
        }
    }

    override fun getItemCount(): Int {

        return list?.size ?: 0
    }

    fun setData(data: List<TopHeadLinesResponse.Articles>) {
        list = data
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_title: TextView
        val txt_description: TextView
        val txt_publisheddate: TextView
        val txt_status: TextView
        val img_share: ImageView
        val imgview_topic: ImageView

        init {
            txt_title = itemView.findViewById(R.id.txt_title)
            txt_description = itemView.findViewById(R.id.txt_description)
            txt_publisheddate = itemView.findViewById(R.id.txt_publisheddate)
            txt_status = itemView.findViewById(R.id.txt_status)
            img_share = itemView.findViewById(R.id.img_share)
            imgview_topic = itemView.findViewById(R.id.imgview_topic)

        }

    }
}