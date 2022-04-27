package com.example.myapplication

import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.google.android.material.appbar.AppBarLayout

object CollapsingBar {
    var mutabled = MutableLiveData(true)

    fun setOnClick(bottom: View, fragment: AppCompatActivity, collapsing: View, appBar: AppBarLayout){
        bottom.setOnClickListener {
            if (collapsing.isVisible){
                collapsing.isVisible = false
                slideDown(collapsing)
                appBar.setExpanded(true)
            }else{
                collapsing.isVisible = true
                slideUp(collapsing)
            }
            ViewCompat.setNestedScrollingEnabled(collapsing, collapsing.isVisible)
            mutabled.value = collapsing.isVisible
        }
        scrollBar(appBar, fragment)
    }

    private fun scrollBar(view: View, fragment: AppCompatActivity){
        mutabled.observe(fragment,{
            val params = view.layoutParams as CoordinatorLayout.LayoutParams
            if (params.behavior == null)
                params.behavior = AppBarLayout.Behavior()
            val behaviour = params.behavior as AppBarLayout.Behavior
            behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return it
                }
            })
        })
    }

    private fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0F
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }
}