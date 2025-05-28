package com.mcshr.wordloom.presentation.learningScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.interactors.appSettings.GetSessionWordLimitUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetLearningSetUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.UpdateWordCardStatusUseCase
import com.yuyakaido.android.cardstackview.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val getLearningSetUseCase: GetLearningSetUseCase,
    private val updateWordCardStatusUseCase: UpdateWordCardStatusUseCase,
    getSessionWordLimitUseCase: GetSessionWordLimitUseCase,
) : ViewModel() {

    private val _learningSet =  MutableLiveData<List<WordCard>>()
    val learningSet : LiveData<List<WordCard>>
        get() = _learningSet

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val wordLimit = getSessionWordLimitUseCase()
            _learningSet.postValue(getLearningSetUseCase(wordLimit))
        }
    }

    fun onCardSwipe(card: WordCard, direction: Direction){
        val newList = mutableListOf<WordCard>()
        _learningSet.value?.let {
            newList.addAll(it)
            //newList.removeAt(0)
            val isPositiveAction = direction == Direction.Right
            viewModelScope.launch(Dispatchers.IO) {
                val moveCardToEnd = updateWordCardStatusUseCase.invoke(card, isPositiveAction)
                if (moveCardToEnd){
                    newList.add(card)
                }
                _learningSet.postValue(newList)
            }
        }
    }
}