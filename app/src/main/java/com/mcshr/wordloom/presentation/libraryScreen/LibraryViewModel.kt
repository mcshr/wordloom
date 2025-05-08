package com.mcshr.wordloom.presentation.libraryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mcshr.wordloom.domain.entities.DictionaryWithStats
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesWithStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    getAllDictionariesWithStatsUseCase: GetAllDictionariesWithStatsUseCase
) : ViewModel() {
    val allDictionariesWithStats: LiveData<List<DictionaryWithStats>> =
        getAllDictionariesWithStatsUseCase()
}