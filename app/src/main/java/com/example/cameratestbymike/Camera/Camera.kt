package com.example.cameratestbymike.Camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import android.Manifest
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cameratestbymike.auth.AuthViewModel
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.database.DatabaseReference


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun camera(
    viewModel: AuthViewModel,
    databaseReference: DatabaseReference,
    newItemName: String,
    Accurancy: String,
    navController: NavHostController
){

        val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(
            key1 = lifecycleOwner,
            effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if(event == Lifecycle.Event.ON_START) {
                        permissionState.launchPermissionRequest()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)

                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
        )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            permissionState.hasPermission -> {
                val cameraViewModel = viewModel<CameraViewModel>()
                CameraView(viewModel,cameraViewModel,databaseReference,newItemName,Accurancy,navController)
            }
            permissionState.shouldShowRationale -> {
                Text(text = "Camera permission is needed " +
                        "to access the camera")
            }
            permissionState.isPermanentlyDenied() -> {
                Text(text = "Camera permission was permanently" +
                        "denied. You can enable it in the app" +
                        "settings.")
            }
        }
    }

}