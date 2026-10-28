package com.homework.writer.domain

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.random.Random

/** Draws text with subtle, repeatable variations in position and rotation. */
class HumanizerEngine(
    private val jitterIntensity: Float = 2f,
    private val angleJitterDegrees: Float = 1.5f,
    private val seed: Int = 42
) {
    fun drawHumanizedText(
        canvas: Canvas,
        text: String,
        startX: Float,
        startY: Float,
        basePaint: Paint,
        lineHeight: Float
    ) {
        val random = Random(seed)
        var x = startX
        var y = startY

        for (character in text) {
            if (character == '\n') {
                x = startX
                y += lineHeight
                continue
            }

            val offsetX = (random.nextFloat() - 0.5f) * jitterIntensity
            val offsetY = (random.nextFloat() - 0.5f) * jitterIntensity
            val rotation = (random.nextFloat() - 0.5f) * 2f * angleJitterDegrees

            canvas.save()
            canvas.rotate(rotation, x + offsetX, y + offsetY)
            canvas.drawText(character.toString(), x + offsetX, y + offsetY, basePaint)
            canvas.restore()

            x += basePaint.measureText(character.toString()) + random.nextFloat() * 1.5f
        }
    }
}
