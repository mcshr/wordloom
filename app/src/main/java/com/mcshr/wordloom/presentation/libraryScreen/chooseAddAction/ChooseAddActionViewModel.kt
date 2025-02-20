package com.mcshr.wordloom.presentation.libraryScreen.chooseAddAction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.interactors.dictionary.CheckIfAnyDictionaryExistsUseCase
import kotlinx.coroutines.launch

class ChooseAddActionViewModel(application: Application):AndroidViewModel(application) {
    private val repository = DictionaryRepositoryImpl(application)
    private val checkIfAnyDictionaryExists = CheckIfAnyDictionaryExistsUseCase(repository)

    private val _isAnyDictExists = MutableLiveData<Boolean>()
    val isAnyDictionaryExists: LiveData<Boolean>
        get() = _isAnyDictExists

    init {
        viewModelScope.launch {
            _isAnyDictExists.value = checkIfAnyDictionaryExists.invoke()
        }
    }
}