package com.mackhartley.temptracker.ui.core

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mackhartley.temptracker.R

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val myPaint = Paint()

    init {
        myPaint.color = ContextCompat.getColor(context, R.color.chart_marker_bg_color)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val w = width
        val h = height

        canvas?.drawCircle(w/2f, h/2f, (w/2).toFloat(), myPaint)
    }
}