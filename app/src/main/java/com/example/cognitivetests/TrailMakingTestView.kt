package com.example.cognitivetests

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random


interface TrailMakingTestListener {
    fun onTestCompleted(isOrderCorrect: Boolean)
}

class TrailMakingTestView(context: Context, attrs: AttributeSet) : View(context, attrs) {
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
        dots = List(dotCount) { PointF(dotRadius + Random.nextFloat() * (width - 2 * dotRadius), dotRadius + Random.nextFloat() * (height - 2 * dotRadius)) }
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
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                checkTouchingDot(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                checkTouchingDot(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                validateOrder()
            }
        }
        invalidate()
        return true
    }

    private fun checkTouchingDot(x: Float, y: Float) {
        dots.forEachIndexed { index, dot ->
            if (isTouchingDot(PointF(x, y), dot) && !touchedDotsOrder.contains(index)) {
                touchedDotsOrder.add(index)
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

    private fun validateOrder() {
        val isOrderCorrect = touchedDotsOrder.indices.all { touchedDotsOrder[it] == it }
        listener?.onTestCompleted(isOrderCorrect)
    }
}