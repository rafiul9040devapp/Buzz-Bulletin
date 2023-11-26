package com.rafiul.buzzbulletin.remote

import com.rafiul.buzzbulletin.models.ResponseNews
import com.rafiul.buzzbulletin.utils.EVERYTHING
import com.rafiul.buzzbulletin.utils.TOP_HEADLINES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET(TOP_HEADLINES)
    suspend fun getBreakingNews(
        @Query("category") category: String,
        @Query("country") country: String = "us",
    ): Response<ResponseNews>

    @GET(EVERYTHING)
    suspend fun searchForNews(
        @Query("q") query: String
    ): Response<ResponseNews>
}