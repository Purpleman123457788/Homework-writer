package com.homework.writer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import com.homework.writer.ui.screens.FontCreatorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val alphabet = remember { "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".toList() }
                    val currentIndex = remember { mutableIntStateOf(0) }
                    val completedCharacters = remember { mutableStateMapOf<Char, List<Path>>() }

                    FontCreatorScreen(
                        currentChar = alphabet[currentIndex.intValue],
                        savedPaths = completedCharacters[alphabet[currentIndex.intValue]].orEmpty(),
                        onCharacterFinished = { paths ->
                            completedCharacters[alphabet[currentIndex.intValue]] = paths
                        },
                        onNextChar = {
                            currentIndex.intValue = (currentIndex.intValue + 1) % alphabet.size
                        },
                        onPreviousChar = {
                            currentIndex.intValue = (currentIndex.intValue - 1 + alphabet.size) % alphabet.size
                        }
                    )
                }
            }
        }
    }
}
