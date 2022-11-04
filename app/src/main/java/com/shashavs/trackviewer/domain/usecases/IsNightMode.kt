package com.shashavs.trackviewer.domain.usecases

import com.shashavs.trackviewer.data.preferences.AppSharedPrefs
import com.shashavs.trackviewer.data.preferences.AppSharedPrefs.Companion.IS_NIGHT_THEME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsNightMode @Inject constructor(
    private val appSharedPrefs: AppSharedPrefs
) {
    suspend fun invoke() = withContext(Dispatchers.IO) {
        appSharedPrefs.getBoolean(key = IS_NIGHT_THEME, default = true)
    }
}