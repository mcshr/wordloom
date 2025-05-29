package com.mcshr.wordloom.presentation.learningScreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import com.mcshr.wordloom.databinding.ItemCardBinding

class CardFlipController(
   binding: ItemCardBinding
) {
    private val frontView = binding.frontSide
    private val backView = binding.backSide
    private var isFront = true
    private var  isFlipping = false
    var onFlipStart: (()->Unit)?= null
    var onFlipEnd: (()->Unit)?= null
    private var currentAnimator: AnimatorSet? = null

    fun flip() {
        if (isFlipping == true) return
        isFlipping = true
        frontView.cameraDistance = 50000f
        backView.cameraDistance = 50000f

        onFlipStart?.invoke()

        val (visibleView, invisibleView) = if (isFront) frontView to backView else backView to frontView

        val animator1 = ObjectAnimator.ofFloat(visibleView, "rotationY", 0f, 90f).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    visibleView.isVisible = false
                    invisibleView.isVisible = true
                }
            })
            interpolator = AccelerateInterpolator()
        }

        val animator2 = ObjectAnimator.ofFloat(invisibleView, "rotationY", -90f, 0f).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isFront = !isFront
                    isFlipping = false
                    onFlipEnd?.invoke()
                }
            })
            interpolator = DecelerateInterpolator()
        }

        currentAnimator = AnimatorSet().apply {
            playSequentially(
                animator1, animator2
            )
            duration = 1000
        }
        currentAnimator?.start()

    }
    fun cancelFlip(){
        currentAnimator?.cancel()
        currentAnimator = null
        frontView.isVisible = true
        backView.isVisible = false
        frontView.rotationY = 0f
        backView.rotationY = 0f
        isFront = true
        isFlipping = false
        onFlipEnd?.invoke()
    }
}
