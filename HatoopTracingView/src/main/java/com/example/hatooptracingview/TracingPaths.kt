package com.example.hatooptracingview
import android.graphics.Path

object TracingPaths {

    private lateinit var paths: ArrayList<Path>
    private var width: Float = 0f
    private var height: Float = 0f

    /**
     * Get letter paths for the given index with responsive sizing
     * @param canvasWidth Width of the drawing canvas
     * @param canvasHeight Height of the drawing canvas
     */
    fun getLetterOrNumbersPaths(name:String, canvasWidth: Float, canvasHeight: Float): ArrayList<Path> {
        // Add padding to the canvas for better visual appearance
        width = canvasWidth * 0.8f
        height = canvasHeight * 0.8f
        paths = when (name) {
            "A" -> getA()
            "B" -> getB()
            "C" -> getC()
            "D" -> getD()
            "E" -> getE()
            "F" -> getF()
            "G" -> getG()
            "H" -> getH()
            "I" -> getI()
            "J" -> getJ()
            "K" -> getK()
            "L" -> getL()
            "M" -> getM()
            "N" -> getN()
            "O" -> getO()
            "P" -> getP()
            "Q" -> getQ()
            "R" -> getR()
            "S" -> getS()
            "T" -> getT()
            "U" -> getU()
            "V" -> getV()
            "W" -> getW()
            "X" -> getX()
            "Y" -> getY()
            "Z" -> getZ()
            /////////////numbers////////////////
            "0" -> getZero()
            "1" -> getOne()
            "2" -> getTwo()
            "3" -> getThree()
            "4" -> getFour()
            "5" -> getFive()
            "6" -> getSix()
            "7" -> getSeven()
            "8" -> getEight()
            "9" -> getNine()
            "10" -> getTen()
            else -> arrayListOf()
        }
        return paths
    }

    // Helper functions to convert ratios to actual coordinates
    private fun w(ratio: Float): Float {
        val offsetX = (width * 0.2f) / 2 // Center horizontally
        return offsetX + (width * ratio)
    }

    private fun h(ratio: Float): Float {
        val offsetY = (height * 0.2f) / 2 // Center vertically
        return offsetY + (height * ratio)
    }

    // ==================== LETTERS ====================

    private fun getA(): ArrayList<Path> {
        paths = arrayListOf()
        // Left diagonal stroke from top to bottom-left
        addPath(setPath(Path(), w(0.5f), h(0.1f), w(0.15f), h(0.9f)))
        // Right diagonal stroke from top to bottom-right
        addPath(setPath(Path(), w(0.5f), h(0.1f), w(0.85f), h(0.9f)))
        // Horizontal crossbar (positioned at golden ratio ~0.55)
        addPath(setPath(Path(), w(0.3f), h(0.55f), w(0.7f), h(0.55f)))
        return paths
    }

    private fun getB(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line
        addPath(setPath(Path(), w(0.2f), h(0.1f), w(0.2f), h(0.9f)))
        // Top curve - smoother and more rounded
        addPath(setPathByCubic(
            Path(),
            w(0.2f), h(0.1f),
            w(0.8f), h(0.05f),
            w(0.8f), h(0.45f),
            w(0.2f), h(0.5f)
        ))
        // Bottom curve - larger and more balanced
        addPath(setPathByCubic(
            Path(),
            w(0.2f), h(0.5f),
            w(0.85f), h(0.5f),
            w(0.85f), h(0.9f),
            w(0.2f), h(0.9f)
        ))
        return paths
    }

    private fun getC(): ArrayList<Path> {
        paths = arrayListOf()
        // C-shaped arc
        addPath(setArchPath(
            Path(),
            w(0.1f), h(0.1f),
            w(0.9f), h(0.9f),
            -45f, -270f
        ))
        return paths
    }

    private fun getD(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line on left
        addPath(setPath(Path(), w(0.15f), h(0.1f), w(0.15f), h(0.9f)))
        // Curved right side - smooth D shape
        addPath(setPathByCubic(
            Path(),
            w(0.15f), h(0.1f),
            w(0.9f), h(0.1f),
            w(0.9f), h(0.9f),
            w(0.15f), h(0.9f)
        ))
        return paths
    }

