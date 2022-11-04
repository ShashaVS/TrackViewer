package com.shashavs.trackviewer.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*
import com.shashavs.trackviewer.R
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.ui.getLatLngBounds
import com.shashavs.trackviewer.ui.markerIcon
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val tracks = viewModel.tracksFlow.collectAsState(initial = emptyList())
    val currentTrack = viewModel.currentTrack

    val openFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult
            result.data?.data?.let {
                viewModel.addFileUri(it)
            }
        }

    val shareLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Timber.d("MainPage shareLauncher result: $result")
        }

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val cameraPositionState = rememberCameraPositionState {}

    fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        openFileLauncher.launch(intent)
    }

    fun shareCurrentTrack(link: String) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/*"
            putExtra(Intent.EXTRA_TEXT, link)
            putExtra(Intent.EXTRA_TITLE, currentTrack.value.name)
        }, "Share track with")
        shareLauncher.launch(share)
    }

    suspend fun bottomSheetClick() {
        when (scaffoldState.bottomSheetState.isCollapsed) {
            true -> scaffoldState.bottomSheetState.expand()
            false -> scaffoldState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                title = {
                    Text(
                        style = MaterialTheme.typography.h5,
                        text = stringResource(id = R.string.app_name)
                    )
                }
            )
        },
        content = {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false
                ),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                },
            ) {
                if (currentTrack.value.points.isEmpty()) return@GoogleMap
                val latLngBounds = currentTrack.value.getLatLngBounds()
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(latLngBounds.center, 11.0f)
                MapProperties(
                    latLngBoundsForCameraTarget = latLngBounds
                )
                Polyline(
                    points = currentTrack.value.points,
                    color = MaterialTheme.colors.background
                )
                Marker(
                    state = MarkerState(position = currentTrack.value.points.first()),
                    icon = LocalContext.current.markerIcon(R.drawable.ic_location_on, MaterialTheme.colors.error)
                )
                Marker(
                    state = MarkerState(position = currentTrack.value.points.last()),
                    icon = LocalContext.current.markerIcon(R.drawable.ic_location_on, MaterialTheme.colors.secondary)
                )
            }
        },
        sheetPeekHeight = 48.dp,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            Column {
                Row(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            bottomSheetClick()
                        }
                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        style = MaterialTheme.typography.h6,
                        text = "Tracks",
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    if (!currentTrack.value.isEmpty()) {
                        IconButton(
                            onClick = {
                                val link = viewModel.getShareLink()
                                shareCurrentTrack(link)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Share track"
                            )
                        }
                        IconButton(onClick = { viewModel.deleteCurrentTrack() }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete track"
                            )
                        }
                    }
                    IconButton(onClick = { openFile() }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new track")
                    }
                }
                Divider()
                LazyColumn(
                    modifier = Modifier.padding(vertical = 8.dp),
                    content = {
                        items(tracks.value) { track ->
                            TextButton(
                                onClick = { viewModel.selectTrack(track) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(start = 16.dp),
                                ) {
                                    Text(
                                        style = MaterialTheme.typography.body2,
                                        text = track.name ?: "Unknown"
                                    )
                                    Text(
                                        style = MaterialTheme.typography.caption,
                                        text = "${track.startTime?.toString("h:mm a")} - ${
                                            track.endTime?.toString(
                                                "h:mm a"
                                            )
                                        }"
                                    )
                                }
                                Spacer(Modifier.weight(1.0f))
                                if (track.id == currentTrack.value.id) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        tint = Color.Blue,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    })
            }
        }
    )
}