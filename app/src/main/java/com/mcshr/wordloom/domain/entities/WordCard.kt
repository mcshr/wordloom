package com.mcshr.wordloom.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordCard(
    val wordText:String,
    val wordTranslations: List<String>,
    val partOfSpeech: PartOfSpeech,
    val status: WordStatus,
    val languageOriginal: Language,
    val languageTranslation: Language,
    val reviewCount:Int,
    val nextReviewTime: Long?, //Unix timestamp
    val imagePath: String?,
    val id: Long
) : Parcelable
{
    companion object{
        const val MAX_REVIEW_COUNT = 8
    }
}