    private fun getE(): ArrayList<Path> {
        paths = arrayListOf()
        // Top horizontal
        addPath(setPath(Path(), w(0.8f), h(0.1f), w(0.15f), h(0.1f)))
        // Vertical line
        addPath(setPath(Path(), w(0.15f), h(0.1f), w(0.15f), h(0.9f)))
        // Bottom horizontal
        addPath(setPath(Path(), w(0.15f), h(0.9f), w(0.8f), h(0.9f)))
        // Middle horizontal (slightly shorter)
        addPath(setPath(Path(), w(0.7f), h(0.5f), w(0.15f), h(0.5f)))
        return paths
    }

    private fun getF(): ArrayList<Path> {
        paths = arrayListOf()
        // Top horizontal
        addPath(setPath(Path(), w(0.8f), h(0.1f), w(0.15f), h(0.1f)))
        // Vertical line
        addPath(setPath(Path(), w(0.15f), h(0.1f), w(0.15f), h(0.9f)))
        // Middle horizontal (shorter than top)
        addPath(setPath(Path(), w(0.7f), h(0.5f), w(0.15f), h(0.5f)))
        return paths
    }

    private fun getG(): ArrayList<Path> {
        paths = arrayListOf()
        // C-shaped arc
        addPath(setArchPath(
            Path(),
            w(0.1f), h(0.1f),
            w(0.9f), h(0.9f),
            -45f, -270f
        ))

        // Small vertical down from bar
        addPath(setPath(Path(), w(0.80f), h(0.75f), w(0.80f), h(0.50f)))
        // Horizontal bar extending inward
        addPath(setPath(Path(), w(0.80f), h(0.5f), w(0.5f), h(0.5f)))
        return paths
    }

    private fun getH(): ArrayList<Path> {
        paths = arrayListOf(
            setPath(Path(), w(0.15f), h(0.1f), w(0.15f), h(0.9f)),
            setPath(Path(), w(0.85f), h(0.1f), w(0.85f), h(0.9f)),
            setPath(Path(), w(0.15f), h(0.5f), w(0.85f), h(0.5f))
        )
        return paths
    }

    private fun getI(): ArrayList<Path> {
        paths = arrayListOf(
            // Vertical center line
            setPath(Path(), w(0.5f), h(0.1f), w(0.5f), h(0.9f)),
            // Top serif
            setPath(Path(), w(0.25f), h(0.1f), w(0.75f), h(0.1f)),
            // Bottom serif
            setPath(Path(), w(0.25f), h(0.9f), w(0.75f), h(0.9f))
        )
        return paths
    }

    private fun getJ(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line down
        addPath(setPath(Path(), w(0.75f), h(0.1f), w(0.75f), h(0.7f)))
        // Bottom hook curve
        addPath(setPathByCubic(
            Path(),
            w(0.75f), h(0.7f),
            w(0.75f), h(1.0f),
            w(0.15f), h(1.0f),
            w(0.15f), h(0.65f)
        ))
        // Top serif (optional)
        addPath(setPath(Path(), w(0.5f), h(0.1f), w(0.85f), h(0.1f)))
        return paths
    }

    private fun getK(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line
        addPath(setPath(Path(), w(0.15f), h(0.1f), w(0.15f), h(0.9f)))
        // Upper diagonal
        addPath(setPath(Path(), w(0.85f), h(0.1f), w(0.15f), h(0.5f)))
        // Lower diagonal
        addPath(setPath(Path(), w(0.15f), h(0.5f), w(0.85f), h(0.9f)))
        return paths
    }

    private fun getL(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line
        addPath(setPath(Path(), w(0.15f), h(0.1f), w(0.15f), h(0.9f)))
        // Horizontal bottom
        addPath(setPath(Path(), w(0.15f), h(0.9f), w(0.6f), h(0.9f)))
        return paths
    }

    private fun getM(): ArrayList<Path> {
        paths = arrayListOf()
        // Left vertical
        addPath(setPath(Path(), w(0.1f), h(0.9f), w(0.1f), h(0.1f)))
        // Left diagonal to center peak
        addPath(setPath(Path(), w(0.1f), h(0.1f), w(0.5f), h(0.6f)))
        // Right diagonal from center peak
        addPath(setPath(Path(), w(0.5f), h(0.6f), w(0.9f), h(0.1f)))
        // Right vertical
        addPath(setPath(Path(), w(0.9f), h(0.1f), w(0.9f), h(0.9f)))
        return paths
    }

