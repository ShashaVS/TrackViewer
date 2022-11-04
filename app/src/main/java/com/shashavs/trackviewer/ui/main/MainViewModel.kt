package com.shashavs.trackviewer.ui.main

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTracks: GetTracks,
    private val parseGPX: ParseGPX,
    private val saveTrack: SaveTrack,
    private val deleteTrack: DeleteTrack,
    private val getShareLink: GetShareLink
) : ViewModel() {

    val tracksFlow = getTracks.invoke()
    val currentTrack = mutableStateOf(value = Track())

    fun addFileUri(uri: Uri) {
        viewModelScope.launch {
            parseGPX.invoke(uri)?.let {
                currentTrack.value = it
                saveTrack.invoke(it)
            }
        }
    }

    fun selectTrack(track: Track) {
        when(currentTrack.value.id == track.id) {
            true -> currentTrack.value = Track()
            false -> currentTrack.value = track
        }
    }

    fun deleteCurrentTrack() {
       viewModelScope.launch {
           deleteTrack.invoke(currentTrack.value)
           currentTrack.value = Track()
       }
    }

    fun getShareLink(): String {
        return getShareLink.invoke(currentTrack.value)
    }
}