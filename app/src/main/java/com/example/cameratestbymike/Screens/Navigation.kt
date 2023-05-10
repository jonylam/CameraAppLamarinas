package com.example.cameratestbymike.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cameratestbymike.Camera.camera
import com.example.cameratestbymike.User.UserStateViewModel
import com.example.cameratestbymike.auth.AuthViewModel
import com.example.cameratestbymike.auth.LoginScreen
import com.example.cameratestbymike.auth.SignupScreen
import com.google.firebase.database.DatabaseReference

@Composable
fun Navigation(viewModel: AuthViewModel, databaseReference: DatabaseReference, userStateViewModel:UserStateViewModel, dataViewModel: DataViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(route = Screen.MainScreen.route){
            PreviewCards(viewModel,dataViewModel,userStateViewModel,databaseReference,navController)
        }
        composable(
            route = Screen.CameraScreen.route + "/{exName}/{accurancy}",
            arguments= listOf(
                navArgument("exName"){
                    type= NavType.StringType
                },
                navArgument("accurancy"){
                    type= NavType.StringType
                }
            )
        ){entry->
            camera(
                viewModel,
                databaseReference = databaseReference,
                newItemName = entry.arguments?.getString("exName")!!,
                Accurancy = entry.arguments?.getString("accurancy")!!,
                navController)
        }
        composable(route = Screen.AddElement.route){
            addElement(navController)
        }
        composable(route = Screen.DeleteElement.route){
            deleteElement(navController)
        }
        composable(route = Screen.Login.route){
            LoginScreen(viewModel, navController)
        }
        composable(route = Screen.SignUp.route){
            SignupScreen(viewModel, navController)
        }
    }
}