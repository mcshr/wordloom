package com.mcshr.wordloom.presentation.learningScreen

import android.util.Log
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val getLearningSetUseCase: GetLearningSetUseCase,
    private val updateWordCardStatusUseCase: UpdateWordCardStatusUseCase,
    getSessionWordLimitUseCase: GetSessionWordLimitUseCase,
) : ViewModel() {

    var position = 0
        private set

    private val _learningSet = MutableLiveData<List<WordCard>>()
    val learningSet: LiveData<List<WordCard>>
        get() = _learningSet

    private val _readyToClose = MutableLiveData<Boolean>()
    val readyToClose: LiveData<Boolean>
        get() = _readyToClose

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val wordLimit = getSessionWordLimitUseCase()
            _learningSet.postValue(getLearningSetUseCase(wordLimit))
        }
    }

    fun onCardSwipe(direction: Direction) {
        val updatedList = learningSet.value?.toMutableList()
        val card: WordCard = updatedList?.getOrNull(position)?:return
        Log.d("LEARNING", "p:$position  $card")
        val isPositiveAction = direction == Direction.Right
        viewModelScope.launch{
            val (updatedCard, moveCardToEnd) = withContext(Dispatchers.IO) {
                updateWordCardStatusUseCase.invoke(card, isPositiveAction)
            }
            if (moveCardToEnd) {
                updatedList.add(updatedCard)
            }
            position += 1
            Log.d("LEARNING_SET", "p+1:$position  $updatedList")
            _learningSet.value = updatedList.toList()
            if (position >= updatedList.size) {
                _readyToClose.value = true
            }
        }
    }
}