    private fun getN(): ArrayList<Path> {
        paths = arrayListOf()
        // Left vertical (bottom to top)
        addPath(setPath(Path(), w(0.15f), h(0.9f), w(0.15f), h(0.1f)))
        // Diagonal (top-left to bottom-right)
        addPath(setPath(Path(), w(0.15f), h(0.1f), w(0.85f), h(0.9f)))
        // Right vertical (bottom to top)
        addPath(setPath(Path(), w(0.85f), h(0.9f), w(0.85f), h(0.1f)))
        return paths
    }

    private fun getO(): ArrayList<Path> {
        paths = arrayListOf()
        // Perfect oval/circle using addOval
        val path = Path()
        path.addOval(
            w(0.1f), h(0.1f),
            w(0.9f), h(0.9f),
            Path.Direction.CW
        )
        addPath(path)
        return paths
    }

    private fun getP(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line
        addPath(setPath(
            Path(),
            w(0.15f), h(0.1f),
            w(0.15f), h(0.9f)))
        // Top curve (rounded P bowl)
        addPath(setPathByCubic(
            Path(),
            w(0.15f), h(0.1f),
            w(0.85f), h(0.05f),
            w(0.85f), h(0.7f),
            w(0.15f), h(0.6f)
        ))
        return paths
    }

    private fun getQ(): ArrayList<Path> {
        paths = arrayListOf()
        // Circle/oval using addOval
        val circlePath = Path()
        circlePath.addOval(
            w(0.1f), h(0.1f),
            w(0.9f), h(0.9f),
            Path.Direction.CW
        )
        addPath(circlePath)
        // Tail diagonal (from inside to outside bottom-right)
        addPath(setPath(Path(), w(0.6f), h(0.6f), w(0.95f), h(0.95f)))
        return paths
    }

    private fun getR(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line
        addPath(setPath(Path(), w(0.15f), h(0.1f), w(0.15f), h(0.95f)))
        // Top curve
        addPath(setPathByCubic(
            Path(),
            w(0.15f), h(0.1f),
            w(0.85f), h(0.1f),
            w(0.85f), h(0.5f),
            w(0.15f), h(0.5f)
        ))
        // Lower diagonal leg
        addPath(setPath(Path(), w(0.25f), h(0.5f), w(0.7f), h(0.9f)))
        return paths
    }

    private fun getS(): ArrayList<Path> {
        paths = arrayListOf()
        // Complete S shape using smooth curves
        addPath(setPathByCubic(
            Path(),
            w(0.8f), h(0.2f),
            w(0.1f), h(0.1f),
            w(0.1f), h(0.4f),
            w(0.5f), h(0.5f)
        ))
        addPath(setPathByCubic(
            Path(),
            w(0.5f), h(0.5f),
            w(0.9f), h(0.6f),
            w(0.9f), h(0.9f),
            w(0.2f), h(0.8f)
        ))
        return paths
    }

    private fun getT(): ArrayList<Path> {
        paths = arrayListOf()
        // Top horizontal (from left to right)
        addPath(setPath(Path(), w(0.1f), h(0.1f), w(0.9f), h(0.1f)))
        // Vertical center line
        addPath(setPath(Path(), w(0.5f), h(0.1f), w(0.5f), h(0.9f)))
        return paths
    }

    private fun getU(): ArrayList<Path> {
        paths = arrayListOf()
        // Smooth U-shaped curve
        addPath(setPathByCubic(
            Path(),
            w(0.15f), h(0.1f),
            w(0.15f), h(0.9f),
            w(0.85f), h(0.9f),
            w(0.85f), h(0.1f)
        ))
        return paths
    }

    private fun getV(): ArrayList<Path> {
        paths = arrayListOf()
        // Left diagonal
        addPath(setPath(Path(), w(0.1f), h(0.1f), w(0.5f), h(0.9f)))
        // Right diagonal
        addPath(setPath(Path(), w(0.5f), h(0.9f), w(0.9f), h(0.1f)))
        return paths
    }

