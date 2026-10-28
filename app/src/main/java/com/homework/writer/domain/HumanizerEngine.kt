package com.homework.writer.domain

import android.graphics.Paint
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.random.Random

class HumanizerEngine(
    private val jitterIntensity: Float = 2f,
    private val angleJitter: Float = 0.03f
) {
    fun drawHumanizedText(
        canvas: Canvas,
        text: String,
        startX: Float,
        startY: Float,
        basePaint: Paint,
        lineHeight: Float
    ) {
        var currentX = startX
        var currentY = startY
        val rnd = Random(42)

        for (char in text) {
            if (char == '\n') {
                currentX = startX
                currentY += lineHeight
                continue
            }

            val offsetX = (rnd.nextFloat() - 0.5f) * jitterIntensity
            val offsetY = (rnd.nextFloat() - 0.5f) * jitterIntensity
            
            canvas.nativeCanvas.save()
            val rotation = (rnd.nextFloat() - 0.5f) * angleJitter
            canvas.nativeCanvas.rotate(rotation * 57.2958f, currentX + offsetX, currentY + offsetY)

            canvas.nativeCanvas.drawText(
                char.toString(),
                currentX + offsetX,
                currentY + offsetY,
                basePaint
            )
            
            canvas.nativeCanvas.restore()
            currentX += basePaint.measureText(char.toString()) + (rnd.nextFloat() * 1.5f)
        }
    }
}
