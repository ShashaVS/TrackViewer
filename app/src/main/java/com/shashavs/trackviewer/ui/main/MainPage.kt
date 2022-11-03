package com.shashavs.trackviewer.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.shashavs.trackviewer.R

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        openFileLauncher.launch(intent)
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                }
            )
        },
        content = {
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
        },
        sheetPeekHeight = 52.dp,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.body1,
                        text = "Track name",
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Share, contentDescription = "Share track")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete track")
                    }
                    IconButton(onClick = { openFile() }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new track")
                    }
                }
                LazyColumn(content = {
                    items(tracks) { track ->
                        Text(
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            text = track.name ?: "Unknown"
                        )
                    }
                })
            }
        }
    )
}