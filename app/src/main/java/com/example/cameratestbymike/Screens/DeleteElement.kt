package com.example.cameratestbymike.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun deleteElement(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Give Name From Exercise You Want To Delete")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = {newName -> name = newName },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            //navController.navigate("camera_screen/$name")
            /**
             * edw prepei na kanw kapws na kanei delete otan dinw to name
             */
        }) {
            Text("Delete")
        }
    }
}