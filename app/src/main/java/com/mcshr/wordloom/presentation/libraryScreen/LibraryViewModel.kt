package com.mcshr.wordloom.presentation.libraryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesUseCase

class LibraryViewModel(application: Application):AndroidViewModel(application) {
    val repository = DictionaryRepositoryImpl(application)
    private val getAllDictionariesUseCase = GetAllDictionariesUseCase(repository)

    val allDictionaries:LiveData<List<Dictionary>> = getAllDictionariesUseCase()

}