package com.example.cameratestbymike.PoseDetect


import android.util.Log


//var min: Double?=null
//var max: Double?=null

//MIN_VALUE timi 8etiki poly konta sto 0.
//MAX_VALUE h megaliteri 8etiki timi.

var totalMaxAgkona: Double = Double.MIN_VALUE
var totalMinAgkona: Double = Double.MAX_VALUE

var totalMaxMasxali: Double = Double.MIN_VALUE
var totalMinMasxali: Double = Double.MAX_VALUE






    fun dumple(angle12_14_16: Double , angle24_12_14:Double){


        //agkonas
        val maxA= getMaxAgkona(angle12_14_16)
        val minA=getMinAgkona(angle12_14_16)

        //masxali
        val maxM= getMaxMasxali(angle24_12_14)
        val minM= getMinMasxali(angle24_12_14)


        //agkonas Max-Min
        Log.d("total1","Total max Agkona is: $maxA")
        Log.d("total1","Total min Agkona is: $minA")

        //masxali Max-Min
        Log.d("total1","Total max masxali is: $maxM")
        Log.d("total1","Total min masxali is: $minM")

    }


    fun getMaxAgkona(angle: Double): Double? {

        //calculate the totalMax value for agkona of all calls of dumple fun
        if (angle > totalMaxAgkona) {
            totalMaxAgkona = angle
           // Log.d("TOTALMAX","MAXAGKONA:$totalMaxAgkona")
        }

        return totalMaxAgkona

        //return max
    }


    fun getMinAgkona(angle: Double): Double? {


        //calculate the totalMin value for agkona of all calls of dumple fun
        if (angle < totalMinAgkona && totalMaxAgkona != Double.MIN_VALUE) {
            totalMinAgkona = angle
           // Log.d("TOTALMIN","MINAGKONA:$totalMinAgkona")
        }

        return totalMinAgkona
    }

    fun getMaxMasxali(angle: Double): Double? {

        //calculate the totalMax value for Masxali of all calls of dumple fun
        if (angle > totalMaxMasxali) {
            totalMaxMasxali = angle
           // Log.d("TOTALMAX","MAXMASXALI:$totalMaxMasxali")
        }

        return totalMaxMasxali

        //return max
    }

    fun getMinMasxali(angle: Double): Double? {

        //calculate the totalMin value for Masxali of all calls of dumple fun
        if (angle < totalMinMasxali && totalMaxMasxali != Double.MIN_VALUE) {
            totalMinMasxali = angle
           // Log.d("TOTALMIN","MINMASXALI:$totalMinMasxali")
        }

        return totalMinMasxali
    }







