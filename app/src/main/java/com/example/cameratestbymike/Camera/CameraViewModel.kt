package com.example.cameratestbymike.Camera

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class CameraViewModel : ViewModel() {
    var timeBeforePoseDetect= mutableStateOf(10)
    var timeAfterPoseDetect= mutableStateOf(10)

    var restartTrigger = mutableStateOf(false)
    var saveTrigger= mutableStateOf(false)

    var matrixTrigger= mutableStateOf(false)
    val matrix = Array(8) { DoubleArray(2) }

    //change time Before poseDetect
    fun setTbefore(time: Int) {
        timeBeforePoseDetect.value = time
    }

    //change time After poseDetect
    fun setTafter(time: Int) {
        timeAfterPoseDetect.value = time
    }

    //Change Both time After and Before poseDetect
    fun setTafterTbefore(timeA: Int,timeB: Int){
        timeBeforePoseDetect.value = timeB
        timeAfterPoseDetect.value = timeA
    }

    // Call this function when the Restart button is clicked
    fun Restart() {
        restartTrigger.value = true
    }

    // Call this function to reset the restart state variable
    fun resetRestart() {
        restartTrigger.value = false
    }

    fun Save(){
        saveTrigger.value=true
    }

    fun resetSave(){
        saveTrigger.value=false
    }

    //Set Start Values to Matrix which contains Max values of the 8 Angles in 1st column and Min values in the 2nd Column
    fun DeclareMinMaxTable(){
        for (i in 0..7)
        {
            for(j in 0..1)
            {

                //max value
                matrix[i][0]=Double.MIN_VALUE
                //min value
                matrix[i][1]=Double.MAX_VALUE

            }
        }
    }


    fun showMatrix(){
        for (i in 0..7)
        {
            Log.d("matrix","$i Row--->MIN: ${matrix[i][1]}, Max: ${matrix[i][0]}")
        }
    }

}