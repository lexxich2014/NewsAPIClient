package com.gmail.lexxich2014.newsapiclient.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.gmail.lexxich2014.newsapiclient.R
import java.text.SimpleDateFormat
import java.util.*

class DateIntervalPickerView(val ctx: Context, attr: AttributeSet) : View(ctx, attr) {

    var startDate: Long
    var currentDate: Long

    val selectedStartDate=Date()
    val selectedEndDate=Date()
    var tSize=0
    init{
        val typeArray=ctx.obtainStyledAttributes(attr, R.styleable.DateIntervalPickerView)
        val daysAgo:Int=typeArray.getInt(R.styleable.DateIntervalPickerView_daysAgo,60)
        tSize=typeArray.getDimensionPixelSize(R.styleable.DateIntervalPickerView_textSize1,63)

        typeArray.recycle()
        currentDate= Date().time
        startDate= currentDate - (daysAgo.toLong()*86400000)

        selectedStartDate.time=startDate
        selectedEndDate.time=currentDate

    }


    private val sdf = SimpleDateFormat("dd-MM-yyyy")

    inner class Picker(var x: Float = 0F, var y: Float = 0F, var picked: Boolean = false) {
        private val path = Path()
        val width = 40F
        private val height = 80F
        private val paint =
            Paint().apply { color = ContextCompat.getColor(ctx, R.color.baseLine2) }

        val start: Float
            get() {
                return x - width / 2
            }
        val end: Float
            get() {
                return x + width / 2
            }

        fun draw(canvas: Canvas?) {
            val c = canvas ?: return
            path.moveTo(x, y)
            path.lineTo(x - width / 2, height)
            path.lineTo(x - width / 2,height*2)
            path.lineTo(x+width/2,height*2)
            path.lineTo(x+width/2,height)
            path.lineTo(x,y)
            //path.lineTo(x - width / 2 + width, height)
            //path.lineTo(x, y)
            canvas.drawPath(path, paint)
            path.reset()
        }
    }


    private val picker1 = Picker()
    private val picker2 = Picker()

     var widthViewSize: Int=0
     var heightViewSize: Int=0


    var millisInPixel = 0F

    val paint = Paint().apply {
        strokeWidth = 3f
        color = ContextCompat.getColor(context, R.color.baseLine1)
        isAntiAlias = true
    }
    val paint2 = Paint().apply {
        strokeWidth = 6F
        color = ContextCompat.getColor(context, R.color.baseLine2)
    }
    val paintText = Paint().apply { textSize = tSize.toFloat()}


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 700
        val desiredHeight = 100

        val widthMode=MeasureSpec.getMode(widthMeasureSpec)
        val widthSize=MeasureSpec.getSize(widthMeasureSpec)

        val heightMode=MeasureSpec.getMode(heightMeasureSpec)
        val heightSize=MeasureSpec.getSize(heightMeasureSpec)

        widthViewSize = when(widthMode){
            MeasureSpec.EXACTLY-> widthSize
            MeasureSpec.AT_MOST-> Math.min(desiredWidth,widthSize)
            else-> desiredWidth
        }
        heightViewSize = when(heightMode){
            MeasureSpec.EXACTLY-> heightSize
            MeasureSpec.AT_MOST-> Math.min(desiredHeight,heightSize)
            else-> desiredHeight
        }

        millisInPixel=(currentDate - startDate) / widthViewSize-picker1.width

        picker1.x=picker1.width/2
        picker1.y = heightViewSize / 2F
        picker2.y = heightViewSize / 2F
        picker2.x = widthViewSize.toFloat()-picker2.width/2
        setMeasuredDimension(widthViewSize, heightViewSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawLine(picker1.width/2, canvas.height / 2f, picker1.x, canvas.height / 2f, paint)
        canvas?.drawLine(
            picker2.x,
            canvas.height / 2f,
            widthViewSize.toFloat()-picker2.width/2,
            canvas.height / 2f,
            paint
        )
        //selected interval
        canvas?.drawLine(
            picker1.x,
            canvas.height / 2f,
            picker2.x,
            canvas.height / 2f,
            paint2
        )
        picker1.draw(canvas)
        picker2.draw(canvas)
        drawIntervalDate(canvas)

    }

    fun drawIntervalDate(canvas: Canvas?) {
        val startDelta = (millisInPixel * (picker1.x-picker1.width/2)).toLong()
        selectedStartDate.time=startDate + startDelta
        val endDelta = (millisInPixel * (picker2.x+picker2.width/2)).toLong()
        selectedEndDate.time=startDate + endDelta
        canvas?.drawText(sdf.format(selectedStartDate), 0F, 43F, paintText)
        canvas?.drawText(sdf.format(selectedEndDate), 400F, 43F, paintText)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if ((event.x > picker1.start) && (event.x < picker1.end)) {
                    picker1.picked = true
                } else if ((event.x > picker2.start) && (event.x < picker2.end)) {
                    picker2.picked = true
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (picker1.picked) {
                    if (event?.x in (picker1.width/2).toDouble()..widthViewSize.toDouble() && picker1.x <= picker2.x - picker2.width / 2) {
                        picker1.x = event?.x
                        invalidate()
                    }
                }
                if (picker2.picked) {
                    if (event?.x in 0.0..(widthViewSize-picker2.width/2).toDouble() && picker2.x >= picker1.x + picker1.width / 2) {
                        picker2.x = event?.x
                        invalidate()
                    }
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if ((picker1.x >= picker2.x - picker2.width / 2) && picker1.picked) {
                    picker1.x = picker1.x - picker2.width / 2
                    invalidate()
                }
                if ((picker2.x <= picker1.x + picker1.width / 2) && picker2.picked) {
                    picker2.x = picker2.x + picker2.width / 2
                    invalidate()
                }
                picker1.picked = false
                picker2.picked = false
                return true
            }
        }
        return true
    }

    fun getSelectedInterval(): Array<Date>{
        return arrayOf(selectedStartDate,selectedEndDate)
    }

}