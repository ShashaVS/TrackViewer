package com.shashavs.trackviewer.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.shashavs.trackviewer.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val tracks = viewModel.tracks
    val openFileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult
        result.data?.data?.let {
            viewModel.addFileUri(it)
        }
    }

    fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        openFileLauncher.launch(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                }
            )
        },
        content = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .apply {
                            if(tracks.isEmpty()) {
                                blur(30.dp,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded)
                            }
                        },
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = false
                        ),
                        onMapLoaded = {

                        },
                    )
                if(tracks.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .apply {
                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                                    background(color = Color.White.copy(alpha = 0.8f))
                                }
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp),
                            text = "No any tracks"
                        )
                        Button(
                            onClick = { openFile() }
                        ) {
                            Text(
                                text = "Add New track",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {

        },
    )
}