package com.example.cameratestbymike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cameratestbymike.Screens.Navigation
import com.example.cameratestbymike.Screens.DataViewModel
import com.example.cameratestbymike.User.UserStateViewModel
import com.example.cameratestbymike.ui.theme.CameraTestByMikeTheme
import com.google.firebase.database.FirebaseDatabase


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

                    val databaseReference =  FirebaseDatabase.getInstance().getReference("Matrix")
                    val dataViewModel = viewModel<DataViewModel>()
                    val userStateViewModel = viewModel<UserStateViewModel>()
                    userStateViewModel.setRoleUser()


                    Navigation(databaseReference,userStateViewModel,dataViewModel)

                    //PreviewCards(viewModelMain,databaseReference)

                    //data.show(data.getList())

                    //camera(databaseReference,"iu3")

                   /* val viewModelMain = viewModel<mainScreenViewModel>()
                    viewModelMain.getdatafromdatabase(databaseReference)

                    if (viewModelMain.getData.value) {
                        for (list in viewModelMain.totallist) {
                            val angleStrings = list.angles.joinToString(", ") { "${it.max}/${it.min}" }
                            Log.d("TotalList", "${list.name}: $angleStrings")
                        }
                    }*/


                }
            }
        }
    }
}

