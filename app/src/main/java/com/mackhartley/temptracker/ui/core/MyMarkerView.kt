package com.mackhartley.temptracker.ui.core

import android.content.Context
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.utils.epochSecondToDate
import com.mackhartley.temptracker.utils.epochSecondToHourOfDay
import com.mackhartley.temptracker.utils.toTempString


/**
 * @param showDot Shows a CircleView or "dot" that indicates the current selected point on a line chart
 * @param allowMarkerClipping Allows the marker to clip off the screen. Useful with linechart where
 * it's a higher priority to make sure the marker lines up exactly with the current selected point
 */
abstract class MyMarkerView(
    context: Context,
    layoutResource: Int,
    private val showDot: Boolean = false,
    private val allowMarkerClipping: Boolean = false
) : MarkerView(context, layoutResource) {

    private var markerTitle: TextView = findViewById(R.id.marker_title)
    private var markerSubtitle1: TextView = findViewById(R.id.marker_subtitle1)
    private var markerSubtitle2: TextView = findViewById(R.id.marker_subtitle2)
    private var markerDot: View = findViewById(R.id.dot_view)

    private var prevVal: Float? = null

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        markerTitle.text = getTemp(e)
        markerSubtitle1.text = getTime(e)
        markerSubtitle2.text = getDate(e)

        hapticFeedbackIfNewSelection(e?.x)
        super.refreshContent(e, highlight)
    }

    private fun getDate(e: Entry?): String {
        return e?.let {
            epochSecondToDate(e.x)
        } ?: "---"
    }

    private fun getTime(e: Entry?): String {
        return e?.let {
            epochSecondToHourOfDay(e.x)
        } ?: "---"
    }

    private fun getTemp(e: Entry?): String {
        return e?.y?.toDouble()?.toTempString() ?: "---"
    }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        return if (allowMarkerClipping) {
            offset
        } else {
            super.getOffsetForDrawingAtPoint(posX, posY)
        }
    }

    private fun hapticFeedbackIfNewSelection(newVal: Float?) {
        if (newVal != prevVal) {
            performHapticFeedback()
        }
        prevVal = newVal
    }

    override fun getOffset(): MPPointF {
        val dotViewRadius = (resources.getDimension(R.dimen.marker_dot_circumference).toInt() / 2)
        return if (showDot) {
            markerDot.visibility = View.VISIBLE

            val xOffset = (width / 2) * -1
            val yOffset = (height - dotViewRadius) * -1 // As the number gets more negative, the view moves up

            MPPointF(xOffset.toFloat(), yOffset.toFloat())
        } else {
            markerDot.visibility = View.INVISIBLE
            val circleHeight = dotViewRadius * 2

            val xOffset = (width / 2) * -1
            val yOffset = (height - circleHeight) * -1 // As the number gets more negative, the view moves up. Use circle circumference instead of radius here because we want to align the top of the "invisible" circle with the data bar (not the center)

            MPPointF(xOffset.toFloat(), yOffset.toFloat())
        }
    }

    abstract fun performHapticFeedback()
}