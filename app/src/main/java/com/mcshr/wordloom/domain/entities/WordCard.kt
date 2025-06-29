package com.mcshr.wordloom.domain.entities

data class WordCard(
    val wordText:String,
    val wordTranslations: List<String>,
    val partOfSpeech: String?,
    val status: WordStatus,
    val languageOriginal: Language,
    val languageTranslation: Language,
    val reviewCount:Int,
    val nextReviewTime: Long?, //Unix timestamp
    val imagePath: String?,
    val id: Long,
    val wordId:Long
//    val definitions:List<String>?,
//    val belongsToDictionaries: List<Dictionary>,
//    val usageExamples:List<UsageExample>,
){
    companion object{
        const val MAX_REVIEW_COUNT = 8
    }
}
