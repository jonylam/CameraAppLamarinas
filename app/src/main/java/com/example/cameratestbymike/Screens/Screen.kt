package com.example.cameratestbymike.Screens

sealed class Screen(val route:String){
    object MainScreen: Screen("main_screen")
    object CameraScreen:Screen("camera_screen")
    object AddElement:Screen("add_screen")
    object DeleteElement:Screen("delete_screen")
    object CameraUser:Screen("camera_asUser")
}
