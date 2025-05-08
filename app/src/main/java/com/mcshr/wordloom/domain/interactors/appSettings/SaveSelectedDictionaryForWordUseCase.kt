package com.mcshr.wordloom.domain.interactors.appSettings

import com.mcshr.wordloom.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SaveSelectedDictionaryForWordUseCase @Inject constructor(
    private val settingsRepository: AppSettingsRepository
) {
    operator fun invoke(dictionaryId: Long) {
        settingsRepository.saveSelectedDictionaryIdForWord(dictionaryId)
    }
}