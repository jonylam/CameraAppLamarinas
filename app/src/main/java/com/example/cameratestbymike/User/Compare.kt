package com.example.cameratestbymike.User

import android.util.Log
import com.example.cameratestbymike.PoseDetect.AnglesViewModel
import com.example.cameratestbymike.Screens.DataViewModel
import com.example.cameratestbymike.StoreData.ForReclaimData
import com.google.firebase.database.DatabaseReference


fun Comparation(
    anglesViewModel: AnglesViewModel,
    listTotal: List<ForReclaimData>,
    exName: String,
    accurancy: Double,
    databaseReference:DatabaseReference,
    dataViewModel: DataViewModel
) {

    dataViewModel.getdatafromdatabase(databaseReference)
    val listTotal=dataViewModel.getList()

    listTotal.forEach { element->
        if(element.name == exName)
        {

            val listAngles = element.angles

            if(exName=="Dumple") {

                if(anglesViewModel.angle24_12_14.value < listAngles[1].min - (listAngles[1].min * ((100 - accurancy) / 100)) )
                    anglesViewModel.textViewed.value="Move your Arm Right"
                    //Log.d("bounds1","Move your Arm Right")
                else if(anglesViewModel.angle24_12_14.value > listAngles[1].max + (listAngles[1].max * ((100 - accurancy) / 100)) )
                    anglesViewModel.textViewed.value="Move your Arm Left"
                    //Log.d("bounds1","Move your Arm Left")
                else {
                    if (
                        anglesViewModel.angle12_14_16.value > listAngles[0].min + (listAngles[0].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value < listAngles[0].max - (listAngles[0].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.stage.value == "up"
                    ) {

                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.textViewed.value = "Move Up"
                        Log.d("bounds1", "Move Up --${anglesViewModel.angle12_14_16.value}")

                    } else if (
                        anglesViewModel.angle12_14_16.value > listAngles[0].min + (listAngles[0].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value < listAngles[0].max - (listAngles[0].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.stage.value == "down"
                    ) {

                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.textViewed.value = "Move Down"
                        Log.d("bounds1", "Move down --${anglesViewModel.angle12_14_16.value}")

                    } else if (
                        anglesViewModel.angle12_14_16.value <= listAngles[0].min + (listAngles[0].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value >= listAngles[0].min - (listAngles[0].min * ((100 - accurancy) / 100))

                    ) {

                        anglesViewModel.rep.value = true
                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.stage.value = "down"
                        anglesViewModel.textViewed.value = "Move Down"
                        Log.d("bounds1", "Move Down --${anglesViewModel.angle12_14_16.value}")

                    } else if (
                        anglesViewModel.angle12_14_16.value >= listAngles[0].max - (listAngles[0].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value <= listAngles[0].max + (listAngles[0].max * ((100 - accurancy) / 100))
                    ) {

                        if (anglesViewModel.rep.value) {
                            anglesViewModel.count.value += 1
                            anglesViewModel.rep.value = false
                            anglesViewModel.textCount.value = "Reps:${anglesViewModel.count.value}"
                            Log.d("bounds1", "reps:${anglesViewModel.count.value}")
                        }
                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.stage.value = "up"
                        anglesViewModel.textViewed.value = "Move Up"
                        Log.d("bounds1", "Move Up --${anglesViewModel.angle12_14_16.value}")

                    } else {
                        anglesViewModel.inbounbs.value = false

                        if (anglesViewModel.angle12_14_16.value < listAngles[0].min - (listAngles[0].min * ((100 - accurancy) / 100))) {

                            anglesViewModel.textViewed.value = "Out of Bounds Move Down"
                            Log.d("bounds1", "out of Bounds Move down--${anglesViewModel.angle12_14_16.value}")

                        } else if (anglesViewModel.angle12_14_16.value > listAngles[0].max + (listAngles[0].max * ((100 - accurancy) / 100))) {

                            if (anglesViewModel.rep.value) {

                                anglesViewModel.count.value += 1
                                anglesViewModel.rep.value = false

                                anglesViewModel.textCount.value = "Reps:${anglesViewModel.count.value}"
                                Log.d("bounds1", "reps:${anglesViewModel.count.value}")

                            }

                            anglesViewModel.textViewed.value = "Out Of Bounds Move Up"
                            Log.d("bounds1", "out of Bounds Move up--${anglesViewModel.angle12_14_16.value}")

                        }

                    }
                }

            }

            if(exName=="Leg"){

                if(anglesViewModel.angle24_26_28.value < listAngles[5].min - (listAngles[5].min * ((100 - accurancy) / 100)) )
                    anglesViewModel.textViewed.value="Dont Bend Your Knee"
                else if(anglesViewModel.angle24_26_28.value > listAngles[5].max + (listAngles[5].max * ((100 - accurancy) / 100)) )
                    anglesViewModel.textViewed.value="Bend Your Knee"
                else {
                    if (
                        anglesViewModel.angle12_24_26.value > listAngles[6].min + (listAngles[6].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_24_26.value < listAngles[6].max - (listAngles[6].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.stage.value == "up"
                    ) {
                        anglesViewModel.textViewed.value = "Move Up"
                        Log.d("bounds1", "Move Up")
                    } else if (
                        anglesViewModel.angle12_24_26.value > listAngles[6].min + (listAngles[6].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_24_26.value < listAngles[6].max - (listAngles[6].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.stage.value == "down"
                    ) {

                        anglesViewModel.textViewed.value = "Move Down"
                        Log.d("bounds1", "Move down --${anglesViewModel.angle12_24_26.value}")

                    } else if (
                        anglesViewModel.angle12_24_26.value <= listAngles[6].min + (listAngles[6].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_24_26.value >= listAngles[6].min - (listAngles[6].min * ((100 - accurancy) / 100))
                    ) {

                        anglesViewModel.rep.value = true
                        anglesViewModel.stage.value = "down"
                        anglesViewModel.textViewed.value = "Move Down"
                        Log.d("bounds1", "Move Down --${anglesViewModel.angle12_24_26.value}")

                    } else if (
                        anglesViewModel.angle12_24_26.value >= listAngles[6].max - (listAngles[6].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_24_26.value <= listAngles[6].max + (listAngles[6].max * ((100 - accurancy) / 100))
                    ) {

                        if (anglesViewModel.rep.value == true) {
                            anglesViewModel.count.value += 1
                            anglesViewModel.rep.value = false
                            anglesViewModel.textCount.value = "Reps:${anglesViewModel.count.value}"
                            Log.d("bounds1", "reps:${anglesViewModel.count.value}")
                        }

                        anglesViewModel.stage.value = "up"
                        anglesViewModel.textViewed.value = "Move Up"
                        Log.d("bounds1", "Move Up --${anglesViewModel.angle12_24_26.value}")

                    } else {

                        if (anglesViewModel.angle12_24_26.value < listAngles[6].min - (listAngles[6].min * ((100 - accurancy) / 100))) {

                            anglesViewModel.textViewed.value = "Out of Bounds Move Down"
                            Log.d("bounds1", "out of Bounds Move down--${anglesViewModel.angle12_24_26.value}")

                        } else if (anglesViewModel.angle12_24_26.value > listAngles[6].max + (listAngles[6].max * ((100 - accurancy) / 100))) {

                            if (anglesViewModel.rep.value) {

                                anglesViewModel.count.value += 1
                                anglesViewModel.rep.value = false

                                anglesViewModel.textCount.value = "Reps:${anglesViewModel.count.value}"
                                Log.d("bounds1", "reps:${anglesViewModel.count.value}")

                            }

                            anglesViewModel.textViewed.value = "Out Of Bounds Move Up"
                            Log.d("bounds1", "out of Bounds Move up--${anglesViewModel.angle12_24_26.value}")

                        }

                    }
                }

            }

        }
    }

}
