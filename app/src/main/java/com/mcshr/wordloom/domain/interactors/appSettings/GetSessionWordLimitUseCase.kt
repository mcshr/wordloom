package com.mcshr.wordloom.domain.interactors.appSettings

import com.mcshr.wordloom.domain.repository.AppSettingsRepository
import javax.inject.Inject

class GetSessionWordLimitUseCase @Inject constructor(
    private val repository: AppSettingsRepository
) {
    operator fun invoke(): Int {
        return repository.getSessionWordLimit()
    }
}