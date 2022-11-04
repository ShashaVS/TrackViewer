package com.shashavs.trackviewer.data.preferences

import android.content.Context
import androidx.core.content.edit

class AppSharedPrefs(context: Context) {

    companion object {
        const val IS_NIGHT_THEME = "is_night_theme"
    }

    private val prefs = context.getSharedPreferences("track_viewer_prefs", Context.MODE_PRIVATE)

    fun setBoolean(key: String, enable: Boolean) {
        prefs.edit { putBoolean(key, enable) }
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }

}