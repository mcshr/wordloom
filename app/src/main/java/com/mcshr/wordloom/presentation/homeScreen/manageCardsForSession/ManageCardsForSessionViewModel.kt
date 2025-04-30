package com.mcshr.wordloom.presentation.homeScreen.manageCardsForSession

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.data.repository.WordCardRepositoryImpl
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.interactors.dictionary.GetDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetWordCardListByDictIdUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.ToggleReadyToLearnStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManageCardsForSessionViewModel(
     val dictionaryId: Long,
     application: Application
) : AndroidViewModel(application) {

    private val wordCardRepository = WordCardRepositoryImpl(application)
    private val dictRepository = DictionaryRepositoryImpl(application)
    private val getWordCardListByDictIdUseCase = GetWordCardListByDictIdUseCase(wordCardRepository)
    private val _toggleReadyToLearnStateUseCase = ToggleReadyToLearnStateUseCase(wordCardRepository)
    private val getDictionaryUseCase = GetDictionaryUseCase(dictRepository)

    private val _dictionary = MutableLiveData<Dictionary>()
    val dictionaryLiveData: LiveData<Dictionary>
        get() = _dictionary



    init {
        viewModelScope.launch {
            val dictionary = withContext(Dispatchers.IO) {
                getDictionaryUseCase(dictionaryId)
            }
            _dictionary.value = dictionary
        }

    }

    fun toggleReadyToLearnState(wordCard:WordCard){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _toggleReadyToLearnStateUseCase.invoke(wordCard)
            }
        }
    }

    val wordCardList = getWordCardListByDictIdUseCase(dictionaryId).map{ list ->
        list.filter{ wordCard ->
            wordCard.status == WordStatus.UNKNOWN || wordCard.status == WordStatus.READY_TO_LEARN
        }.sortedBy { wordCard ->
            wordCard.status
        }
    }



    companion object {
        fun provideFactory(
            dictionaryId: Long,
            application: Application
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ManageCardsForSessionViewModel::class.java))
                        return ManageCardsForSessionViewModel(dictionaryId, application) as T
                    else throw RuntimeException("Unknown view model $modelClass")
                }
            }
        }
    }
}