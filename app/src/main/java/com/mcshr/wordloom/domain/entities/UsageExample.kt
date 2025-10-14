package com.mcshr.wordloom.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsageExample(
    val text: String,
    val translation: String? = null
): Parcelable
