package com.example.rectendlineview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Canvas
import android.app.Activity
import android.content.Context

val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 3.2f
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#f44336",
    "#004D40",
    "#FFD600",
    "#00C853",
    "#6200EA"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n

fun Canvas.drawRectEndLine(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sc1 : Float = scale.divideScale(0, parts)
    val sc2 : Float = scale.divideScale(1, parts)
    val sc3 : Float = scale.divideScale(2, parts)
    val sc4 : Float = scale.divideScale(3, parts)
    val us1 : Float = size * sc1
    val us2 : Float = size * sc2
    val us3 : Float = (w / 2 - size / 2) * sc3
    val us4 : Float = (w / 2 - size / 2) * sc4
    save()
    translate(w / 2, h / 2)
    save()
    drawLine(-us1 / 2, size / 2, us1 / 2, size / 2, paint)
    translate(0f, (h / 2 + size) * sc4)
    for (j in 0..1) {
        save()
        translate(-size / 2 + size * j, size / 2)
        drawLine(0f, 0f, 0f, -us2, paint)
        restore()
        save()
    }
    restore()
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f)
        drawLine(us4, -size, us3, -size, paint)
        restore()
    }
    restore()
}

fun Canvas.drawRELNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawRectEndLine(scale, w, h, paint)
}

class RectEndLineView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}