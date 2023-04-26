package com.example.cameratestbymike.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun addElement(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var accurancy by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Give Name For Exercise")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = {newName -> name = newName },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Give Accurancy (ex. 70%)")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = accurancy,
            onValueChange = {newAcc -> accurancy = newAcc },
            label = { Text("Accurancy") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("camera_screen/$name/$accurancy")
        }) {
            Text("Start")
        }
    }
}