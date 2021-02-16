package com.newsovert.dashboard.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TopHeadLinesResponse( @SerializedName("status")var status:String,
                                 @SerializedName("totalResults") var totalResults:Int,
                                 @SerializedName("articles") var articles:List<Articles>)  {

    data class Articles(
        @SerializedName("source")
        var source: Source,
        @SerializedName("author")
        val author: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("urlToImage")
        val urlToImage: String,
        @SerializedName("publishedAt")
        val publishedAt: String,
        @SerializedName("content")
        val content: String
    ):Serializable

    data class Source(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String
    ):Serializable


}

