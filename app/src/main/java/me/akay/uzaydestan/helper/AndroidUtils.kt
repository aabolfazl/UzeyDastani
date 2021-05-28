package me.akay.uzaydestan.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

class AndroidUtils {
    companion object {
        fun shakeView(view: View?, x: Int, num: Int) {
            if (view == null) {
                return
            }
            if (num == 6) {
                view.translationX = 0f
                return
            }
            val animatorSet = AnimatorSet()
            val value = view.resources.displayMetrics.density * x
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationX", value))
            animatorSet.duration = 50
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    shakeView(view, if (num == 5) 0 else -x, num + 1)
                }
            })
            animatorSet.start()
        }
    }
}