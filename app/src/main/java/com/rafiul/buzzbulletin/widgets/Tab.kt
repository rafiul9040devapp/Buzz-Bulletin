package com.rafiul.buzzbulletin.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Flare
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.SportsBasketball
import androidx.compose.ui.graphics.vector.ImageVector


data class TabItem(
    val title:String,
    val selectedIcon:ImageVector,
    val unSelectedIcon:ImageVector
)

val tabItemList = listOf<TabItem>(
    TabItem(
        title =  "General",
        selectedIcon = Icons.Filled.Info,
        unSelectedIcon = Icons.Outlined.Info
    ),

    TabItem(
        title = "Business",
        selectedIcon = Icons.Filled.Business,
        unSelectedIcon = Icons.Outlined.Business
    ),

    TabItem(
        title = "Health",
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.Favorite
    ),

    TabItem(
        title = "Science",
        selectedIcon = Icons.Filled.Flare,
        unSelectedIcon = Icons.Outlined.Flare
    ),

    TabItem(
        title = "Sports",
        selectedIcon = Icons.Filled.SportsBasketball,
        unSelectedIcon = Icons.Outlined.SportsBasketball
    ),

    TabItem(
        title = "Technology",
        selectedIcon = Icons.Filled.Code,
        unSelectedIcon = Icons.Outlined.Code
    ),

    TabItem(
        title = "Entertainment",
        selectedIcon = Icons.Filled.Movie,
        unSelectedIcon = Icons.Outlined.Movie
    ),

)


val categories = listOf(
    "General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment"
)
