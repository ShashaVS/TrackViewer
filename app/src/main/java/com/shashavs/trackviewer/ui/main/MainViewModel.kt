package com.shashavs.trackviewer.ui.main

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.domain.usecases.ParseGPX
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val parseGPX: ParseGPX
) : ViewModel()   {

    val tracks = mutableStateListOf<Track>()

    init {
        // TEST
        for (i in 0..10) {
            tracks.add(
                Track(id = "$i", name = "name $i")
            )
        }
    }

    fun addFileUri(uri: Uri) {
        Timber.i("MainViewModel addFileUri uri: $uri")
        parseGPX.invoke(uri)
    }

}