package com.mcshr.wordloom.presentation.createWordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.PartOfSpeech
import com.mcshr.wordloom.domain.entities.UsageExample
import com.mcshr.wordloom.domain.interactors.wordCard.CreateWordCardUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.SaveWordCardToDictionaryUseCase
import com.mcshr.wordloom.domain.wrappers.DataOperationState
import com.mcshr.wordloom.domain.wrappers.errors.WordCardCreationFailure
import com.mcshr.wordloom.presentation.utils.uiModels.UsageExampleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWordViewModel @Inject constructor(
    private val createWordCardUseCase: CreateWordCardUseCase,
    private val saveWordCardToDictionaryUseCase: SaveWordCardToDictionaryUseCase
) : ViewModel() {

    private val _meaningList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val meaningList: LiveData<List<String>>
        get() = _meaningList

    private val _examplesList: MutableLiveData<List<UsageExampleUiModel>> =
        MutableLiveData(emptyList())
    val examplesList: LiveData<List<UsageExampleUiModel>>
        get() = _examplesList

    private val _saveAndClose = MutableLiveData<Boolean>()
    val saveAndClose: LiveData<Boolean>
        get() = _saveAndClose

    private var exampleIdCounter = 0

    fun addMeaning(meaning: String): Boolean {
        if (_meaningList.value?.contains(meaning) == false) {
            _meaningList.value = _meaningList.value?.plus(meaning)
            return true
        }
        return false
    }

    fun deleteMeaning(meaning: String): String {
        _meaningList.value = _meaningList.value?.filterNot { it == meaning }
        return meaning
    }

    fun addExample() {
        val currentList = _examplesList.value.orEmpty()
        _examplesList.value = currentList + UsageExampleUiModel(
            id = exampleIdCounter++,
            position = currentList.size + 1
        )
    }

    fun deleteExample(usageExampleUiModel: UsageExampleUiModel) {
        val currentList = _examplesList.value.orEmpty()
        _examplesList.value = currentList
            .filterNot {
                it.id == usageExampleUiModel.id
            }.mapIndexed { index, it -> //update position
                it.copy(position = index + 1)
            }
    }

    fun updateExample(example: UsageExampleUiModel, newText: String) {
        _examplesList.value?.find { it.id == example.id }?.text = newText
    }

    fun updateTranslation(example: UsageExampleUiModel, newText: String) {
        _examplesList.value?.find { it.id == example.id }?.translation = newText
    }

    private fun getUsageExamplesFromLiveData(): List<UsageExample> {
        return examplesList.value?.filterNot {
            it.text.isBlank()
        }?.map {
            UsageExample(
                text = it.text.trim(),
                translation = if (it.translation.isNotBlank()) {
                    it.translation.trim()
                } else {
                    null
                }
            )
        } ?: emptyList()
    }

    fun createWordCardInDictionary(
        wordText: String,
        wordMeaningList: List<String>,
        dict: Dictionary
    ) {
        viewModelScope.launch {
            val result = createWordCardUseCase(
                word = wordText,
                translations = wordMeaningList,
                partOfSpeech = PartOfSpeech.EMPTY,
                imagePath = null,
                languageOriginal = dict.languageOriginal,
                languageTranslation = dict.languageTranslation,
                usageExamples = getUsageExamplesFromLiveData()
            )
            when (result) {
                is DataOperationState.Success<Long> -> {
                    saveWordCardToDictionaryUseCase(dict.id, result.data)
                    _saveAndClose.postValue(true)
                }

                is DataOperationState.Failure<WordCardCreationFailure> -> {
                    handleError(result.errorData)
                    _saveAndClose.postValue(false)
                }
            }
        }


    }

    private fun handleError(error: WordCardCreationFailure) {
        /*
        TODO Show the error to the user
         and suggest editing the existing word
         or copying it if it is in another dictionary.
        */
    }

}