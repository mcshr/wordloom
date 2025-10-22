package com.mcshr.wordloom.presentation.createEditWordScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.PartOfSpeech
import com.mcshr.wordloom.domain.entities.UsageExample
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.interactors.wordCard.CreateWordCardUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.EditWordCardUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetWordCardByIdUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.SaveWordCardToDictionaryUseCase
import com.mcshr.wordloom.domain.wrappers.DataOperationState
import com.mcshr.wordloom.domain.wrappers.errors.WordCardOperationFailure
import com.mcshr.wordloom.presentation.utils.uiModels.UsageExampleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditWordViewModel @Inject constructor(
    private val createWordCardUseCase: CreateWordCardUseCase,
    private val saveWordCardToDictionaryUseCase: SaveWordCardToDictionaryUseCase,
    private val getWordCardByIdUseCase: GetWordCardByIdUseCase,
    private val editWordCardUseCase: EditWordCardUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val wordCardId: Long = savedStateHandle["wordCardId"] ?: -1L

    val isCreationMode = wordCardId == -1L

    private val _oldWordCard = MutableLiveData<WordCard>()
    val oldWordCard: LiveData<WordCard>
        get() = _oldWordCard

    private val _translationList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val translationList: LiveData<List<String>>
        get() = _translationList

    private val _examplesList: MutableLiveData<List<UsageExampleUiModel>> =
        MutableLiveData(emptyList())
    val examplesList: LiveData<List<UsageExampleUiModel>>
        get() = _examplesList

    private val _saveAndClose = MutableLiveData<Boolean>()
    val saveAndClose: LiveData<Boolean>
        get() = _saveAndClose

    private var exampleIdCounter = 0


    init {
        if (!isCreationMode){
            setupFromOldWordCard()
        }
    }

    private fun setupFromOldWordCard() {
        viewModelScope.launch {
            val wordCard = getWordCardByIdUseCase(wordCardId)
            _oldWordCard.value = wordCard
            wordCard.wordTranslations.forEach {
                addTranslation(it)
            }
            wordCard.usageExamples.forEach {
                val currentList = _examplesList.value.orEmpty()
                _examplesList.value = currentList + UsageExampleUiModel(
                    id = exampleIdCounter++,
                    text = it.text,
                    translation = it.translation.orEmpty(),
                    position = currentList.size + 1
                )
            }
        }
    }


    fun addTranslation(translation: String): Boolean {
        if (_translationList.value?.contains(translation) == false) {
            _translationList.value = _translationList.value?.plus(translation)
            return true
        }
        return false
    }

    fun deleteTranslation(translation: String): String {
        _translationList.value = _translationList.value?.filterNot { it == translation }
        return translation
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

    fun saveWordCard(
        wordText: String,
        wordTranslationList: List<String>,
        dict: Dictionary,
        pos: PartOfSpeech
    ) {
        viewModelScope.launch {
            if (isCreationMode) {
                createWordCardAndSaveToDict(wordText, wordTranslationList, pos, dict)
            } else {
                editWordCard(wordText, wordTranslationList, pos)
            }
        }
    }

    private suspend fun createWordCardAndSaveToDict(
        wordText: String,
        wordMeaningList: List<String>,
        pos: PartOfSpeech,
        dict: Dictionary
    ) {
        val result = createWordCardUseCase(
            word = wordText,
            translations = wordMeaningList,
            partOfSpeech = pos,
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

            is DataOperationState.Failure<WordCardOperationFailure> -> {
                handleError(result.errorData)
                _saveAndClose.postValue(false)
            }
        }
    }


    private suspend fun editWordCard(
        wordText: String,
        wordTranslationList: List<String>,
        pos: PartOfSpeech
    ) {
        val oldWordCard = oldWordCard.value ?: return
        val newWordCard = oldWordCard.copy(
            wordText = wordText,
            wordTranslations = wordTranslationList,
            partOfSpeech = pos,
            usageExamples = getUsageExamplesFromLiveData()
        )
        Log.d("EDIT", oldWordCard.toString())
        Log.d("EDIT", newWordCard.toString())
        val result = editWordCardUseCase(
            oldWordCard,
            newWordCard
        )
        when (result) {
            is DataOperationState.Success -> {
                _saveAndClose.postValue(true)
            }

            is DataOperationState.Failure<WordCardOperationFailure> -> {
                handleError(result.errorData)
                _saveAndClose.postValue(false)
            }
        }
    }

    private fun handleError(error: WordCardOperationFailure) {
        /*
        TODO Show the error to the user
         and suggest editing the existing word
         or copying it if it is in another dictionary.
        */
    }
}