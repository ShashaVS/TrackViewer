package com.shashavs.trackviewer.ui.main

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.domain.usecases.GetTracks
import com.shashavs.trackviewer.domain.usecases.ParseGPX
import com.shashavs.trackviewer.domain.usecases.SaveTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val parseGPX: ParseGPX,
    private val saveTrack: SaveTrack,
    private val getTracks: GetTracks
) : ViewModel()   {

    val tracksFlow = getTracks.invoke()
    val currentTrack = mutableStateOf(value = Track(id = ""))

    fun addFileUri(uri: Uri) {
        viewModelScope.launch {
            parseGPX.invoke(uri)?.let {
                currentTrack.value = it
                saveTrack.invoke(it)
            }
        }
    }
}