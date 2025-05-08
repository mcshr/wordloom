package com.mcshr.wordloom.di

import android.content.Context
import androidx.room.Room
import com.mcshr.wordloom.data.AppSettingsSharedPreferences
import com.mcshr.wordloom.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "wordloom_db"
        ).fallbackToDestructiveMigration(false) // TODO REMOVE
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(
        @ApplicationContext context: Context
    ): AppSettingsSharedPreferences {
        return AppSettingsSharedPreferences(context)
    }
}
