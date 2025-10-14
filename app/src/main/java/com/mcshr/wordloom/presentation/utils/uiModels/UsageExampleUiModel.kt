package com.mcshr.wordloom.presentation.utils.uiModels

data class UsageExampleUiModel(
    val id: Long = System.nanoTime(),
    var text: String = "",
    var translation: String = "",
)
