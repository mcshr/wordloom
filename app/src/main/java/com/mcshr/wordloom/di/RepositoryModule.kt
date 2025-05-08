package com.mcshr.wordloom.di

import com.mcshr.wordloom.data.repository.AppSettingsRepositoryImpl
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.data.repository.LanguageRepositoryImpl
import com.mcshr.wordloom.data.repository.PrepopulateDataRepositoryImpl
import com.mcshr.wordloom.data.repository.WordCardRepositoryImpl
import com.mcshr.wordloom.domain.repository.AppSettingsRepository
import com.mcshr.wordloom.domain.repository.DictionaryRepository
import com.mcshr.wordloom.domain.repository.LanguageRepository
import com.mcshr.wordloom.domain.repository.PrepopulateDataRepository
import com.mcshr.wordloom.domain.repository.WordCardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindAppSettings(
        impl: AppSettingsRepositoryImpl
    ): AppSettingsRepository

    @Binds
    fun bindDictionary(
        impl: DictionaryRepositoryImpl
    ): DictionaryRepository

    @Binds
    fun bindPrepopulateData(
        impl: PrepopulateDataRepositoryImpl
    ): PrepopulateDataRepository

    @Binds
    fun bindWordCard(
        impl: WordCardRepositoryImpl
    ): WordCardRepository

    @Binds
    fun bindLanguage(
        impl: LanguageRepositoryImpl
    ): LanguageRepository
}