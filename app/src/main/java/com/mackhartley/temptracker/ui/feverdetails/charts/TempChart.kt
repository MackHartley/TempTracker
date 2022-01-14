package com.mackhartley.temptracker.ui.feverdetails.charts

import android.content.Context
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.EntryXComparator
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.databinding.ViewTempChartBinding
import com.mackhartley.temptracker.ui.core.MyMarkerView
import com.mackhartley.temptracker.utils.epochSecondToDate
import com.mackhartley.temptracker.utils.epochSecondToHourOfDay
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime
import java.util.*

class TempChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var lineChart: LineChart
    private lateinit var noDataTitle: TextView
    private lateinit var noDataDesc: TextView

    private val animationLength = resources.getInteger(R.integer.animation_length_ms)

    private var showXAxisInHours = true

    companion object {
        private const val MAX_X_AXIS_LABELS = 5
        private const val MIN_X_VALUES_FOR_ANIM = 7
        private const val GRAPH_MAX_Y_VAL_MODIFIER = 1.1
        private const val GRAPH_MIN_Y_VAL_MODIFIER = .99
        private const val MIN_HOURS_TO_SHOW_X_AXIS_DATES = 48
    }

    init {
        initView()
    }

    private fun initView() {
        val binding = ViewTempChartBinding.inflate(LayoutInflater.from(context), this, false)
        val view = binding.root
        lineChart = binding.lineChart
        noDataTitle = binding.noDataHeader
        noDataDesc = binding.noDataSubtitle
        initLineChart()
        setInitialData()
        addView(view)
    }

    private fun setInitialData() {
        updateData(emptyList())
    }

    private fun initLineChart() {
        val chartLabelColor = ContextCompat.getColor(context, R.color.teal_700)
        with(lineChart) {
            isHighlightPerDragEnabled = true
            isHighlightPerTapEnabled = true
            description.isEnabled = false
            legend.isEnabled = false
            setScaleEnabled(false)
            setNoDataText("")
            setDrawBorders(true)
            setBorderColor(chartLabelColor)
            setBorderWidth(.5f)
            extraLeftOffset = 36f
            extraRightOffset = 12f
            extraTopOffset = 48f

            // x axis
            xAxis.textColor = chartLabelColor
            xAxis.valueFormatter = DateAxisValueFormatter()
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(true)
            xAxis.setLabelCount(MAX_X_AXIS_LABELS, false)

            // left right axis
            axisRight.textColor = chartLabelColor
            axisRight.setDrawGridLines(true)
            axisLeft.isEnabled = false
            axisRight.axisMinimum = 0f
            marker = object : MyMarkerView(context, R.layout.view_marker_layout, showDot = true, allowMarkerClipping = true) {
                override fun performHapticFeedback() {
                    lineChart.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                }
            }
        }
    }

    fun updateData(tempValues: List<Pair<OffsetDateTime, Double>>) {
        val dataToDisplay = tempValues
        if (tempValues.size > 0) {
            showEmptyStateView(false)
        } else {
            showEmptyStateView(true)
        }
        updateXAxisUnits(dataToDisplay)

        updateChartRange(dataToDisplay)
        updateNumberOfChartLabels(dataToDisplay)
        setAnimSpeed(dataToDisplay)

        val lineChartData = calculateLineChartData(dataToDisplay)
        val lineDataSet = LineDataSet(lineChartData, "")

        lineDataSet.highLightColor = ContextCompat.getColor(context, R.color.teal_700)
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        lineDataSet.setDrawVerticalHighlightIndicator(false)
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.setDrawValues(false)
        lineDataSet.axisDependency = YAxis.AxisDependency.RIGHT
        setChartColor(lineDataSet)

        val lineData = LineData(lineDataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun updateXAxisUnits(dataToDisplay: List<Pair<OffsetDateTime, Double>>) {
        if (dataToDisplay.size < 2) {
            showXAxisInHours = true
        } else {
            val first = dataToDisplay.first().first
            val last = dataToDisplay.last().first
            val min = if (first > last) last else first
            val max = if (first > last) first else last
            showXAxisInHours = shouldShowXAxisInHours(min, max)
        }
    }

    private fun shouldShowXAxisInHours(date1: OffsetDateTime, date2: OffsetDateTime): Boolean {
        val x = Duration.between(date1, date2)
        return x.toHours() < MIN_HOURS_TO_SHOW_X_AXIS_DATES
    }

    private fun setChartColor(lineDataSet: LineDataSet) {
        lineDataSet.circleHoleColor = ContextCompat.getColor(context, R.color.teal_700) //todo change to white
        lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.teal_700))
        lineDataSet.color = ContextCompat.getColor(context, R.color.teal_700)
    }

    private fun updateChartRange(values: List<Pair<OffsetDateTime, Double>>) {
        if (values.isEmpty()) return
        val minYValue: Double = values.minByOrNull { it.second }?.second ?: 0.0
        val maxYValue: Double = values.maxByOrNull { it.second }?.second ?: 0.0

        val newMinY = minYValue * GRAPH_MIN_Y_VAL_MODIFIER
        val newMaxY = maxYValue * GRAPH_MAX_Y_VAL_MODIFIER

        lineChart.axisRight.axisMaximum = newMaxY.toFloat()
        lineChart.axisRight.axisMinimum = newMinY.toFloat()
    }

    private fun updateNumberOfChartLabels(nutritionValues: List<Pair<OffsetDateTime, Double>>) {
        val numDataPoints = nutritionValues.size
        val numLabelsToShow = if (numDataPoints < MAX_X_AXIS_LABELS) numDataPoints else MAX_X_AXIS_LABELS
        lineChart.xAxis.setLabelCount(numLabelsToShow, true)

        lineChart.axisRight.setLabelCount(6, false)
    }

    private fun setAnimSpeed(nutritionValues: List<Pair<OffsetDateTime, Double>>) {
        if (nutritionValues.size < MIN_X_VALUES_FOR_ANIM) {
            lineChart.animateX(0) // Don't run the X anim if there aren't many values. Will look choppy
        } else {
            lineChart.animateX(animationLength)
        }
    }

    private fun showEmptyStateView(shouldShow: Boolean) {
        if (shouldShow) {
            noDataTitle.visibility = View.VISIBLE
            noDataDesc.visibility = View.VISIBLE
        } else {
            noDataTitle.visibility = View.GONE
            noDataDesc.visibility = View.GONE
        }
    }

    private fun calculateLineChartData(tempValues: List<Pair<OffsetDateTime, Double>>): List<Entry> {
        val x = tempValues.map {
            Entry(
                dateTimeToXAxisFloat(it.first),
                it.second.toFloat()
            )
        }
        Collections.sort(x, EntryXComparator()) // From: https://github.com/PhilJay/MPAndroidChart/issues/2074
        return x
    }

    private fun dateTimeToXAxisFloat(dateTime: OffsetDateTime): Float {
        val result = dateTime.toEpochSecond().toFloat()
        return result
    }

    inner class DateAxisValueFormatter : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return if (showXAxisInHours) {
                epochSecondToHourOfDay(value)
            } else {
                epochSecondToDate(value)
            }
        }
    }
}