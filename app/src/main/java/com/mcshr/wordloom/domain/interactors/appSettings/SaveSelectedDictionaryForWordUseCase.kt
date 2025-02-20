package com.mcshr.wordloom.domain.interactors.appSettings

import com.mcshr.wordloom.domain.repository.AppSettingsRepository

class SaveSelectedDictionaryForWordUseCase(
    private val settingsRepository: AppSettingsRepository
) {
    operator fun invoke(dictionaryId: Long){
        settingsRepository.saveSelectedDictionaryIdForWord(dictionaryId)
    }
}