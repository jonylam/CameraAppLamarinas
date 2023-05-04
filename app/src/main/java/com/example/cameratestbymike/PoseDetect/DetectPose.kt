package com.example.cameratestbymike.PoseDetect


import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cameratestbymike.Camera.CameraViewModel
import com.example.cameratestbymike.Camera.SourceInfo
import com.example.cameratestbymike.Screens.DataViewModel
import com.example.cameratestbymike.StoreData.ForReclaimData
import com.example.cameratestbymike.User.Comparation
import com.example.cameratestbymike.User.UserStateViewModel
import com.google.firebase.database.DatabaseReference
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark

import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Composable
fun DetectedPose(
    pose: Pose?,
    sourceInfo: SourceInfo,
    cameraViewModel: CameraViewModel,
    userStateViewModel: UserStateViewModel,
    list:List<ForReclaimData>,
    exName:String,
    anglesViewModel: AnglesViewModel,
    accurancy:String,
    databaseReference:DatabaseReference,
    dataViewModel:DataViewModel
) {

    if (pose != null) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 1.dp.toPx()
            val whitePaint = SolidColor(Color.White)
            val leftPaint = SolidColor(Color.Green)
            val rightPaint = SolidColor(Color.Yellow)

            //bazw egw
            val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
            val leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
            val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
            val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
            val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
            val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
            val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
            val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
            val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
            val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
            val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
            //bazw egw



            val needToMirror = sourceInfo.isImageFlipped
            val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
            val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
            val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
            val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
            val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
            val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
            val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
            val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
            val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
            val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
            val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
            val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

            val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
            val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
            val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
            val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
            val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
            val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
            val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
            val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
            val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
            val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

            fun drawLine(
                startLandmark: PoseLandmark?,
                endLandmark: PoseLandmark?,
                paint: Brush
            ) {
                if (startLandmark != null && endLandmark != null) {
                    val startX =
                        if (needToMirror) size.width - startLandmark.position.x else startLandmark.position.x
                    val startY = startLandmark.position.y
                    val endX =
                        if (needToMirror) size.width - endLandmark.position.x else endLandmark.position.x
                    val endY = endLandmark.position.y
                    drawLine(
                        brush = paint,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = strokeWidth,
                    )
                }
            }





            //bazw egw
            drawLine(nose, leftEyeInner, whitePaint)
            drawLine( leftEyeInner, leftEye, whitePaint)
            drawLine( leftEye, leftEyeOuter, whitePaint)
            drawLine(leftEyeOuter, leftEar, whitePaint)
            drawLine( nose, rightEyeInner, whitePaint)
            drawLine( rightEyeInner, rightEye, whitePaint)
            drawLine( rightEye, rightEyeOuter, whitePaint)
            drawLine(rightEyeOuter, rightEar, whitePaint)
            drawLine( leftMouth, rightMouth, whitePaint)
            //mexri edw

            drawLine(leftShoulder, rightShoulder, whitePaint)
            drawLine(leftHip, rightHip, whitePaint)
            // Left body
            drawLine(leftShoulder, leftElbow, leftPaint)
            drawLine(leftElbow, leftWrist, leftPaint)
            drawLine(leftShoulder, leftHip, leftPaint)
            drawLine(leftHip, leftKnee, leftPaint)
            drawLine(leftKnee, leftAnkle, leftPaint)
            drawLine(leftWrist, leftThumb, leftPaint)
            drawLine(leftWrist, leftPinky, leftPaint)
            drawLine(leftWrist, leftIndex, leftPaint)
            drawLine(leftIndex, leftPinky, leftPaint)
            drawLine(leftAnkle, leftHeel, leftPaint)
            drawLine(leftHeel, leftFootIndex, leftPaint)
            // Right body
            drawLine(rightShoulder, rightElbow, rightPaint)
            drawLine(rightElbow, rightWrist, rightPaint)
            drawLine(rightShoulder, rightHip, rightPaint)
            drawLine(rightHip, rightKnee, rightPaint)
            drawLine(rightKnee, rightAnkle, rightPaint)
            drawLine(rightWrist, rightThumb, rightPaint)
            drawLine(rightWrist, rightPinky, rightPaint)
            drawLine(rightWrist, rightIndex, rightPaint)
            drawLine(rightIndex, rightPinky, rightPaint)
            drawLine(rightAnkle, rightHeel, rightPaint)
            drawLine(rightHeel, rightFootIndex, rightPaint)


            if(userStateViewModel.role.value=="host") {
                anglesViewModel.angle12_14_16.value = getAngle(rightShoulder, rightElbow, rightWrist)

                //MAX gwnias
                if (anglesViewModel.angle12_14_16.value > cameraViewModel.matrix[0][0]) {
                    cameraViewModel.matrix[0][0] = anglesViewModel.angle12_14_16.value
                    Log.d("TOTALMAX", "Max-Agkona-Right:${cameraViewModel.matrix[0][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle12_14_16.value < cameraViewModel.matrix[0][1] && cameraViewModel.matrix[0][0] != Double.MIN_VALUE) {
                    cameraViewModel.matrix[0][1] = anglesViewModel.angle12_14_16.value
                    Log.d("TOTALMIN", "Min-Agkona-Right:${cameraViewModel.matrix[0][1]}")
                }


                //gwnia dexia masxalis
                anglesViewModel.angle24_12_14.value = getAngle(rightHip, rightShoulder, rightElbow)

                //MAX gwnias
                if (anglesViewModel.angle24_12_14.value > cameraViewModel.matrix[1][0]) {
                    cameraViewModel.matrix[1][0] = anglesViewModel.angle24_12_14.value
                    Log.d("TOTALMAX", "Max-Masxali-Right:${cameraViewModel.matrix[1][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle24_12_14.value < cameraViewModel.matrix[1][1] && cameraViewModel.matrix[1][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[1][1] = anglesViewModel.angle24_12_14.value
                    Log.d("TOTALMIN", "Min-Masxali-Right:${cameraViewModel.matrix[1][1]}")
                }

                //gwnia aristera mesa agkona
                anglesViewModel.angle11_13_15.value = getAngle(leftShoulder, leftElbow, leftWrist)

                //MAX gwnias
                if (anglesViewModel.angle11_13_15.value > cameraViewModel.matrix[2][0]) {
                    cameraViewModel.matrix[2][0] = anglesViewModel.angle11_13_15.value
                    Log.d("TOTALMΑΧ", "Max-Agkona-Left:${cameraViewModel.matrix[2][0]}")
                    Log.d("TESTMPIKELEFT", "MPIKE left agkona: ${cameraViewModel.matrix[2][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle11_13_15.value < cameraViewModel.matrix[2][1] && cameraViewModel.matrix[2][0] != Double.MIN_VALUE) {
                    cameraViewModel.matrix[2][1] = anglesViewModel.angle11_13_15.value
                    Log.d("TOTALMIN", "Min-Agkona-Left:${cameraViewModel.matrix[2][1]}")
                }

                //gwnia aristera masxalis
                anglesViewModel.angle23_11_13.value = getAngle(leftHip, leftShoulder, leftElbow)

                //MAX gwnias
                if (anglesViewModel.angle23_11_13.value > cameraViewModel.matrix[3][0]) {
                    cameraViewModel.matrix[3][0] = anglesViewModel.angle23_11_13.value
                    Log.d("TOTALMΑΧ", "Max-Masxali-Left:${cameraViewModel.matrix[3][0]}")
                    Log.d("TESTMPIKELEFT", "MPIKE left masxali: ${cameraViewModel.matrix[3][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle23_11_13.value < cameraViewModel.matrix[3][1] && cameraViewModel.matrix[3][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[3][1] = anglesViewModel.angle23_11_13.value
                    Log.d("TOTALMIN", "Min-Masxali-Left:${cameraViewModel.matrix[3][1]}")
                }

                //gwnia gonato aristero
                anglesViewModel.angle23_25_27.value = getAngle(leftHip, leftKnee, leftAnkle)

                //MAX gwnias
                if (anglesViewModel.angle23_25_27.value > cameraViewModel.matrix[4][0]) {
                    cameraViewModel.matrix[4][0] = anglesViewModel.angle23_25_27.value
                    Log.d("TOTALMΑΧ", "Max-Gonato-Left:${cameraViewModel.matrix[4][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle23_25_27.value < cameraViewModel.matrix[4][1] && cameraViewModel.matrix[4][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[4][1] = anglesViewModel.angle23_25_27.value
                    Log.d("TOTALMIN", "Min-Gonato-Left:${cameraViewModel.matrix[4][1]}")
                }

                //gwnia gonato dexi
                anglesViewModel.angle24_26_28.value = getAngle(rightHip, rightKnee, rightAnkle)

                //MAX gwnias
                if (anglesViewModel.angle24_26_28.value > cameraViewModel.matrix[5][0]) {
                    cameraViewModel.matrix[5][0] = anglesViewModel.angle24_26_28.value
                    Log.d("TOTALMΑΧ", "Max-Gonato-Right:${cameraViewModel.matrix[5][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle24_26_28.value < cameraViewModel.matrix[5][1] && cameraViewModel.matrix[5][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[5][1] = anglesViewModel.angle24_26_28.value
                    Log.d("TOTALMIN", "Min-Gonato-Right:${cameraViewModel.matrix[5][1]}")
                }

                //gwnia dexi lekani
                anglesViewModel.angle12_24_26.value = getAngle(rightShoulder, rightHip, rightKnee)

                //MAX gwnias
                if (anglesViewModel.angle12_24_26.value > cameraViewModel.matrix[6][0]) {
                    cameraViewModel.matrix[6][0] = anglesViewModel.angle12_24_26.value
                    Log.d("TOTALMΑΧ", "Max-Lekani-Right:${cameraViewModel.matrix[6][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle12_24_26.value < cameraViewModel.matrix[6][1] && cameraViewModel.matrix[6][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[6][1] = anglesViewModel.angle12_24_26.value
                    Log.d("TOTALMIN", "Min-Lekani-Right:${cameraViewModel.matrix[6][1]}")
                }

                //gwnia aristeri lekani
                anglesViewModel.angle11_23_25.value = getAngle(leftShoulder, leftHip, leftKnee)

                //MAX gwnias
                if (anglesViewModel.angle11_23_25.value > cameraViewModel.matrix[7][0]) {
                    cameraViewModel.matrix[7][0] = anglesViewModel.angle11_23_25.value
                    Log.d("TOTALMΑΧ", "Max-Lekani-Left:${cameraViewModel.matrix[7][0]}")
                }

                //MIN gwnias
                if (anglesViewModel.angle11_23_25.value < cameraViewModel.matrix[7][1] && cameraViewModel.matrix[7][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[7][1] = anglesViewModel.angle11_23_25.value
                    Log.d("TOTALMIN", "Min-Lekani-Left:${cameraViewModel.matrix[7][1]}")
                }
            }
            else{
                //anglesViewModel.angle12_14_16.value = 60.00

                anglesViewModel.angle12_14_16.value = getAngle(rightShoulder, rightElbow, rightWrist)
                anglesViewModel.angle24_12_14.value = getAngle(rightHip, rightShoulder, rightElbow)
                anglesViewModel.angle11_13_15.value = getAngle(leftShoulder, leftElbow, leftWrist)
                anglesViewModel.angle23_11_13.value = getAngle(leftHip, leftShoulder, leftElbow)
                anglesViewModel.angle23_25_27.value = getAngle(leftHip, leftKnee, leftAnkle)
                anglesViewModel.angle24_26_28.value = getAngle(rightHip, rightKnee, rightAnkle)
                anglesViewModel.angle12_24_26.value = getAngle(rightShoulder, rightHip, rightKnee)
                anglesViewModel.angle11_23_25.value = getAngle(leftShoulder, leftHip, leftKnee)

            }

            /**
             * litoyrgei auto apla paw na allaksw tis gwnies se viewmodel wste na dw an mporw na to epeksergasto alliws me call.

            if(userStateViewModel.role.value=="host") {
                val angle12_14_16 = getAngle(rightShoulder, rightElbow, rightWrist)

                //MAX gwnias
                if (angle12_14_16 > cameraViewModel.matrix[0][0]) {
                    cameraViewModel.matrix[0][0] = angle12_14_16
                    Log.d("TOTALMAX", "Max-Agkona-Right:${cameraViewModel.matrix[0][0]}")
                }

                //MIN gwnias
                if (angle12_14_16 < cameraViewModel.matrix[0][1] && cameraViewModel.matrix[0][0] != Double.MIN_VALUE) {
                    cameraViewModel.matrix[0][1] = angle12_14_16
                    Log.d("TOTALMIN", "Min-Agkona-Right:${cameraViewModel.matrix[0][1]}")
                }


                //gwnia dexia masxalis
                val angle24_12_14 = getAngle(rightHip, rightShoulder, rightElbow)

                //MAX gwnias
                if (angle24_12_14 > cameraViewModel.matrix[1][0]) {
                    cameraViewModel.matrix[1][0] = angle24_12_14
                    Log.d("TOTALMAX", "Max-Masxali-Right:${cameraViewModel.matrix[1][0]}")
                }

                //MIN gwnias
                if (angle24_12_14 < cameraViewModel.matrix[1][1] && cameraViewModel.matrix[1][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[1][1] = angle24_12_14
                    Log.d("TOTALMIN", "Min-Masxali-Right:${cameraViewModel.matrix[1][1]}")
                }

                //gwnia aristera mesa agkona
                val angle11_13_15 = getAngle(leftShoulder, leftElbow, leftWrist)

                //MAX gwnias
                if (angle11_13_15 > cameraViewModel.matrix[2][0]) {
                    cameraViewModel.matrix[2][0] = angle11_13_15
                    Log.d("TOTALMΑΧ", "Max-Agkona-Left:${cameraViewModel.matrix[2][0]}")
                    Log.d("TESTMPIKELEFT", "MPIKE left agkona: ${cameraViewModel.matrix[2][0]}")
                }

                //MIN gwnias
                if (angle11_13_15 < cameraViewModel.matrix[2][1] && cameraViewModel.matrix[2][0] != Double.MIN_VALUE) {
                    cameraViewModel.matrix[2][1] = angle11_13_15
                    Log.d("TOTALMIN", "Min-Agkona-Left:${cameraViewModel.matrix[2][1]}")
                }

                //gwnia aristera masxalis
                val angle23_11_13 = getAngle(leftHip, leftShoulder, leftElbow)

                //MAX gwnias
                if (angle23_11_13 > cameraViewModel.matrix[3][0]) {
                    cameraViewModel.matrix[3][0] = angle23_11_13
                    Log.d("TOTALMΑΧ", "Max-Masxali-Left:${cameraViewModel.matrix[3][0]}")
                    Log.d("TESTMPIKELEFT", "MPIKE left masxali: ${cameraViewModel.matrix[3][0]}")
                }

                //MIN gwnias
                if (angle23_11_13 < cameraViewModel.matrix[3][1] && cameraViewModel.matrix[3][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[3][1] = angle23_11_13
                    Log.d("TOTALMIN", "Min-Masxali-Left:${cameraViewModel.matrix[3][1]}")
                }

                //gwnia gonato aristero
                val angle23_25_27 = getAngle(leftHip, leftKnee, leftAnkle)

                //MAX gwnias
                if (angle23_25_27 > cameraViewModel.matrix[4][0]) {
                    cameraViewModel.matrix[4][0] = angle23_25_27
                    Log.d("TOTALMΑΧ", "Max-Gonato-Left:${cameraViewModel.matrix[4][0]}")
                }

                //MIN gwnias
                if (angle23_25_27 < cameraViewModel.matrix[4][1] && cameraViewModel.matrix[4][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[4][1] = angle23_25_27
                    Log.d("TOTALMIN", "Min-Gonato-Left:${cameraViewModel.matrix[4][1]}")
                }

                //gwnia gonato dexi
                val angle24_26_28 = getAngle(rightHip, rightKnee, rightAnkle)

                //MAX gwnias
                if (angle24_26_28 > cameraViewModel.matrix[5][0]) {
                    cameraViewModel.matrix[5][0] = angle24_26_28
                    Log.d("TOTALMΑΧ", "Max-Gonato-Right:${cameraViewModel.matrix[5][0]}")
                }

                //MIN gwnias
                if (angle24_26_28 < cameraViewModel.matrix[5][1] && cameraViewModel.matrix[5][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[5][1] = angle24_26_28
                    Log.d("TOTALMIN", "Min-Gonato-Right:${cameraViewModel.matrix[5][1]}")
                }

                //gwnia dexi lekani
                val angle12_24_26 = getAngle(rightShoulder, rightHip, rightKnee)

                //MAX gwnias
                if (angle12_24_26 > cameraViewModel.matrix[6][0]) {
                    cameraViewModel.matrix[6][0] = angle12_24_26
                    Log.d("TOTALMΑΧ", "Max-Lekani-Right:${cameraViewModel.matrix[6][0]}")
                }

                //MIN gwnias
                if (angle12_24_26 < cameraViewModel.matrix[6][1] && cameraViewModel.matrix[6][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[6][1] = angle12_24_26
                    Log.d("TOTALMIN", "Min-Lekani-Right:${cameraViewModel.matrix[6][1]}")
                }

                //gwnia aristeri lekani
                val angle11_23_25 = getAngle(leftShoulder, leftHip, leftKnee)

                //MAX gwnias
                if (angle11_23_25 > cameraViewModel.matrix[7][0]) {
                    cameraViewModel.matrix[7][0] = angle11_23_25
                    Log.d("TOTALMΑΧ", "Max-Lekani-Left:${cameraViewModel.matrix[7][0]}")
                }

                //MIN gwnias
                if (angle11_23_25 < cameraViewModel.matrix[7][1] && cameraViewModel.matrix[7][0] != Double.MIN_VALUE) {

                    cameraViewModel.matrix[7][1] = angle11_23_25
                    Log.d("TOTALMIN", "Min-Lekani-Left:${cameraViewModel.matrix[7][1]}")
                }
            }
            else{

                //apo edw

                //mexri edw add


                //gwnia dexia agkona
                //val angle12_14_16 = getAngle(rightShoulder, rightElbow, rightWrist)
                val angle12_14_16 = 60.00
                if(Comparation(angle12_14_16,list,0,exName)) {
                    //Log.d("Comparation", "dexi agkona:inBounds")
                }
                else {
                     //Log.d("Comparation","dexi agona:out of Bounds")
                }

                //gwnia dexia masxalis
                val angle24_12_14 = getAngle(rightHip, rightShoulder, rightElbow)
                if(Comparation(angle24_12_14,list,1,exName)) {
                   // Log.d("Comparation", "dexi masxali:inBounds")

                }
                else {
                    //Log.d("Comparation", "dexi masxali:out of Bounds")

                }
                //gwnia aristera mesa agkona
                val angle11_13_15 = getAngle(leftShoulder, leftElbow, leftWrist)
                if(Comparation(angle11_13_15,list,2,exName)) {
                   // Log.d("Comparation", "aristera agkona:inBounds")

                }
                else {
                   // Log.d("Comparation", "aristera agona:out of Bounds")

                }
                //gwnia aristera masxalis
                val angle23_11_13 = getAngle(leftHip, leftShoulder, leftElbow)
                if(Comparation(angle23_11_13,list,3,exName)) {
                    //Log.d("Comparation", "aristera masxali:inBounds")

                }
                else {
                    //Log.d("Comparation", "aristera masxali:out of Bounds")

                }
                //gwnia gonato aristero
                val angle23_25_27 = getAngle(leftHip, leftKnee, leftAnkle)
                if(Comparation(angle23_25_27,list,4,exName)) {
                   // Log.d("Comparation", "aristero gonato:inBounds")

                }
                else {
                    //Log.d("Comparation", "aristero gonato:out of Bounds")

                }
                //gwnia gonato dexi
                val angle24_26_28 = getAngle(rightHip, rightKnee, rightAnkle)
                if(Comparation(angle24_26_28,list,5,exName)) {
                   // Log.d("Comparation", "dexi gonato:inBounds")

                }
                else {
                   // Log.d("Comparation", "dexi gonato:out of Bounds")

                }
                //gwnia dexi lekani
                val angle12_24_26 = getAngle(rightShoulder, rightHip, rightKnee)
                if(Comparation(angle12_24_26,list,6,exName)) {
                    //Log.d("Comparation", "dexi lekani:inBounds")

                }
                else {
                    //Log.d("Comparation", "dexi lekani:out of Bounds")

                }
                //gwnia aristeri lekani
                val angle11_23_25 = getAngle(leftShoulder, leftHip, leftKnee)
                if(Comparation(angle11_23_25,list,7,exName)) {
                   // Log.d("Comparation", "aristeri lekani:inBounds")

                }
                else {
                    //Log.d("Comparation", "aristeri lekani:out of Bounds")

                }
            }*/



        }
        LaunchedEffect(Unit) {
            while (isActive) { // Check if the coroutine is still active
                //Comparation(anglesViewModel,list,exName)
                Comparation(anglesViewModel,list,exName,accurancy.toDouble(),databaseReference,dataViewModel)
                delay(200) // Wait for 3 seconds
            }
        }
    }

}


