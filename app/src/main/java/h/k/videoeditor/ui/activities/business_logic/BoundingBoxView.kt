package h.k.videoeditor.ui.activities.business_logic

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import h.k.videoeditor.R
import java.util.*

class BoundingBoxView (context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var boxPaint = Paint()
    private var rect:Rect?=null
    private var scaleFactor: Float = 2.2f
    private var scaleX: Int = 0
    private var scaleY: Int = 0

    init {
        initPaints()
    }

    fun clear() {
        boxPaint.reset()
        rect=null
        invalidate()
        initPaints()
    }

    fun setRect(rect: Rect?){
        this.rect=rect
    }
    fun getRect():Rect?{
        return this.rect
    }

    private fun initPaints() {
        boxPaint.color = ContextCompat.getColor(context!!, R.color.blue_dark)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

       //Rect(96, 338 - 323, 423)
            val top = rect?.top?.times(scaleFactor)
            val bottom = rect?.bottom?.times(scaleFactor)
            val left = rect?.left?.times(scaleFactor)
            val right = rect?.right?.times(scaleFactor)

            // Draw bounding box around detected objects
            val drawableRect = RectF(left?:0f, top?:0f, right?:0f, bottom?:0f)
            canvas.drawRect(drawableRect, boxPaint)

        
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }


    override fun onTouchEvent( event: MotionEvent?): Boolean {
        Log.d("logkey",event?.action.toString())
        if (event?.action == MotionEvent.ACTION_MOVE) {

            Log.d("logkey","left: ${rect?.left}\n top: ${rect?.top}\n" +
                    " right: ${rect?.right}\n" +
                    " bottom: ${rect?.bottom}")
            if (event.rawX>2){
                scaleX=event.rawX.toInt()/2
            }
            if (event.rawY>4){
                scaleY=event.rawY.toInt()/3
            }
//            rect?.left=event.rawX.toInt()
//            rect?.top=event.rawY.toInt()
            setRect(Rect(
                rect?.left?: 0,
                rect?.top?: 0,
                scaleX,scaleY,
            ))
            invalidate()
//            clear()

        }
        return true
    }
}
