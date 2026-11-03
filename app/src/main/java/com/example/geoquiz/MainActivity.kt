package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun QuizScreen(modifier: Modifier = Modifier) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var correctCount by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    val question = questionBank[currentIndex]

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Индикатор прогресса
            Text("Вопрос ${currentIndex + 1} из ${questionBank.size}", fontSize = 16.sp)
            Spacer(Modifier.height(16.dp))

            // Текст вопроса
            Text(
                text = question.text,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(Modifier.height(16.dp))
            // Кнопки True / False — скрываются после ответа
            if (!answered) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = {
                        answered = true
                        if (question.answer) correctCount++
                    }) { Text("True") }

                    Button(onClick = {
                        answered = true
                        if (!question.answer) correctCount++
                    }) { Text("False") }
                }
            } else {
                // Показываем, что ответ дан
                Text("Ответ принят", fontSize = 14.sp)
            }

            Spacer(Modifier.height(24.dp))

            // Кнопка Next — скрывается на последнем вопросе
            if (currentIndex < questionBank.size - 1) {
                Button(
                    onClick = {
                        currentIndex++
                        answered = false
                    }
                ) { Text("Next") }
            } else if (answered) {
                // На последнем вопросе после ответа Next скрыт
                Text("Это был последний вопрос", fontSize = 14.sp)
            }

            // Финальная проверка (пока выведем текстом)
            if (answered && currentIndex == questionBank.size - 1) {
                Text("Правильных ответов: $correctCount из ${questionBank.size}")
            }
        }
    }
}