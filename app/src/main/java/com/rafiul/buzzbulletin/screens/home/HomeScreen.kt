package com.rafiul.buzzbulletin.screens.home


import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    LazyColumn {
        items(items = viewmodel.userViewState.value.data.articles ?: emptyList()) { article ->
            Log.d("My Response","$article")
            if (article != null) {
                article.description?.let { Text(text = it) }
            }
        }
    }
}

