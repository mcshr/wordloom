package com.mcshr.wordloom.domain.entities

data class Dictionary(
    val name:String,
    val description:String?,
    val icon:String,
    val wordCards: List<WordCard>?
)
