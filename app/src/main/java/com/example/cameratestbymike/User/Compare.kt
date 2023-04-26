package com.example.cameratestbymike.User

import android.util.Log
import com.example.cameratestbymike.StoreData.ForReclaimData


fun Comparation(
    angle:Double,
    listTotal:List<ForReclaimData>,
    angleNumber:Int,
    exName:String
): Boolean {
    if(listTotal.isEmpty()){
        //Log.d("mpike","einai kenh")
        return false
    }
    else {
        listTotal.forEach { element->
            if(element.name == exName)
            {
                val listAngles = element.angles
                val perCentMax = listAngles[angleNumber].max * listAngles[angleNumber].accurancy / 100
                //val apoklisiMax = listAngles[angleNumber].max - perCentMax //27

                val perCentMin = listAngles[angleNumber].min * listAngles[angleNumber].accurancy / 100
                //val apoklisiMin = listAngles[angleNumber].min - perCentMin //9

                Log.d("perCentMax", "MaxPerCent:$perCentMax")
               // Log.d("perCentMax", "MaxApoklisi:$apoklisiMax")

                Log.d("perCentMin", "MinPerCent:$perCentMin")
               // Log.d("perCentMin", "MinApoklisi:$apoklisiMin")
                //Log.d("anglevalues","Angle$angleNumber: Value$angle, Max:${listAngles[angleNumber].max}, Min:${listAngles[angleNumber].min}, Acc:${listAngles[angleNumber].accurancy}")
                if(angle < perCentMax && angle > perCentMin)
                    Log.d("bounds","inside Normal bounds")
                else
                    Log.d("bounds","OutSide of bounds of ${listAngles[angleNumber].accurancy}% accurancy")
                //return (angle<=listAngle[angleNumber].max && angle>=listAngle[angleNumber].min)
            }
        }
        return false
        //Log.d("maxminComparation","${listAngle[0].max},${listAngle[0].min} ")
        //return (angle<=listAngle[angleNumber].max && angle>=listAngle[angleNumber].min)
    }
}