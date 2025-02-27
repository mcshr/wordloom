package com.mcshr.wordloom.presentation.dictionaryScreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.data.repository.WordCardRepositoryImpl
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.interactors.dictionary.GetDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetWordCardListByDictIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DictionaryViewModel(
    application: Application,
    private val dictionaryId: Long
) : ViewModel() {
    private val dictRepository = DictionaryRepositoryImpl(application)
    private val wordCardRepository = WordCardRepositoryImpl(application)
    private val getDictionaryUseCase = GetDictionaryUseCase(dictRepository)
    private val getWordCardListByDictIdUseCase = GetWordCardListByDictIdUseCase(wordCardRepository)

    private val _dictionary = MutableLiveData<Dictionary>()
    val dictionaryLiveData: LiveData<Dictionary>
        get() = _dictionary


    val wordList = getWordCardListByDictIdUseCase(dictionaryId)


    init {
        viewModelScope.launch {
            val dictionary = withContext(Dispatchers.IO) {
                getDictionaryUseCase(dictionaryId)
            }
            _dictionary.value = dictionary
        }

    }


    companion object {
        fun provideFactory(
            application: Application,
            dictionaryId: Long
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(DictionaryViewModel::class.java))
                        return DictionaryViewModel(application, dictionaryId) as T
                    else throw RuntimeException("Unknown view model $modelClass")
                }
            }
        }
    }
}