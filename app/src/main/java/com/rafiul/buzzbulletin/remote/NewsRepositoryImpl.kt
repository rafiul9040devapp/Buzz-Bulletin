package com.rafiul.buzzbulletin.remote

import com.rafiul.buzzbulletin.base.ApiState
import com.rafiul.buzzbulletin.base.BaseRepository
import com.rafiul.buzzbulletin.models.ResponseNews
import com.rafiul.buzzbulletin.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
) : NewsRepository, BaseRepository() {
    override suspend fun getBreakingNews(category: String): Flow<ApiState<ResponseNews>> =
        safeApiCall {
            api.getBreakingNews(category = category)
        }

    override suspend fun searchForNews(query: String): Flow<ApiState<ResponseNews>> = safeApiCall {
        api.searchForNews(query = query)
    }
}