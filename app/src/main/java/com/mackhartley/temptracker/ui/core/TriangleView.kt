package com.mackhartley.temptracker.ui.core

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mackhartley.temptracker.R

class TriangleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val myPaint = Paint()
    private val myPath = Path()

    init {
        myPaint.color = ContextCompat.getColor(context, R.color.chart_marker_bg_color)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val mid = width / 2

        myPath.moveTo(0f, 0f)
        myPath.lineTo(width.toFloat(), 0f)
        myPath.lineTo(mid.toFloat(), height.toFloat())
        myPath.lineTo(0f, 0f)
        myPath.close()

        canvas?.drawPath(myPath, myPaint)
    }
}