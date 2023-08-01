package org.ahivs.composeplayground.sideeffects.disposableeffects

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun TextComponent(text: String) {
    Text(text = text)
    val localView: View = LocalView.current
    DisposableEffect(text) {
        val clickListener = View.OnClickListener {
            println("### Clicked on view with text = $text")
        }
        localView.setOnClickListener(clickListener)
        println("### Set up click listener for view with text = $text")
        onDispose {
            // This will called first time when the composable is disposed
            println("### Disposed view with text = $text")
            localView.setOnClickListener(null)
        }
    }
}


@Preview
@Composable
fun ChangeTextExampleWithDisposable() {
    var randomText by remember { mutableStateOf("") }

    Column {
        TextComponent(text = randomText)
        Button(onClick = { randomText = "Random Number: ${Random.nextInt()}" }) {
            Text("Change Text")
        }
    }
}
