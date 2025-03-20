package com.mcshr.wordloom.presentation.libraryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.entities.DictionaryWithStats
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesWithStatsUseCase

class LibraryViewModel(application: Application):AndroidViewModel(application) {
    val repository = DictionaryRepositoryImpl(application)
    private val getAllDictionariesWithStatsUseCase = GetAllDictionariesWithStatsUseCase(repository)

    val allDictionariesWithStats:LiveData<List<DictionaryWithStats>> = getAllDictionariesWithStatsUseCase()

}