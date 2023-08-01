package org.ahivs.composeplayground.navigation.listnavigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import org.ahivs.composeplayground.navigation.models.Shift

class ShiftListActivity : AppCompatActivity() {
    private val shifts = listOf(
        Shift(1, "Shift 1", "This is the description for Shift 1"),
        Shift(2, "Shift 2", "This is the description for Shift 2"),
        Shift(3, "Shift 3", "This is the description for Shift 3"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            LazyColumn {
                items(shifts) { shift ->
                    Button(onClick = {
                        // Launch the ShiftDetails activity and pass the selected shift
                        val intent = Intent(context, ShiftDetailsActivity::class.java)
                        intent.putExtra("shift", shift)
                        startActivity(intent)
                    }) {
                        Text(text = shift.title)
                    }
                }
            }
        }
    }
}