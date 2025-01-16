package com.mcshr.wordloom.presentation.editDictionaryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.WordloomRepositoryImpl
import com.mcshr.wordloom.domain.interactors.dictionary.CreateDictionaryUseCase
import kotlinx.coroutines.launch

class EditDictionaryViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordloomRepositoryImpl(application)
    private val createDictionaryUseCase = CreateDictionaryUseCase(repository)

    //TODO validation, ready to close
    fun createDictionary(name: String){
        if (name.isNotEmpty())
            viewModelScope.launch {
                createDictionaryUseCase(name, null, null)
            }
    }
}