    private fun getW(): ArrayList<Path> {
        paths = arrayListOf()
        // Left diagonal down
        addPath(setPath(Path(), w(0.05f), h(0.1f), w(0.25f), h(0.9f)))
        // Left diagonal up to center
        addPath(setPath(Path(), w(0.25f), h(0.9f), w(0.5f), h(0.35f)))
        // Right diagonal down from center
        addPath(setPath(Path(), w(0.5f), h(0.35f), w(0.75f), h(0.9f)))
        // Right diagonal up
        addPath(setPath(Path(), w(0.75f), h(0.9f), w(0.95f), h(0.1f)))
        return paths
    }

    private fun getX(): ArrayList<Path> {
        paths = arrayListOf()
        // Diagonal from top-left to bottom-right
        addPath(setPath(Path(), w(0.1f), h(0.1f), w(0.9f), h(0.9f)))
        // Diagonal from top-right to bottom-left
        addPath(setPath(Path(), w(0.9f), h(0.1f), w(0.1f), h(0.9f)))
        return paths
    }

    private fun getY(): ArrayList<Path> {
        paths = arrayListOf()
        // Left diagonal to center
        addPath(setPath(Path(), w(0.1f), h(0.1f), w(0.5f), h(0.5f)))
        // Right diagonal to center
        addPath(setPath(Path(), w(0.9f), h(0.1f), w(0.5f), h(0.5f)))
        // Vertical line down from center
        addPath(setPath(Path(), w(0.5f), h(0.5f), w(0.5f), h(0.9f)))
        return paths
    }

    private fun getZ(): ArrayList<Path> {
        paths = arrayListOf()
        // Top horizontal
        addPath(setPath(Path(), w(0.1f), h(0.1f), w(0.9f), h(0.1f)))
        // Diagonal
        addPath(setPath(Path(), w(0.9f), h(0.1f), w(0.1f), h(0.9f)))
        // Bottom horizontal
        addPath(setPath(Path(), w(0.1f), h(0.9f), w(0.9f), h(0.9f)))
        return paths
    }

    // ==================== NUMBERS ====================

    private fun getZero(): ArrayList<Path> {
        paths = arrayListOf()
        // Perfect oval/circle using addOval
        val path = Path()
        path.addOval(
            w(0.15f), h(0.1f),
            w(0.85f), h(0.9f),
            Path.Direction.CW
        )
        addPath(path)
        return paths
    }

    private fun getOne(): ArrayList<Path> {
        paths = arrayListOf()
        // Diagonal top (like a small flag)
        addPath(setPath(Path(), w(0.35f), h(0.25f), w(0.5f), h(0.1f)))
        // Main vertical line
        addPath(setPath(Path(), w(0.5f), h(0.1f), w(0.5f), h(0.9f)))
        return paths
    }


    private fun getTwo(): ArrayList<Path> {
        paths = arrayListOf()
        // Top curve
        addPath(setPathByCubic(
            Path(),
            w(0.15f), h(0.2f),
            w(0.85f), h(0.05f),
            w(0.90f), h(0.4f),
            w(0.15f), h(0.9f)
        ))
        // Bottom horizontal
        addPath(setPath(Path(), w(0.15f), h(0.9f), w(0.8f), h(0.9f)))
        return paths
    }

    private fun getThree(): ArrayList<Path> {
        paths = arrayListOf()
        // Top curve
        addPath(setPathByCubic(
            Path(),
            w(0.18f), h(0.15f),
            w(0.45f), h(0.01f),
            w(0.75f), h(0.3f),
            w(0.25f), h(0.4f)
        ))
        // Bottom curve
        addPath(setPathByCubic(
            Path(),
            w(0.25f), h(0.4f),
            w(0.85f), h(0.3f),
            w(0.85f), h(1f),
            w(0.15f), h(0.8f)
        ))
        return paths
    }

    private fun getFour(): ArrayList<Path> {
        paths = arrayListOf()
        // Vertical line (goes down and continues through crossbar)
        addPath(setPath(Path(), w(0.65f), h(0.1f), w(0.65f), h(0.9f)))
        // Angled line from top to crossbar
        addPath(setPath(Path(), w(0.65f), h(0.1f), w(0.1f), h(0.65f)))
        // Horizontal crossbar
        addPath(setPath(Path(), w(0.1f), h(0.65f), w(0.9f), h(0.65f)))
        return paths
    }

