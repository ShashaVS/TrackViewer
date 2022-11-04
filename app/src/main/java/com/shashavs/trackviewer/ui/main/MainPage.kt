package com.shashavs.trackviewer.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.shashavs.trackviewer.R
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
    val context = LocalContext.current
    val tracks = viewModel.tracksFlow.collectAsState(initial = emptyList())
    val currentTrack = viewModel.currentTrack
    val darkTheme = viewModel.darkTheme

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
        }, context.getString(R.string.share_track_with))
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            style = MaterialTheme.typography.h5,
                            text = stringResource(id = R.string.app_name)
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        IconButton(onClick = {
                            viewModel.changeDayNightMode()
                        }) {
                            Icon(
                                imageVector = if(darkTheme.value) Icons.Filled.Nightlight else Icons.Filled.WbSunny,
                                contentDescription = stringResource(id = R.string.change_day_night_mode)
                            )
                        }
                    }
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
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                        context, if(darkTheme.value) R.raw.map_style_dark else R.raw.map_style_light
                    )
                ),
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
                    color = MaterialTheme.colors.secondary
                )
                Marker(
                    state = MarkerState(position = currentTrack.value.points.first()),
                    icon = context.markerIcon(R.drawable.ic_location_on, MaterialTheme.colors.primary)
                )
                Marker(
                    state = MarkerState(position = currentTrack.value.points.last()),
                    icon = context.markerIcon(R.drawable.ic_location_on, MaterialTheme.colors.error)
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
                        text = stringResource(id = R.string.tracks),
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
                                contentDescription = stringResource(id = R.string.share_track)
                            )
                        }
                        IconButton(onClick = { viewModel.deleteCurrentTrack() }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(id = R.string.delete_track)
                            )
                        }
                    }
                    IconButton(onClick = { openFile() }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.add_new_track)
                        )
                    }
                }
                Divider()
                LazyColumn(
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
                                        color = MaterialTheme.colors.secondary,
                                        text = track.name ?: stringResource(id = R.string.unknown)
                                    )
                                    Text(
                                        style = MaterialTheme.typography.caption,
                                        color = MaterialTheme.colors.secondary,
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
                                        tint = MaterialTheme.colors.primary,
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