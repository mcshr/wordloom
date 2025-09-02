package com.mcshr.wordloom.presentation.utils

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

fun pxToDp(context: Context, px: Int): Int {
    val density = context.resources.displayMetrics.density
    return (px / density).toInt()
}
