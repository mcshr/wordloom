package com.mcshr.wordloom.presentation.libraryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.DictionaryWithStats
import com.mcshr.wordloom.domain.interactors.dictionary.DeleteDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesWithStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    getAllDictionariesWithStatsUseCase: GetAllDictionariesWithStatsUseCase,
    private val deleteDictionaryUseCase: DeleteDictionaryUseCase
) : ViewModel() {
    val allDictionariesWithStats: LiveData<List<DictionaryWithStats>> =
        getAllDictionariesWithStatsUseCase()

    fun deleteDictionary(dictionary: Dictionary){
        viewModelScope.launch {
            deleteDictionaryUseCase(dictionary)
        }
    }
}