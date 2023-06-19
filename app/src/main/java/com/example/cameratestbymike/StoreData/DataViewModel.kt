package com.example.cameratestbymike.Screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cameratestbymike.StoreData.ForStoreData
import com.example.cameratestbymike.StoreData.ForReclaimData
import com.google.firebase.database.*


class DataViewModel : ViewModel() {

    val totallist = mutableListOf<ForReclaimData>()
    val getData = mutableStateOf(false)

    fun getDataFromDatabase(databaseReference: DatabaseReference) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                totallist.clear()

                for (childSnapshot in snapshot.children) {
                    val angleAsRowList = mutableListOf<ForStoreData>()

                    val name = childSnapshot.key ?: ""

                    for (testChildSnapshot in childSnapshot.children) {

                        val max = testChildSnapshot.child("max").getValue(Double::class.java) ?: 0.0
                        val min = testChildSnapshot.child("min").getValue(Double::class.java) ?: 0.0

                        //test gia to accurancy
                        val acc = testChildSnapshot.child("accurancy").getValue(Double::class.java) ?: 0.0

                        val angleAsRow = ForStoreData(max,min,acc)
                        angleAsRowList.add(angleAsRow)
                    }

                    val angleList = ForReclaimData(name, angleAsRowList)
                    totallist.add(angleList)
                }
                getData.value = true

            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to retrieve data from matrix: ${error.message}")
            }
        })
    }

    fun deleteExercise(databaseReference: DatabaseReference, name: String) {
        databaseReference.child(name).removeValue()
    }

    fun getList(): List<ForReclaimData> {
        if (getData.value){
            return totallist
        }
        return emptyList()
    }

    fun show() {
        for (list in totallist) {
            val angleStrings = list.angles.joinToString(", ") { "${it.max}/${it.min}" }
            Log.d("totallist", "${list.name}: $angleStrings")
        }
    }

}