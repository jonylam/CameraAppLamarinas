package com.example.cameratestbymike.PoseDetect

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cameratestbymike.Screens.DataViewModel
import com.example.cameratestbymike.StoreData.ForReclaimData
import com.google.firebase.database.FirebaseDatabase

class AnglesViewModel: ViewModel() {
    var angle12_14_16 = mutableStateOf(0.0)
    var angle24_12_14 = mutableStateOf(0.0)
    var angle11_13_15 = mutableStateOf(0.0)
    var angle23_11_13 = mutableStateOf(0.0)
    var angle23_25_27 = mutableStateOf(0.0)
    var angle24_26_28 = mutableStateOf(0.0)
    var angle12_24_26 = mutableStateOf(0.0)
    var angle11_23_25 = mutableStateOf(0.0)

    /**
     * gia elegxo dumple prepei na dw isws gonia 23 11 15, gia na dw an to xeri einai px eu8ia mprosta idia me to kefali h exei klisi pros dexia h aristera.
     */

    var count = mutableStateOf(0)
    var rep = mutableStateOf(false)
    var inbounbs = mutableStateOf(false)
    var stage= mutableStateOf("down")

    var textViewed = mutableStateOf("")
    var textCount = mutableStateOf("")

}
