package com.example.cameratestbymike.User

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserStateViewModel : ViewModel() {
    var role = mutableStateOf("")

    fun setRoleUser(){
        role.value="user"
    }

    fun setRoleHost(){
        role.value="host"
    }
}