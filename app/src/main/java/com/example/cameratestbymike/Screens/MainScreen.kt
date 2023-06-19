package com.example.cameratestbymike.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cameratestbymike.auth.AuthViewModel
import com.google.firebase.database.DatabaseReference



/** prepei na balw to add button katw dexia kai na pw na emfanizete pali an einai o rolos host. meta na emfanizei name askisis
 * na anoigei tin kamera kai na kanei store an patisw save.
 */

@Composable
fun PreviewCards(
    viewModel: AuthViewModel,
    dataViewModel: DataViewModel,
    databaseReference: DatabaseReference,
    navController: NavController
) {

    val listData = dataViewModel.getList()

    dataViewModel.getDataFromDatabase(databaseReference)

    //Box(modifier = Modifier.fillMaxWidth()) {
    //BoxWithConstraints(modifier = Modifier.fillMaxSize().background(color = Color(0xFF6C6775))) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Exercises:",
                modifier = Modifier.padding(9.dp),
                fontSize = 18.sp
            )
            LazyColumn(
                //modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(listData) { index, listItem ->

                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("camera_screen/${listItem.name}/${listItem.angles[index].accurancy}")
                            },
                        elevation = 4.dp
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {

                            Text(
                                text = listItem.name,
                                modifier = Modifier.padding(16.dp)
                            )
                            //na emfanistei an exw rolo host
                            if(viewModel.currentUser?.displayName.toString()=="host") {
                                Button(
                                    onClick = {
                                        //auto einai gia to an kanei press to button start kai exei role host
                                        //wste na ginoyn oi allages ksana stin yparxoysa askisi an patisei save.
                                        navController.navigate("camera_screen/${listItem.name}/${listItem.angles[index].accurancy}")
                                    },
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.CenterEnd)
                                ) {
                                    Text(text = "Start")
                                }
                            }

                            //mexri edw me if gia host.

                        }
                    }

                }

            }
            //me if an einai host
            if(viewModel.currentUser?.displayName.toString()=="host") {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.background(color = Color.Gray)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("add_screen")
                            }
                        ) {
                            Text(text = "Add")
                        }

                        Button(
                            onClick = {
                                navController.navigate("delete_screen")
                            }
                        ) {
                            Text(text = "Delete")
                        }

                        Button(
                            onClick = {
                                viewModel?.logout()
                                navController.navigate("login") {
                                    popUpTo("main_screen") { inclusive = true }
                                }
                            }
                        ) {
                            Text(text = "logout")
                        }
                    }
                }
            }
            //mexri edw gia host gia buttons delete add logout.


            if(viewModel.currentUser?.displayName.toString()=="user") {
                Box(modifier = Modifier.align(Alignment.End))
                {
                    Button(
                        onClick = {
                            viewModel?.logout()
                            navController.navigate("login") {
                                popUpTo("main_screen") { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    ) {
                        Text(text = "logout")
                    }
                }


            }


        }



    }
}





