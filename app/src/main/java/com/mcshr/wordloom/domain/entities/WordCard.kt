package com.mcshr.wordloom.domain.entities

import java.time.LocalDateTime

data class WordCard(
    val wordText:String,
    val wordTranslation:String,
    val language: String,
    val partOfSpeech: String?,
    val status: WordStatus,
    val reviewCount:Int,
    val nextReviewTime: LocalDateTime?,
    val definitions:List<String>?,
    val belongsToDictionaries: List<Dictionary>
//    val usageExamples:List<UsageExample>,
//    val picture: String?
){
    companion object{
        const val MAX_REVIEW_COUNT = 9
    }
}
