package com.shashavs.trackviewer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashavs.trackviewer.data.repositories.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trackRepository: TrackRepository
) : ViewModel()   {

    init {
        viewModelScope.launch {

        }
    }

}