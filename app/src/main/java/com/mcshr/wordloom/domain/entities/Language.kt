package com.mcshr.wordloom.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    val code: String,
    val name: String,
    val id: Long
): Parcelable
