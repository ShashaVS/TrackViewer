package com.shashavs.trackviewer.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shashavs.trackviewer.data.room.converters.Converters
import com.shashavs.trackviewer.data.room.dao.TrackDao
import com.shashavs.trackviewer.data.room.entity.TrackEntity
import androidx.room.Database as DatabaseAnnotation

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "trackviewer.db"

@DatabaseAnnotation(
        version = DATABASE_VERSION,
        exportSchema = true,
        entities = [
            TrackEntity::class,
        ]
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}

class DatabaseFactory(private val ctx: Context) {
    fun getInstance() = Room.databaseBuilder(ctx, Database::class.java, DATABASE_NAME)
            .build()
}