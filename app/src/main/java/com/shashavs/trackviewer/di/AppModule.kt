package com.shashavs.trackviewer.di

import android.content.Context
import com.shashavs.trackviewer.data.preferences.AppSharedPrefs
import com.shashavs.trackviewer.data.repositories.TrackRepository
import com.shashavs.trackviewer.data.repositories.impl.TrackRepositoryImpl
import com.shashavs.trackviewer.data.room.Database
import com.shashavs.trackviewer.data.room.DatabaseFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabaseFactory(@ApplicationContext ctx: Context): DatabaseFactory =
            DatabaseFactory(ctx)

    @Provides
    @Singleton
    fun provideDatabase(factory: DatabaseFactory): Database = factory.getInstance()

    @Provides
    @Singleton
    fun provideTrackRepository(impl: TrackRepositoryImpl): TrackRepository = impl

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext ctx: Context): AppSharedPrefs = AppSharedPrefs(ctx)
}