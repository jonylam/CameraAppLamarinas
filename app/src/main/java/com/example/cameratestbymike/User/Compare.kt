package com.example.cameratestbymike.User

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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

                    if (
                        anglesViewModel.angle12_14_16.value > listAngles[0].min + (listAngles[0].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value < listAngles[0].max - (listAngles[0].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.stage.value == "up"
                    ) {

                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.textViewed.value="Move Up --${anglesViewModel.angle12_14_16.value}"
                        Log.d("bounds1", "Move Up --${anglesViewModel.angle12_14_16.value}")

                    } else if (
                        anglesViewModel.angle12_14_16.value > listAngles[0].min + (listAngles[0].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value < listAngles[0].max - (listAngles[0].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.stage.value == "down"
                    ) {

                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.textViewed.value="Move Down --${anglesViewModel.angle12_14_16.value}"
                        Log.d("bounds1", "Move down --${anglesViewModel.angle12_14_16.value}")

                    } else if (
                        anglesViewModel.angle12_14_16.value <= listAngles[0].min + (listAngles[0].min * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value >= listAngles[0].min - (listAngles[0].min * ((100 - accurancy) / 100))

                    ) {

                        anglesViewModel.rep.value = true
                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.stage.value = "down"
                        anglesViewModel.textViewed.value="Move Down --${anglesViewModel.angle12_14_16.value}"
                        Log.d("bounds1", "Move Down --${anglesViewModel.angle12_14_16.value}")

                    } else if (
                        anglesViewModel.angle12_14_16.value >= listAngles[0].max - (listAngles[0].max * ((100 - accurancy) / 100)) &&
                        anglesViewModel.angle12_14_16.value <= listAngles[0].max + (listAngles[0].max * ((100 - accurancy) / 100))
                    ) {

                        if (anglesViewModel.rep.value == true) {
                            anglesViewModel.count.value += 1
                            anglesViewModel.rep.value = false
                            anglesViewModel.textCount.value="Reps:${anglesViewModel.count.value}"
                            Log.d("bounds1", "reps:${anglesViewModel.count.value}")
                        }
                        anglesViewModel.inbounbs.value = true
                        anglesViewModel.stage.value = "up"
                        anglesViewModel.textViewed.value="Move Up --${anglesViewModel.angle12_14_16.value}"
                        Log.d("bounds1", "Move Up --${anglesViewModel.angle12_14_16.value}")

                    } else {
                        anglesViewModel.inbounbs.value = false

                        if (anglesViewModel.angle12_14_16.value < listAngles[0].min - (listAngles[0].min * ((100 - accurancy) / 100))) {
                            anglesViewModel.textViewed.value="Out of Bounds Move Down--${anglesViewModel.angle12_14_16.value}"
                            Log.d("bounds1", "out of Bounds Move down--${anglesViewModel.angle12_14_16.value}")
                        }
                        else if (anglesViewModel.angle12_14_16.value > listAngles[0].max + (listAngles[0].max * ((100 - accurancy) / 100))) {

                            if (anglesViewModel.rep.value) {
                                anglesViewModel.count.value += 1
                                anglesViewModel.rep.value = false
                                anglesViewModel.textCount.value="Reps:${anglesViewModel.count.value}"
                                Log.d("bounds1", "reps:${anglesViewModel.count.value}")
                            }

                            anglesViewModel.textViewed.value="Out Of Bounds Move Up--${anglesViewModel.angle12_14_16.value}"
                            Log.d("bounds1", "out of Bounds Move up--${anglesViewModel.angle12_14_16.value}")

                        }

                    }

            }



            /*if(angle24_12_14.value >= listAngles[1].min && angle24_12_14.value <= listAngles[1].max)
                Log.d("bounds1","dexi masxali:${angle24_12_14.value} , Min:${listAngles[1].min} , Max:${listAngles[1].max} in bounds")
            else
                Log.d("bounds1","dexi masxali:${angle24_12_14.value} , Min:${listAngles[1].min} , Max:${listAngles[1].max} , out of bounds")

            if(angle11_13_15.value >= listAngles[2].min && angle11_13_15.value <= listAngles[2].max)
                Log.d("bounds1","aristero agkona:${angle11_13_15.value} , Min:${listAngles[2].min} , Max:${listAngles[2].max} in bounds")
            else
                Log.d("bounds1","aristero agkona:${angle11_13_15.value} , Min:${listAngles[2].min} , Max:${listAngles[2].max} , out of bounds")

            if(angle23_11_13.value >= listAngles[3].min && angle23_11_13.value <= listAngles[3].max)
                Log.d("bounds1","aristero masxali:${angle23_11_13.value} , Min:${listAngles[3].min} , Max:${listAngles[3].max} in bounds")
            else
                Log.d("bounds1","aristero masxali:${angle23_11_13.value} , Min:${listAngles[3].min} , Max:${listAngles[3].max} , out of bounds")

            if(angle23_25_27.value >= listAngles[4].min && angle23_25_27.value <= listAngles[4].max)
                Log.d("bounds1","aristero gonato:${angle23_25_27.value} , Min:${listAngles[4].min} , Max:${listAngles[4].max} in bounds")
            else
                Log.d("bounds1","aristero gonato:${angle23_25_27.value} , Min:${listAngles[4].min} , Max:${listAngles[4].max} , out of bounds")

            if(angle24_26_28.value >= listAngles[5].min && angle24_26_28.value <= listAngles[5].max)
                Log.d("bounds1","dexi gonato:${angle24_26_28.value} , Min:${listAngles[5].min} , Max:${listAngles[5].max} in bounds")
            else
                Log.d("bounds1","dexi gonato:${angle24_26_28.value} , Min:${listAngles[5].min} , Max:${listAngles[5].max} , out of bounds")

            if(angle12_24_26.value >= listAngles[6].min && angle12_24_26.value <= listAngles[6].max)
                Log.d("bounds1","dexi gofos:${angle12_24_26.value} , Min:${listAngles[6].min} , Max:${listAngles[6].max} in bounds")
            else
                Log.d("bounds1","dexi gofos:${angle12_24_26.value} , Min:${listAngles[6].min} , Max:${listAngles[6].max} , out of bounds")

            if(angle11_23_25.value >= listAngles[7].min && angle11_23_25.value <= listAngles[7].max)
                Log.d("bounds1","aristero gofos:${angle11_23_25.value} , Min:${listAngles[7].min} , Max:${listAngles[7].max} in bounds")
            else
                Log.d("bounds1","aristero gofos:${angle11_23_25.value} , Min:${listAngles[7].min} , Max:${listAngles[7].max} , out of bounds")

            Log.d("bounds1","-------------------------------------------------------------")
            Log.d("bounds1","-------------------------------------------------------------")*/

        }
    }

}
