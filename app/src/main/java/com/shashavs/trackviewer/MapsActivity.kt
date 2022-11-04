package com.shashavs.trackviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.shashavs.trackviewer.ui.theme.AppTheme
import com.shashavs.trackviewer.ui.main.MainPage
import com.shashavs.trackviewer.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = viewModel.darkTheme
            AppTheme(
                darkTheme = darkTheme.value
            ) {
                MainPage()
            }
        }
    }

}