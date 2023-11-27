package com.rafiul.buzzbulletin.screens.home

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafiul.buzzbulletin.base.ApiState
import com.rafiul.buzzbulletin.base.map
import com.rafiul.buzzbulletin.models.ResponseNews
import com.rafiul.buzzbulletin.remote.NewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: NewsRepositoryImpl) : ViewModel() {

    private val _newsViewState: MutableStateFlow<ApiState<ResponseNews>> =
        MutableStateFlow(ApiState.Loading)
    val newsViewState: StateFlow<ApiState<ResponseNews>> = _newsViewState

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            repository.getBreakingNews("Sports").collectLatest { newsApiState ->
                _newsViewState.value = newsApiState
            }
        }
    }
}


