package com.rafiul.buzzbulletin.screens.home


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafiul.buzzbulletin.base.ApiState


const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    val news = viewmodel.newsViewState.collectAsState()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "News",
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Light
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.onBackground,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (news.value) {

                is ApiState.Failure -> {
                    Log.d(TAG, "Failure ${news.value}")
                    val errorMessage = (news.value as ApiState.Failure).message
                    Log.e(TAG, "ApiState.Failure: $errorMessage")
                }

                ApiState.Loading -> {
                    Log.d(TAG, "Loading.....")
                    CircularProgressIndicator()

                }

                is ApiState.Success -> {
                    val responseNews = (news.value as ApiState.Success).data
                    Log.d(
                        TAG,
                        "Success ${responseNews.articles} ${responseNews.status} ${responseNews.totalResults}"
                    )

                    LazyColumn {
                        items(items = responseNews.articles ?: emptyList()) { article ->
                            Log.d("My Response", "$article")
                            if (article != null) {
                                article.title?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.padding(all = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

