package com.shashavs.trackviewer.ui.main

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.domain.usecases.ParseGPX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val parseGPX: ParseGPX
) : ViewModel()   {

    val tracks = mutableStateListOf<Track>()
    val currentTrack = mutableStateOf(value = Track(id = ""))

    init {
    }

    fun addFileUri(uri: Uri) {
        viewModelScope.launch {
            parseGPX.invoke(uri)?.let {
                currentTrack.value = it
                tracks.add(it)
            }
        }
    }

}