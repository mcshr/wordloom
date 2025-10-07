package com.mcshr.wordloom.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dictionary(
    val id:Long,
    val name:String,
    val description:String?,
    val imagePath: String?,
    val isSelected: Boolean,
    val creationDateTime: Long?,
    val languageOriginal: Language,
    val languageTranslation: Language
): Parcelable
