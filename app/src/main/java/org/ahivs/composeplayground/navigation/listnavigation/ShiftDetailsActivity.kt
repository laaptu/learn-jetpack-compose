package org.ahivs.composeplayground.navigation.listnavigation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.ahivs.composeplayground.navigation.models.Shift

class ShiftDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val shift = intent.getParcelableExtra<Shift>("shift")!!

            val navController = rememberNavController()

            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(coroutineScope) {
                navController.currentBackStackEntryFlow.collectLatest {
                    val description: String? =
                        it.savedStateHandle["description"]
                    println("### route = ${it.destination.route}")
                    if (description != null) {
                        //it.savedStateHandle["description"] = null
                        delay(1500)
                    }
                    println("###Description = $description")
                }
            }

            NavHost(navController = navController, startDestination = "shift_details") {
                composable("shift_details") {
                    ShiftDetailsUI(shift, navController)
                }
                composable("shift_description/{description}") {
                    val description =
                        navController.currentBackStackEntry?.arguments?.getString("description")
                            ?: ""
                    val editedDescription = remember { mutableStateOf("") }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Description")
                        Text(text = description)
                        TextField(
                            value = editedDescription.value,
                            onValueChange = { editedDescription.value = it }
                        )
                        Button(onClick = {
                            // Pass the edited description back to ShiftDetailsUI
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "description",
                                editedDescription.value
                            )
                            navController.popBackStack()
                        }) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ShiftDetailsUI(shift: Shift, navController: NavController) {
        val description =
            navController.currentBackStackEntry?.savedStateHandle?.get("description")
                ?: shift.description
        println("### ShiftDetailsUI description = $description")

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = shift.title)
            Text(text = description)
            Button(onClick = {
                // Navigate to the ShiftDescription screen and pass the description as an argument
                navController.navigate(
                    "shift_description/${description}",
                )
            }) {
                Text(text = "View Description")
            }
        }
    }

}