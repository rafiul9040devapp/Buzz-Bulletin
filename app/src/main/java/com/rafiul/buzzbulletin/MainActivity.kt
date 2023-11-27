package com.rafiul.buzzbulletin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rafiul.buzzbulletin.navigation.NewsNavigation
import com.rafiul.buzzbulletin.ui.theme.BuzzBulletinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                NewsNavigation()
            }
        }
    }
}


@Composable
fun MyApp(content: @Composable () -> Unit) {
    BuzzBulletinTheme {
        content()
    }
}
