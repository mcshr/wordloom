package com.mcshr.wordloom.domain.entities

data class Dictionary(
    val id:Long,
    val name:String,
    val description:String?,
    val imagePath: String?,
    val isSelected: Boolean,
    val creationDateTime: Long?,
    val languageOriginal: Language,
    val languageTranslation: Language
)
