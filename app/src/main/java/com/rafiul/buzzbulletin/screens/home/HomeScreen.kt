package com.rafiul.buzzbulletin.screens.home


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafiul.buzzbulletin.base.ApiState
import com.rafiul.buzzbulletin.widgets.NewRowComponent
import com.rafiul.buzzbulletin.widgets.TabItem
import com.rafiul.buzzbulletin.widgets.tabItemList
import kotlinx.coroutines.launch


const val TAG = "HomeScreen"

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    val news = viewmodel.newsViewState.collectAsState()
    val userScrollable = mutableStateOf(true)

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }


    val pagerStateHorizontal = rememberPagerState {
        tabItemList.size
    }


    LaunchedEffect(selectedTabIndex) {
        viewmodel.getNews(tabItemList[selectedTabIndex].title)
        pagerStateHorizontal.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerStateHorizontal.currentPage, pagerStateHorizontal.isScrollInProgress) {

        if (!pagerStateHorizontal.isScrollInProgress) {
            selectedTabIndex = pagerStateHorizontal.currentPage
        }

    }

    val pagerStateVertical = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) {
        100
    }

    LaunchedEffect(pagerStateVertical) {
        snapshotFlow { pagerStateVertical.currentPage }.collect { page ->
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

            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = Color.Green,
                        height = 2.dp,
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    )
                },
                divider = {
                    Divider(thickness = 0.dp, color = MaterialTheme.colorScheme.background)
                },
                tabs = {
                    tabItemList.forEachIndexed { index: Int, tabItem: TabItem ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(
                                    text = tabItem.title,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = tabItem.takeIf { index == selectedTabIndex }?.selectedIcon
                                        ?: tabItem.unSelectedIcon,
                                    contentDescription = tabItem.title
                                )
                            },
                            selectedContentColor = Color.Green,
                            unselectedContentColor = Color.White,
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                        )
                    }
                }
            )


            HorizontalPager(
                state = pagerStateHorizontal,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    VerticalPager(
                        state = pagerStateVertical,
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
                            }
                        }
                        if (page == (news.value as? ApiState.Success)?.data?.articles?.size?.minus(1)) {
                            val coroutineScope = rememberCoroutineScope()
                            Button(onClick = {
                                coroutineScope.launch {
                                    userScrollable.value = true
                                    pagerStateVertical.animateScrollToPage(-1)
                                }
                            }) {
                                Text(text = "Scroll to Top", color = Color.White)
                            }
                        }
                    }
                }

            }
        }
    }
}


