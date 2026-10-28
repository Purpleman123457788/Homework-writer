package com.homework.writer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
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
                    val characters = listOf('А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'К')
                    var currentIndex by remember { mutableStateOf(0) }
                    val charPathsMap = remember { mutableMapOf<Char, List<Path>>() }

                    FontCreatorScreen(
                        currentChar = characters[currentIndex],
                        onCharacterFinished = { paths ->
                            charPathsMap[characters[currentIndex]] = paths
                        },
                        onNextChar = {
                            if (currentIndex < characters.size - 1) {
                                currentIndex++
                            } else {
                                currentIndex = 0 
                            }
                        }
                    )
                }
            }
        }
    }
}