    private fun getFive(): ArrayList<Path> {
        paths = arrayListOf()
        // Top horizontal
        addPath(setPath(Path(), w(0.65f), h(0.1f), w(0.2f), h(0.1f)))
        // Vertical down to middle
        addPath(setPath(Path(), w(0.2f), h(0.1f), w(0.15f), h(0.45f)))
        // Bottom curve (like a J)
        addPath(setPathByCubic(
            Path(),
            w(0.15f), h(0.45f),
            w(0.85f), h(0.2f),
            w(0.85f), h(1f),
            w(0.15f), h(0.8f)
        ))
        return paths
    }
    private fun getSix(): ArrayList<Path> {
        paths = arrayListOf()

        addPath(
            setPathByCubic(
                Path(),
                w(.4f), h(.5f),
                w(.85f), h(0.4f),
                w(.85f), h(1f),
                w(.5f), h(0.95f)
            )
        )

        addPath(
            setPathByCubic(
                Path(),
                w(.5f), h(.95f),
                w(.1f), h(1f),
                w(.2f), h(1f - .9f),
                w(.7f), h(0.2f)
            )
        )

        return paths
    }

    private fun getSeven(): ArrayList<Path> {
        paths = arrayListOf()
        // Top horizontal
        addPath(setPath(Path(), w(0.1f), h(0.1f), w(0.7f), h(0.1f)))
        // Diagonal down (slightly curved for style)
        addPath(setPath(Path(), w(0.7f), h(0.1f), w(0.35f), h(0.9f)))
        return paths
    }
    private fun getEight(): ArrayList<Path> {
        paths = arrayListOf()

        // Top circle
        addPath(
            setArchPath(
                Path(),
                w(0.28f), h(0.14f),
                w(0.72f), h(0.50f),
                70f,
                -320f
            )
        )

        // Bottom circle
        addPath(
            setArchPath(
                Path(),
                w(0.28f), h(0.50f),
                w(0.72f), h(0.85f),
                -90f,
                320f
            )
        )

        return paths
    }

    private fun getNine():ArrayList<Path>{
        paths = arrayListOf()
        addPath(setPathByCubic(
            Path(),
            w(.6f), h(.5f),
            w(.1f), h(.6f),
            w(.1f), h(0f),
            w(.5f), h(.1f)))

        addPath(setPathByCubic(
            Path(),
            w(.5f), h(.1f),
            w(.9f), h(0.15f),
            w(.7f), h(.9f),
            w(.2f), h(.8f)))
        return paths
    }

    private fun getTen(): ArrayList<Path> {
        paths = arrayListOf()
        // "1" - diagonal top
        addPath(setPath(Path(), w(0.15f), h(0.25f), w(0.25f), h(0.1f)))
        // "1" - vertical line
        addPath(setPath(Path(), w(0.25f), h(0.1f), w(0.25f), h(0.9f)))

        // "0" - oval using addOval
        val zeroPath = Path()
        zeroPath.addOval(
            w(0.5f), h(0.25f),
            w(0.95f), h(0.9f),
            Path.Direction.CW
        )
        addPath(zeroPath)

        return paths
    }

    // ==================== HELPER FUNCTIONS ====================
    private fun setArchPath(
        path: Path,
        mx: Float, my: Float,
        ex: Float, ey: Float,
        startAngle: Float,
        sweepAngle: Float
    ): Path {
        path.apply {
            arcTo(mx, my, ex, ey, startAngle, sweepAngle, true)
        }
        return path
    }

    private fun addPath(path: Path) {
        paths.add(path)
    }

    private fun setPathByCubic(
        path: Path,
        mx: Float, my: Float,
        cx1: Float, cy1: Float,
        cx2: Float, cy2: Float,
        ex: Float, ey: Float
    ): Path {
        path.apply {
            moveTo(mx, my)
            cubicTo(cx1, cy1, cx2, cy2, ex, ey)
        }
        return path
    }

    private fun setPath(path: Path, mx: Float, my: Float, ex: Float, ey: Float): Path {
        path.apply {
            moveTo(mx, my)
            lineTo(ex, ey)
        }
        return path
    }
}