package com.homework.writer.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun FontCreatorScreen(
    currentChar: Char,
    savedPaths: List<Path>,
    onCharacterFinished: (List<Path>) -> Unit,
    onNextChar: () -> Unit,
    onPreviousChar: () -> Unit
) {
    val paths = remember(currentChar) { mutableStateListOf<Path>().apply { addAll(savedPaths) } }
    var currentPoints by remember(currentChar) { mutableStateOf<List<Offset>>(emptyList()) }

    fun pointsToPath(points: List<Offset>): Path = Path().apply {
        if (points.isNotEmpty()) {
            moveTo(points.first().x, points.first().y)
            points.drop(1).forEach { lineTo(it.x, it.y) }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Нарисуйте символ: $currentChar",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Нарисовано штрихов: ${paths.size}",
            style = MaterialTheme.typography.bodyMedium
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                .pointerInput(currentChar) {
                    detectDragGestures(
                        onDragStart = { offset -> currentPoints = listOf(offset) },
                        onDrag = { change, _ ->
                            change.consume()
                            currentPoints = currentPoints + change.position
                        },
                        onDragEnd = {
                            if (currentPoints.isNotEmpty()) paths.add(pointsToPath(currentPoints))
                            currentPoints = emptyList()
                        },
                        onDragCancel = { currentPoints = emptyList() }
                    )
                }
                .pointerInput(currentChar) {
                    detectTapGestures(onTap = { point ->
                        paths.add(Path().apply { moveTo(point.x, point.y); lineTo(point.x + 1f, point.y + 1f) })
                    })
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = Color.White.copy(alpha = 0.55f))
                paths.forEach { path ->
                    drawPath(path, color = Color.Black, style = Stroke(width = 8f))
                }
                if (currentPoints.isNotEmpty()) {
                    drawPath(pointsToPath(currentPoints), color = Color.DarkGray, style = Stroke(width = 8f))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    paths.clear()
                    currentPoints = emptyList()
                    onCharacterFinished(emptyList())
                }
            ) { Text("Очистить") }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onCharacterFinished(paths.toList())
                    onPreviousChar()
                }
            ) { Text("← Назад") }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (currentPoints.isNotEmpty()) {
                        paths.add(pointsToPath(currentPoints))
                        currentPoints = emptyList()
                    }
                    onCharacterFinished(paths.toList())
                    onNextChar()
                }
            ) { Text("Далее →") }
        }
    }
}
