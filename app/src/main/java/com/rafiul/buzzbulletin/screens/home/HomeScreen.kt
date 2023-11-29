package com.rafiul.buzzbulletin.screens.home


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafiul.buzzbulletin.base.ApiState
import com.rafiul.buzzbulletin.models.Article
import kotlinx.coroutines.launch


const val TAG = "HomeScreen"

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    val news = viewmodel.newsViewState.collectAsState()
    val userScrollable = mutableStateOf(true)

    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) {
        100
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Log.d("Page change", "Page changed to $page")
            val totalPages = (news.value as? ApiState.Success)?.data?.articles?.size ?: 0
            val isLastPage = page == totalPages - 1
            if (isLastPage) {
                userScrollable.value = false
            }
        }
    }

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


            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageSize = PageSize.Fill,
                pageSpacing = 8.dp,
                userScrollEnabled = userScrollable.value
            ) { page ->
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

                        responseNews.articles?.getOrNull(page)?.let { NewRowComponent(page, it) }


//                        val listSize = responseNews.articles?.size?.minus(1)
//                        if (responseNews.articles?.isNotEmpty() == true) {
//                            if (page <= (listSize ?: 0)) {
//                                responseNews.articles[page]?.let { NewRowComponent(page, it) }
//                            } else {
//                                userScrollable.value = false
//                            }
//                        }
                    }
                }


                if (page == (news.value as? ApiState.Success)?.data?.articles?.size?.minus(1)) {
                    val coroutineScope = rememberCoroutineScope()
                    Button(onClick = {
                        coroutineScope.launch {
                            userScrollable.value = true
                            pagerState.animateScrollToPage(-1)
                        }
                    }) {
                        Text(text = "Scroll to Top", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun NewRowComponent(page: Int, article: Article?) {

    if (article != null) {
        article.title?.let { Text(text = it) }
    }
}

