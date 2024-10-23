package com.mcshr.wordloom.domain.entities

data class WordCard(
    val wordText:String,
    val wordTranslation:String,
    val language: String,
    val partOfSpeech: String?,
    val status: WordStatus,
    val reviewCount:Int,
    val nextReviewTime: Long?, //Unix timestamp
    val definitions:List<String>?,
    val belongsToDictionaries: List<Dictionary>
//    val usageExamples:List<UsageExample>,
//    val picture: String?
){
    companion object{
        const val MAX_REVIEW_COUNT = 9
    }
}
