package com.rafiul.buzzbulletin.remote

import com.rafiul.buzzbulletin.models.ResponseNews
import com.rafiul.buzzbulletin.utils.EVERYTHING
import com.rafiul.buzzbulletin.utils.TOP_HEADLINES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {


//    @Headers("x-api-key: 6da7c60c82d94facb3b9c7a7bd923ed8")
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