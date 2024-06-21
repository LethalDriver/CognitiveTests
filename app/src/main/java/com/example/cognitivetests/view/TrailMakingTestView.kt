package com.example.cognitivetests.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.random.Random

interface TrailMakingTestListener {
    fun onTestCompleted()
    fun onTestStarted()
    fun onMistake()
}

class TrailMakingTestView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private lateinit var dots: List<Pair<PointF, String>>
    private var isTestStarted = false
    private var processTouchEvent = true
    private var listener: TrailMakingTestListener? = null
    private var testPart = 1
    private val dotCount = 20
    private val dotRadius = 30f
    private val linePaint = Paint().apply {
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }
    private val dotPaint = Paint().apply {
        color = Color.rgb(98, 0, 238)
    }
    private val textPaint = Paint().apply {
        textSize = 30f
        color = Color.WHITE
    }
    private val path = Path()
    private val touchedDotsOrder = mutableListOf<Int>()
    private val textBounds = Rect()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        dots = mutableListOf()
        while (dots.size < dotCount) {
            var newDot: PointF
            var label: String
            do {
                newDot = PointF(dotRadius + Random.nextFloat() * (width - 2 * dotRadius), dotRadius + Random.nextFloat() * (height - 2 * dotRadius))
                label = if (testPart == 1) (dots.size + 1).toString() else if (dots.size % 2 == 0) (dots.size / 2 + 1).toString() else ('A' + dots.size / 2).toString()
            } while (dots.any { isOverlapping(it.first, newDot) })
            (dots as MutableList<Pair<PointF, String>>).add(Pair(newDot, label))
        }
    }

    private fun isOverlapping(dot1: PointF, dot2: PointF): Boolean {
        val dx = dot1.x - dot2.x
        val dy = dot1.y - dot2.y
        return dx * dx + dy * dy <= 4 * dotRadius * dotRadius
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!processTouchEvent && event.action != MotionEvent.ACTION_UP) {
            return true
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isTestStarted) {
                    listener?.onTestStarted()
                    isTestStarted = true
                }
                path.moveTo(event.x, event.y)
                checkTouchingDot(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                checkTouchingDot(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                checkIfTestFinished()
                path.reset()
                processTouchEvent = true
            }
        }
        invalidate()
        return true
    }

    private fun checkTouchingDot(x: Float, y: Float) {
        dots.forEachIndexed { index, (dot, label) ->
            if (isTouchingDot(PointF(x, y), dot)) {
                if (!touchedDotsOrder.contains(index)) {
                    if (index != touchedDotsOrder.size) {
                        onMistakeMade()
                        return
                    }
                    touchedDotsOrder.add(index)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, linePaint)
        dots.forEachIndexed { index, (point, label) ->
            canvas.drawCircle(point.x, point.y, dotRadius, dotPaint)
            textPaint.getTextBounds(label, 0, label.length, textBounds)
            val textWidth = textPaint.measureText(label)
            val textHeight = textBounds.height()
            canvas.drawText(label, point.x - textWidth / 2, point.y + textHeight / 2, textPaint)
        }
    }

    private fun isTouchingDot(point: PointF, dot: PointF): Boolean {
        val dx = point.x - dot.x
        val dy = point.y - dot.y
        return dx * dx + dy * dy <= dotRadius * dotRadius
    }

    fun setTrailMakingTestListener(listener: TrailMakingTestListener) {
        this.listener = listener
    }

    private fun checkIfTestFinished() {
        if (touchedDotsOrder.size != dotCount) {
            return
        }
        if (testPart == 1) {
            testPart++
            touchedDotsOrder.clear()
            onSizeChanged(width, height, width, height)
            invalidate()
        } else {
            listener?.onTestCompleted()
        }
    }

    private fun onMistakeMade() {
        listener?.onMistake()
        path.reset()
        touchedDotsOrder.clear()
        Toast.makeText(context, "Mistake made", Toast.LENGTH_SHORT).show()
        processTouchEvent = false
    }
}