package com.mcshr.wordloom.presentation.utils

import android.content.Context
import com.mcshr.wordloom.R
import com.mcshr.wordloom.domain.entities.WordStatus

fun WordStatus.getColorId():Int{
    return when(this){
        WordStatus.UNKNOWN -> R.color.unknown_status
        WordStatus.KNOWN -> R.color.known_status
        WordStatus.READY_TO_LEARN -> R.color.readyToLearn_status
        WordStatus.LEARNING -> R.color.learning_status
        WordStatus.LEARNED -> R.color.learned_status
    }
}

fun WordStatus.getColor(context: Context):Int{
    return context.getColor(this.getColorId())
}

fun WordStatus.getText(context: Context):String{
    val resId = when(this){
        WordStatus.UNKNOWN -> R.string.unknown
        WordStatus.KNOWN -> R.string.known
        WordStatus.READY_TO_LEARN -> R.string.ready_to_learn
        WordStatus.LEARNING -> R.string.learning
        WordStatus.LEARNED -> R.string.learned
    }
    return context.getString(resId)
}