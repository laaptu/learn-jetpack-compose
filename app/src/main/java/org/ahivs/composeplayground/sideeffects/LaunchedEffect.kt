package org.ahivs.composeplayground.sideeffects

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun TextComponent(text: String) {
    Text(text = text)
    LaunchedEffect(text) {
        performDelayedOperation(text)
    }
}

suspend fun performDelayedOperation(text: String) {
    delay(3000)
    println("Delayed operation: $text")
}

@Preview
@Composable
fun ChangeTextExample() {
    var randomText by remember { mutableStateOf("") }

    Column {
        TextComponent(text = randomText)
        Button(onClick = { randomText = "Random Number: ${Random.nextInt()}" }) {
            Text("Change Text")
        }
    }
}
