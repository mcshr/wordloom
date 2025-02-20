package com.mcshr.wordloom.presentation.editWordScreen.selectDictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesUseCase

class SelectDictionaryViewModel(application: Application) : AndroidViewModel(application) {
    private val dictRepository = DictionaryRepositoryImpl(application)
    private val getAllDictionariesUseCase = GetAllDictionariesUseCase(dictRepository)

    val allDictionaries = getAllDictionariesUseCase()
}