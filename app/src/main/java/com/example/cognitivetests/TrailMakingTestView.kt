package com.example.cognitivetests

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
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
    private var isTestStarted = false
    private var processTouchEvent = true
    private var listener: TrailMakingTestListener? = null
    private val dotCount = 10
    private val dotRadius = 30f
    private val linePaint = Paint().apply {
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }
    private val dotPaint = Paint()
    private val textPaint = Paint().apply {
        textSize = 30f
        color = Color.WHITE
    }
    private lateinit var dots: List<PointF>
    private val path = Path()
    private val touchedDotsOrder = mutableListOf<Int>()
    private val textBounds = Rect()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        dots = mutableListOf()
        while (dots.size < dotCount) {
            var newDot: PointF
            do {
                newDot = PointF(dotRadius + Random.nextFloat() * (width - 2 * dotRadius), dotRadius + Random.nextFloat() * (height - 2 * dotRadius))
            } while (dots.any { isOverlapping(it, newDot) })
            (dots as MutableList<PointF>).add(newDot)
        }
    }

    private fun isOverlapping(dot1: PointF, dot2: PointF): Boolean {
        val dx = dot1.x - dot2.x
        val dy = dot1.y - dot2.y
        return dx * dx + dy * dy <= 4 * dotRadius * dotRadius
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, linePaint)
        dots.forEachIndexed { index, point ->
            canvas.drawCircle(point.x, point.y, dotRadius, dotPaint)
            val text = (index + 1).toString()
            textPaint.getTextBounds(text, 0, text.length, textBounds)
            val textWidth = textPaint.measureText(text)
            val textHeight = textBounds.height()
            canvas.drawText(text, point.x - textWidth / 2, point.y + textHeight / 2, textPaint)
        }
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
        dots.forEachIndexed { index, dot ->
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
        listener?.onTestCompleted()
    }


    private fun onMistakeMade() {
        listener?.onMistake()
        path.reset()
        touchedDotsOrder.clear()
        Toast.makeText(context, "Mistake made", Toast.LENGTH_SHORT).show()
        processTouchEvent = false
    }
}