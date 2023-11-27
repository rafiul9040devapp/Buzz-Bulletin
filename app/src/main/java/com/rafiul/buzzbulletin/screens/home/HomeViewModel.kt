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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: NewsRepositoryImpl) : ViewModel() {

    private val _newsViewState = MutableStateFlow(NewsState())
    val userViewState: StateFlow<NewsState> = _newsViewState

    init {
        viewModelScope.launch {
            repository.getBreakingNews("Sports").map { state ->
                when (state) {
                    is ApiState.Failure -> {}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {
                        _newsViewState.value.data = state.data
                    }
                }

            }
        }
    }
}

data class NewsState(
    var data: ResponseNews = ResponseNews(emptyList(), "", 0)
)
