package org.ahivs.composeplayground.sideeffects.remembercoroutinescope

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun TextComponent(text: String) {
    Text(text = text)
    val coroutineScope = rememberCoroutineScope()
    // the coroutinescope will not be cancelled during recomposition
    // but only cancelled when it leaves the composition
    coroutineScope.launch {
        performDelayedOperation(text)
    }
}

suspend fun performDelayedOperation(text: String) {
    delay(3000)
    println("## Delayed operation: $text")
}

@Preview
@Composable
fun ChangeTextExampleCoroutineScope() {
    var randomText by remember { mutableStateOf("") }

    Column {
        TextComponent(text = randomText)
        Button(onClick = { randomText = "Random Number: ${Random.nextInt()}" }) {
            Text("Change Text")
        }
    }
}

@Preview
@Composable
fun ChangeTextExampleRemovingComposition() {
    var randomText by remember { mutableStateOf("") }
    var times by remember {
        mutableStateOf(0)
    }
    println("### Entering ChangeTextExample for text = $randomText")
    Column {
        // here we are removing the TextComponent from composition
        // at that time , the couroutineScope will be cancelled of TextComponent
        if (times < 3) {
            TextComponent(text = randomText)
        }
        Button(onClick = {
            randomText = "Random Number: ${Random.nextInt()}"
            times++
        }) {
            Text("Change Text")
        }
    }
}

