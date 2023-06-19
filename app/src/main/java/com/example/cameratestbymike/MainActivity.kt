package com.example.cameratestbymike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cameratestbymike.Screens.Navigation
import com.example.cameratestbymike.Screens.DataViewModel
import com.example.cameratestbymike.auth.AuthViewModel
import com.example.cameratestbymike.ui.theme.CameraTestByMikeTheme
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTestByMikeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val authViewModel by viewModels<AuthViewModel>()
                    val databaseReference =  FirebaseDatabase.getInstance().getReference("Matrix")
                    val dataViewModel = viewModel<DataViewModel>()

                    Navigation(authViewModel,databaseReference,dataViewModel)


                }
            }
        }
    }
}

