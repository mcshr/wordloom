package com.mcshr.wordloom.domain.interactors.prepopulateData

import android.util.Log
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.PrepopulateDataRepository
import javax.inject.Inject

class PrepopulateLanguagesUseCase @Inject constructor(
    private val repository: PrepopulateDataRepository
) {
    suspend operator fun invoke() {

        if (repository.hasLanguages()) {
            Log.d("DATA","Languages already prepopulated")
            return
        }

        val languages = listOf(
            Language("en", "English", 0),
            Language("ru", "Russian", 0),
            Language("ua", "Ukrainian", 0)
        )
        repository.prepopulateLanguages(languages)
    }
}