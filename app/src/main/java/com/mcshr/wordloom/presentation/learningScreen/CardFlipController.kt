package com.mcshr.wordloom.presentation.learningScreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import com.mcshr.wordloom.databinding.ItemCardBinding

class CardFlipController(
   binding: ItemCardBinding
) {
    private val cardView = binding.root
    private val frontView = binding.frontSide
    private val backView = binding.backSide
    private var isFront = true
    private var isFlipping = false

    fun flip() {
        if (isFlipping) return
        cardView.cameraDistance = 20000f

        isFlipping = true

        val (visibleView, invisibleView) = if (isFront) frontView to backView else backView to frontView

        val animator1 = ObjectAnimator.ofFloat(visibleView, "rotationY", 0f, 90f)
        animator1.duration = 150
        animator1.interpolator = AccelerateInterpolator()

        val animator2 = ObjectAnimator.ofFloat(invisibleView, "rotationY", -90f, 0f)
        animator2.duration = 150
        animator2.interpolator = DecelerateInterpolator()

        animator1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibleView.isVisible = false
                invisibleView.isVisible = true
                animator2.start()
            }
        })

        animator2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isFlipping = false
                isFront = !isFront
            }
        })

        animator1.start()
    }
}
