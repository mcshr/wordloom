package com.mcshr.wordloom.domain.entities

data class WordCard(
    val wordText:String,
    val wordTranslations: List<String>,
    val language: Language,
    val partOfSpeech: String?,
    val status: WordStatus,
    val reviewCount:Int,
    val nextReviewTime: Long?, //Unix timestamp
    val picturePath: String?
//    val definitions:List<String>?,
//    val belongsToDictionaries: List<Dictionary>,
//    val usageExamples:List<UsageExample>,
){
    companion object{
        const val MAX_REVIEW_COUNT = 9
    }
}
