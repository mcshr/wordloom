package com.mcshr.wordloom.presentation.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.interactors.dictionary.GetSelectedDictionariesWithStatsUseCase

class HomeViewModel(application: Application):AndroidViewModel(application) {
    private val dictionaryRepository = DictionaryRepositoryImpl(application)
    private val getSelectedDictionariesWithStatsUseCase = GetSelectedDictionariesWithStatsUseCase(dictionaryRepository)

    val selectedDictionaries = getSelectedDictionariesWithStatsUseCase()

}