package com.homework.writer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
    val noteTitle = remember { mutableStateOf("Конспект по теме: ${currentChar}") }
    val noteSubject = remember { mutableStateOf("Математика / История / Программирование") }
    val noteTemplate = remember { mutableStateOf("Кратко") }
    val noteBullets = remember {
        mutableStateListOf(
            "Ключевая идея: тема раскрывается через основные доказательства.",
            "Главный вывод: процесс идёт быстрее при системной структуре.",
            "Практика: закрепляем материал на примерах и ответах."
        )
    }

    val previewText = remember {
        mutableStateOf(
            "Тема: ${noteSubject.value}\n\n" +
                "Ключевой тезис: материал важен для подготовки к контрольной работе.\n\n" +
                "1. Понимание основ.\n" +
                "2. Применение на примерах.\n" +
                "3. Проверка и закрепление."
        )
    }

    fun buildPreview(): String {
        val bullets = noteBullets.joinToString(separator = "\n") { "• $it" }
        return buildString {
            append("Тема: ${noteSubject.value}\n")
            append("Заголовок: ${noteTitle.value}\n\n")
            append("Формат: ${noteTemplate.value}\n\n")
            append("Основные пункты:\n")
            append(bullets)
            append("\n\n")
            append("Вывод: материал достаточно понятен, чтобы быстро повторить и подготовиться к проверке.")
        }
    }

    fun regeneratePreview() {
        previewText.value = buildPreview()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Генератор фейковых конспектов",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = noteTitle.value,
                    onValueChange = { noteTitle.value = it },
                    label = { Text("Название конспекта") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = noteSubject.value,
                    onValueChange = { noteSubject.value = it },
                    label = { Text("Тема / предмет") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = noteTemplate.value,
                    onValueChange = { noteTemplate.value = it },
                    label = { Text("Формат: Кратко / Развернуто / План") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Text(
            text = "Пункты конспекта",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(noteBullets.toList()) { index, item ->
                OutlinedTextField(
                    value = item,
                    onValueChange = { newValue ->
                        noteBullets[index] = newValue
                        regeneratePreview()
                    },
                    label = { Text("Пункт ${index + 1}") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { regeneratePreview() }
            ) {
                Text("Сгенерировать")
            }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    noteTitle.value = "Новый конспект"
                    noteSubject.value = "Публичный доклад"
                    noteTemplate.value = "Кратко"
                    noteBullets.clear()
                    noteBullets.addAll(
                        listOf(
                            "Ключевой тезис: материал должен быть понятен сразу.",
                            "Доказательство: используем факты и примеры.",
                            "Практика: разобрать вопросы в конце блока."
                        )
                    )
                    regeneratePreview()
                }
            ) {
                Text("Сброс")
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Предпросмотр",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = previewText.value,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    onCharacterFinished(emptyList())
                    onPreviousChar()
                }
            ) {
                Text("← Назад")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    onCharacterFinished(emptyList())
                    onNextChar()
                }
            ) {
                Text("Далее →")
            }
        }
    }
}
