package com.shashavs.trackviewer.domain.usecases

import android.content.Context
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.domain.repository.TrackRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SaveTrack @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val trackRepository: TrackRepository
) {
    suspend fun invoke(track: Track) = withContext(Dispatchers.IO) {
         runCatching {
             trackRepository.saveTrack(track)
        }.onFailure {
            Timber.e("SaveTrack error", it)
        }
    }
}