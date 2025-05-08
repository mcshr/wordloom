package com.mcshr.wordloom.presentation.homeScreen.manageCardsForSession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.interactors.dictionary.GetDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetWordCardListByDictIdUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.ToggleReadyToLearnStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManageCardsForSessionViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    getWordCardListByDictIdUseCase: GetWordCardListByDictIdUseCase,
    private val getDictionaryUseCase: GetDictionaryUseCase,
    private val toggleReadyToLearnStateUseCase: ToggleReadyToLearnStateUseCase
) : ViewModel() {

    val dictionaryId: Long = savedStateHandle["dictionaryId"]
        ?: throw RuntimeException("dictionary id is null on ManageCardsForSessionViewModel")

    private val _dictionary = MutableLiveData<Dictionary>()
    val dictionaryLiveData: LiveData<Dictionary>
        get() = _dictionary


    val wordCardList = getWordCardListByDictIdUseCase(dictionaryId).map { list ->
        list.filter { wordCard ->
            wordCard.status == WordStatus.UNKNOWN || wordCard.status == WordStatus.READY_TO_LEARN
        }
//            .sortedBy { wordCard ->
//                wordCard.status
//            }
    }

    init {
        viewModelScope.launch {
            val dictionary = withContext(Dispatchers.IO) {
                getDictionaryUseCase(dictionaryId)
            }
            _dictionary.value = dictionary
        }

    }

    fun toggleReadyToLearnState(wordCard: WordCard) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                toggleReadyToLearnStateUseCase.invoke(wordCard)
            }
        }
    }
}