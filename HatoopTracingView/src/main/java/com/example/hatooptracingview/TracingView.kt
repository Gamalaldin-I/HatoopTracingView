package com.example.hatooptracingview
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.graphics.Canvas
import androidx.core.graphics.toColorInt
import kotlin.math.sqrt

class TracingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    // Callback for completion
    private lateinit var listener: TracingListener
    private lateinit var currentPath: Path
    private var drawing = false
    private var counter = 0
    private var tracingName = ""
    var completedPaths = ArrayList<Path>()
    var pathArray = ArrayList<Path>()

    // Paint objects
    private val completedPaint = Paint()
    private val activePaint = Paint()
    private val tracePaint = Paint()
    private val tracedPaint = Paint()
    private val borderPaint = Paint()
    private val thumbPaint = Paint()
    private val glowPaint = Paint()

    // Path tracking
    private val pathMeasure = PathMeasure()
    private var thumbRadius = 70f
    private var thumbPos = FloatArray(2)
    private var pathProgress = 0f
    private var isDragging = false

    // Tolerance for easier tracing (balanced for accuracy and ease)
    private var touchTolerance = 170f

    // Maximum jump allowed per frame to prevent skipping
    private val maxProgressJumpPerFrame = 0.05f

    // Default colors
    private var inCompetedPathsColor: Int = Color.GRAY
    private var activePathColor: Int = Color.YELLOW
    private var completedPathColor: Int = Color.MAGENTA
    private var dottedPathColor: Int = Color.LTGRAY
    private var borderColor: Int = Color.DKGRAY
    private var thumbColor: Int = Color.RED
    private var glowColor: Int = "#40F44336".toColorInt()

    init {
        attrs?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.TracingView,
                0, 0
            ).apply {
                try {
                   // tracingName = getString(R.styleable.TracingView_tracingLetter) ?: ""

                    setActivePathColor(
                        getColor(R.styleable.TracingView_activePathColor, activePathColor)
                    )
                    setCompletedPathColor(
                        getColor(R.styleable.TracingView_completedPathColor, completedPathColor)
                    )
                    setDottedPathColor(
                        getColor(R.styleable.TracingView_dottedPathColor, dottedPathColor)
                    )
                    setBorderColor(
                        getColor(R.styleable.TracingView_borderColor, borderColor)
                    )
                    setThumbColor(
                        getColor(R.styleable.TracingView_thumbColor, thumbColor)
                    )
                    setGlowColor(
                        getColor(R.styleable.TracingView_glowColor, glowColor)
                    )
                    setInCompetedPathsColor(
                        getColor(R.styleable.TracingView_inCompetedPathsColor, inCompetedPathsColor)
                    )
                } finally {
                    recycle()
                }
            }
        }

        // Gray dashed guide path
        tracePaint.apply {
            color = inCompetedPathsColor
            style = Paint.Style.STROKE
            strokeWidth = 100f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }

        // Black border for guide path
        borderPaint.apply {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = 110f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }

        // Yellow dots for active path direction
        activePaint.apply {
            color = dottedPathColor
            style = Paint.Style.STROKE
            strokeWidth = 25f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            pathEffect = DashPathEffect(floatArrayOf(5f, 40f), 0f)
        }

        // Green traced path (what the child has drawn)
        tracedPaint.apply {
            color = activePathColor
            style = Paint.Style.STROKE
            strokeWidth = 100f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }

        // White completed paths
        completedPaint.apply {
            color = completedPathColor
            style = Paint.Style.STROKE
            strokeWidth = 100f
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }

        // Red ball (thumb) with glow effect
        thumbPaint.apply {
            color = thumbColor
            style = Paint.Style.FILL
            isAntiAlias = true
            setShadowLayer(20f, 0f, 0f, "#FFCDD2".toColorInt())
        }

        // Glow effect for the ball
        glowPaint.apply {
            color = glowColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    //

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////Public getters/////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    fun getPathsNumber() = pathArray.size

    ////////////////////////////////////////////////////////////////////////
    //////////////////////////////Public setters///////////////////////////
    //////////////////////////////////////////////////////////////////////
    fun setActivePathColor(color: Int) { activePathColor = color; invalidate() }
    fun setCompletedPathColor(color: Int) { completedPathColor = color; invalidate() }
    fun setDottedPathColor(color: Int) { dottedPathColor = color; invalidate() }
    fun setBorderColor(color: Int) { borderColor = color; invalidate() }
    fun setThumbColor(color: Int) { thumbColor = color; invalidate() }
    fun setGlowColor(color: Int) { glowColor = color; invalidate() }
    fun setInCompetedPathsColor(color: Int) { inCompetedPathsColor = color; invalidate() }

    fun setTracingListener(listener: TracingListener) { this.listener= listener }

    fun setChar(nameOfLetterOrNumber: String) {
        pathArray.clear()
        completedPaths = arrayListOf()
        tracingName = nameOfLetterOrNumber
        pathArray = TracingPaths.getLetterOrNumbersPaths(nameOfLetterOrNumber,this.width.toFloat(),this.height.toFloat())
        if (pathArray.isNotEmpty()) {
            currentPath = pathArray[0]
            pathMeasure.setPath(currentPath, false)
            pathMeasure.getPosTan(0f, thumbPos, null)
            pathProgress = 0f
            counter = 0
            drawing = true
            isDragging = false
            invalidate()
        }
    }
    fun reset() {
        completedPaths.clear()
        listener.onReset()
        counter = 0
        if (pathArray.isNotEmpty()) {
            changePath(0)
            drawing = true
        }
    }

    override fun onDraw(canvas:Canvas) {
        super.onDraw(canvas)

        // Draw all guide paths with border
        for (path in pathArray) {
            canvas.drawPath(path, borderPaint)
            canvas.drawPath(path, tracePaint)
        }

        // Draw traced portion
        if (drawing && pathProgress > 0) {
            canvas.drawPath(getTracedPath(), tracedPaint)
        }

        // Draw completed paths
        for (path in completedPaths) {
            canvas.drawPath(path, completedPaint)
        }

        // Draw active path indicators and thumb
        if (drawing && !checkAllPathsCompleted()) {
            // Draw yellow dots showing direction
            canvas.drawPath(getActivePath(), activePaint)

            // Draw glowing ball effect
            canvas.drawCircle(thumbPos[0], thumbPos[1], thumbRadius + 15f, glowPaint)
            canvas.drawCircle(thumbPos[0], thumbPos[1], thumbRadius, thumbPaint)
        }

        // Auto-invalidate only when drawing
        if (drawing && !checkAllPathsCompleted()) {
            invalidate()
        }
    }

    private fun getActivePath(): Path {
        return currentPath
    }

    private fun getTracedPath(): Path {
        val tracedPath = Path()
        pathMeasure.getSegment(0f, pathProgress * pathMeasure.length, tracedPath, true)
        return tracedPath
    }

    private fun changePath(index: Int) {
        if (index < pathArray.size) {
            currentPath = pathArray[index]
            pathMeasure.setPath(currentPath, false)
            pathMeasure.getPosTan(0f, thumbPos, null)
            pathProgress = 0f
            invalidate()
        }
    }

    private fun addCompletedPath(path: Path) {
        completedPaths.add(path)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        // Check if current path is fully traced
        if (isFullyTraced() && counter < pathArray.size) {
            isDragging = false
            addCompletedPath(currentPath)
            counter++
            listener.onCompletedPathsChange(completedPaths.size)
            listener.onProgressChange(getCompletionPercentage())
            if(counter == pathArray.size){
                listener.onCompleted()
            }

            if (counter < pathArray.size) {
                changePath(counter)
                if (tracingName.isNotEmpty()) {
                    isDragging = true
                }
            } else {
                // All paths completed!
                drawing = false
                invalidate()
                return true
            }
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // More forgiving touch detection for kids
                val dx = touchX - thumbPos[0]
                val dy = touchY - thumbPos[1]
                val distance = sqrt((dx * dx + dy * dy).toDouble())

                // MUST start near the red ball - can't skip ahead!
                if (distance <= thumbRadius + touchTolerance) {
                    isDragging = true
                    drawing = true
                    return true
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val (nearestProgress, nearestPos) = getNearestPointOnPath(touchX, touchY)
                    val distToPath = distanceToPath(touchX, touchY)

                    // CRITICAL: Only allow moving forward incrementally, not jumping ahead
                    val progressDiff = nearestProgress - pathProgress

                    // Check if within tolerance and not jumping too far ahead
                    if (nearestProgress >= pathProgress &&
                        progressDiff <= maxProgressJumpPerFrame &&
                        distToPath <= touchTolerance) {
                        pathProgress = nearestProgress
                        thumbPos = nearestPos
                        invalidate()
                    } else if (distToPath > touchTolerance * 1.5f) {
                        // If they go too far off path, stop dragging
                        isDragging = false
                    }
                    // If trying to jump ahead too much, don't update position
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
            }
        }
        return true
    }

    private fun distanceToPath(touchX: Float, touchY: Float): Float {
        var closestDistance = Float.MAX_VALUE
        val pathLength = pathMeasure.length

        // Check points along the path
        for (i in 0..100) {
            val progress = i / 100f * pathLength
            val pos = FloatArray(2)
            pathMeasure.getPosTan(progress, pos, null)

            val dx = touchX - pos[0]
            val dy = touchY - pos[1]
            val distance = sqrt((dx * dx + dy * dy).toDouble()).toFloat()

            if (distance < closestDistance) {
                closestDistance = distance
            }
        }
        return closestDistance
    }

    private fun getNearestPointOnPath(touchX: Float, touchY: Float): Pair<Float, FloatArray> {
        val pathLength = pathMeasure.length
        var closestDistance = Float.MAX_VALUE
        var closestPos = FloatArray(2)
        var closestProgress = 0f

        // Increased samples for very accurate tracking (500 instead of 200)
        for (i in 0..500) {
            val progress = i / 500f * pathLength
            val pos = FloatArray(2)
            pathMeasure.getPosTan(progress, pos, null)

            val dx = touchX - pos[0]
            val dy = touchY - pos[1]
            val distToTouch = dx * dx + dy * dy

            if (distToTouch < closestDistance) {
                closestDistance = distToTouch
                closestPos = pos
                closestProgress = progress / pathLength
            }
        }
        return Pair(closestProgress, closestPos)
    }

    private fun checkAllPathsCompleted(): Boolean {
        return completedPaths.size == pathArray.size
    }

    private fun isFullyTraced(): Boolean {
        val progress = pathProgress * pathMeasure.length
        val pathLength = pathMeasure.length
        // More forgiving completion threshold (95% instead of 100%)
        return (pathLength - progress) <= pathLength * 0.05f
    }

    private fun getCompletionPercentage(): Int {
        if (pathArray.isEmpty()) return 0
        return (completedPaths.size * 100) / pathArray.size
    }


}