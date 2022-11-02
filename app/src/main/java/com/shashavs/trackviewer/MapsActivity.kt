package com.shashavs.trackviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shashavs.trackviewer.ui.AppTheme
import com.shashavs.trackviewer.ui.main.MainPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme() {
                MainPage()
            }
        }
    }

}