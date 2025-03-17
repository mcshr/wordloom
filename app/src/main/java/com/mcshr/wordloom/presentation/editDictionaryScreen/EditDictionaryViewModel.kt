package com.mcshr.wordloom.presentation.editDictionaryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.interactors.dictionary.CreateDictionaryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDictionaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DictionaryRepositoryImpl(application)
    private val createDictionaryUseCase = CreateDictionaryUseCase(repository)

    private val _saveAndClose = MutableLiveData<Boolean>()
    val saveAndClose: LiveData<Boolean>
        get() = _saveAndClose

    fun createDictionary(name: String) {
        viewModelScope.launch {
            val isSuccess = withContext(Dispatchers.IO) {
                createDictionaryUseCase(
                    name,
                    null,
                    null,
                    Language("", "", 0),
                    Language("", "", 0)
                ) //TODO imgs
            }
            _saveAndClose.postValue(isSuccess)
        }
    }
}