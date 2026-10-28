package com.homework.writer.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun FontCreatorScreen(
    currentChar: Char,
    onCharacterFinished: (List<Path>) -> Unit,
    onNextChar: () -> Unit
) {
    val paths = remember { mutableStateListOf<Path>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Нарисуйте символ: $currentChar",
            style = MaterialTheme.typography.headlineMedium
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                .pointerInput(currentChar) { // Сбрасываем жест при смене буквы
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentPath = Path().apply {
                                moveTo(offset.x, offset.y)
                            }
                        },
                        onDrag = { change, _ ->
                            currentPath?.lineTo(change.position.x, change.position.y)
                        },
                        onDragEnd = {
                            currentPath?.let {
                                paths.add(it)
                                currentPath = null
                            }
                        },
                        onDragCancel = {
                            currentPath = null
                        }
                    )
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = Color.LightGray.copy(alpha = 0.3f))
                
                for (path in paths) {
                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke(width = 8f)
                    )
                }
                currentPath?.let {
                    drawPath(
                        path = it,
                        color = Color.DarkGray,
                        style = Stroke(width = 8f)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { 
                paths.clear()
                currentPath = null
            }) {
                Text("Очистить")
            }
            Button(onClick = {
                onCharacterFinished(paths.toList())
                paths.clear()
                currentPath = null
                onNextChar()
            }) {
                Text("Далее ➔")
            }
        }
    }
}
