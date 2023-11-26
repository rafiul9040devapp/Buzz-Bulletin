package com.rafiul.buzzbulletin.models


import com.google.gson.annotations.SerializedName

data class ResponseNews(
    @SerializedName("articles")
    val articles: List<Article?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?
)