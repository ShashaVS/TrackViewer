package com.shashavs.trackviewer.data.entities

import java.lang.System

data class Track(
    val id: String,
    val path: String? = null,
    val raw: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
