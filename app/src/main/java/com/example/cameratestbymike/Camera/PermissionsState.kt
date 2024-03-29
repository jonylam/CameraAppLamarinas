package com.example.cameratestbymike.Camera

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied() : Boolean {
    return !shouldShowRationale && !hasPermission
}