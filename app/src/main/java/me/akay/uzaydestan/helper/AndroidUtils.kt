package me.akay.uzaydestan.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt

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

        fun calculateDistance(a: Point, b: Point): Double {
            return sqrt((a.x - b.x).pow(2.toDouble()) + (a.y - b.y).pow(2.toDouble()))
        }
    }
}