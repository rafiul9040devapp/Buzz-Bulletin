package com.rafiul.buzzbulletin.repository

import com.rafiul.buzzbulletin.base.ApiState
import com.rafiul.buzzbulletin.models.ResponseNews
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getBreakingNews(category: String): Flow<ApiState<ResponseNews>>

    suspend fun searchForNews(query: String): Flow<ApiState<ResponseNews>>

}