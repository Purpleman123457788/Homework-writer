package com.homework.writer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun FontCreatorScreen(
    currentChar: Char,
    savedPaths: List<Path>,
    onCharacterFinished: (List<Path>) -> Unit,
    onNextChar: () -> Unit,
    onPreviousChar: () -> Unit
) {
    var inputValue by remember(currentChar) { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Введите символ: $currentChar",
            style = MaterialTheme.typography.headlineMedium
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = inputValue,
                onValueChange = { value ->
                    inputValue = value.take(1)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Символ") }
            )

            Text(
                text = "Предпросмотр: ${inputValue.ifEmpty { currentChar }}",
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    inputValue = ""
                    onCharacterFinished(emptyList())
                }
            ) { Text("Сброс") }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onCharacterFinished(emptyList())
                    onPreviousChar()
                }
            ) { Text("← Назад") }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    onCharacterFinished(emptyList())
                    onNextChar()
                }
            ) { Text("Далее →") }
        }
    }
}
