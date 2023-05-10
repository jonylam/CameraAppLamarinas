package com.example.cameratestbymike.Camera


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
//import androidx.compose.material.icons.sharp.FlipCameraAndroid
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cameratestbymike.PoseDetect.AnglesViewModel
import com.example.cameratestbymike.PoseDetect.DetectedPose
import com.example.cameratestbymike.PoseDetect.PoseDetectorProcessor
import com.example.cameratestbymike.R
import com.example.cameratestbymike.Screens.DataViewModel

import com.example.cameratestbymike.StoreData.ForStoreData
import com.example.cameratestbymike.User.UserStateViewModel
import com.example.cameratestbymike.auth.AuthViewModel
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.database.DatabaseReference
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.pose.Pose
import kotlinx.coroutines.delay
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine



@Composable
fun CameraView(
    viewModel: AuthViewModel,
    cameraViewModel: CameraViewModel,
    databaseReference: DatabaseReference,
    newItemName: String,
    Accurancy: String,
    navController: NavHostController
) {
    val context = LocalContext.current
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK)}

    CameraPreviewView(
        viewModel,
        databaseReference,
        newItemName,
        Accurancy,
        navController,
        lensFacing,
        cameraViewModel,
    ) { cameraUIAction ->
        when(cameraUIAction) {
            is CameraUIAction.OnSwitchCameraClick -> {
                lensFacing =
                    if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT
                    else
                        CameraSelector.LENS_FACING_BACK
            }
        }
    }

}


@Composable
private fun CameraPreviewView(
    viewModel: AuthViewModel,
    databaseReference: DatabaseReference,
    newItemName: String,
    Accurancy: String,
    navController: NavHostController,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    cameraViewModel: CameraViewModel,
    cameraUIAction: (CameraUIAction) -> Unit,
    ) {
    val userStateViewModel=viewModel<UserStateViewModel>()
    userStateViewModel.setRoleHost()
    //Log.d("Roleof","${userStateViewModel.role.value}")

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    //Build the preview
    val preview = Preview.Builder().build()

    //By default is back camera
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val previewView = remember {
        PreviewView(context)
    }


    var detectedPose by remember { mutableStateOf<Pose?>(null) }
    var sourceInfo by remember { mutableStateOf(SourceInfo(10, 10, true)) }

    val analysis =
        bindAnalysisUseCase(lensFacing, setSourceInfo = { sourceInfo = it }, onPoseDetected = { detectedPose = it })


    //Changes about time viewmodel

    if(viewModel.currentUser?.displayName.toString()=="host") {
        LaunchedEffect(
            cameraViewModel.timeBeforePoseDetect,
            cameraViewModel.timeAfterPoseDetect,
            cameraViewModel.restartTrigger.value
        ) {


            while (cameraViewModel.timeBeforePoseDetect.value > 0) {

                delay(1000)

                cameraViewModel.setTbefore(cameraViewModel.timeBeforePoseDetect.value - 1)

                Log.d("TimeTest", "time before: ${cameraViewModel.timeBeforePoseDetect.value}")
            }

            while (cameraViewModel.timeBeforePoseDetect.value == 0 && cameraViewModel.timeAfterPoseDetect.value > 0) {

                delay(1000)

                cameraViewModel.setTafter(cameraViewModel.timeAfterPoseDetect.value - 1)

                Log.d("TimeTest", "time after: ${cameraViewModel.timeAfterPoseDetect.value}")
            }

            cameraViewModel.resetRestart()
        }
    }
    /**edw einai an 8elw na balw to timer gia ton user ton 10 sec prin tin katagrafi
     * else if(userStateViewModel.role.value=="user"){
        LaunchedEffect(
            cameraViewModel.timeBeforePoseDetect,
            cameraViewModel.restartTrigger.value
        ) {


            while (cameraViewModel.timeBeforePoseDetect.value > 0) {

                delay(1000)

                cameraViewModel.setTbefore(cameraViewModel.timeBeforePoseDetect.value - 1)

                Log.d("TimeTest", "time before: ${cameraViewModel.timeBeforePoseDetect.value}")
            }

            cameraViewModel.resetRestart()
        }
    }*/


    //end changes about time viewmodel


    //Listen for changes to lensFacing
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            //egine add xriazete gia poses apo mike einai
            analysis,
        )
        //To see the changes
        preview.setSurfaceProvider(previewView.surfaceProvider)

    }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {
            }

           //kanw add to timer viewmodel gia ton host
            if(viewModel.currentUser?.displayName.toString()=="host") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = when {
                            cameraViewModel.timeBeforePoseDetect.value != 0 -> "Time Remaining For Pose Detect Start:  ${cameraViewModel.timeBeforePoseDetect.value}"
                            cameraViewModel.timeAfterPoseDetect.value != 0 -> "Time Remaining For Pose Detect Stop:  ${cameraViewModel.timeAfterPoseDetect.value}"
                            else -> "Pose Captured, Save or Restart Capture!"
                        },
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            //end add viewmodel

            //kanw add ta texts gia user
            if(viewModel.currentUser?.displayName.toString()=="user") {
                val anglesViewModel=viewModel<AnglesViewModel>()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(vertical = 16.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.TopStart)) {
                        //text gia kathodigisi
                        Text(
                            text = anglesViewModel.textViewed.value,
                            color = Color.White,
                            fontSize = 22.sp
                            //modifier = Modifier.align(Alignment.Center),
                        )
                        Text(
                            text= anglesViewModel.textCount.value,
                            color = Color.White,
                            fontSize = 22.sp
                           // modifier = Modifier.align(Alignment.Center),
                        )
                    }

                }

            }
            //end add texts gia user


            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                with(LocalDensity.current) {
                    Box(
                        modifier = Modifier
                            .size(
                                height = sourceInfo.height.toDp(),
                                width = sourceInfo.width.toDp()
                            )
                            .scale(
                                calculateScale(
                                    constraints,
                                    sourceInfo,
                                    PreviewScaleType.CENTER_CROP
                                )
                            )
                    )
                    {


                        val dataViewModel=viewModel<DataViewModel>()
                        dataViewModel.getdatafromdatabase(databaseReference)
                        val listTotal=dataViewModel.getList()

                        val anglesViewModel= viewModel<AnglesViewModel>()

                        if(viewModel.currentUser?.displayName.toString()=="host") {
                            if ((cameraViewModel.timeBeforePoseDetect.value == 0) && (cameraViewModel.timeAfterPoseDetect.value > 0)) {

                                if (cameraViewModel.matrixTrigger.value == false) {
                                    cameraViewModel.DeclareMinMaxTable()
                                    cameraViewModel.matrixTrigger.value = true
                                }

                                DetectedPose(
                                    pose = detectedPose,
                                    sourceInfo = sourceInfo,
                                    cameraViewModel,
                                    userStateViewModel,
                                    listTotal,
                                    newItemName,
                                    anglesViewModel,
                                    Accurancy,
                                    databaseReference,
                                    dataViewModel
                                )

                            }
                        }
                        else{
                          //sta sxolia einai an 8elw na balw to time ton 10 sec prin arxisei h katagrafi pozwn gia ton user
                          //  if ( (cameraViewModel.timeBeforePoseDetect.value == 0) ) {
                                DetectedPose(
                                    pose = detectedPose,
                                    sourceInfo = sourceInfo,
                                    cameraViewModel,
                                    userStateViewModel,
                                    listTotal,
                                    newItemName,
                                    anglesViewModel,
                                    Accurancy,
                                    databaseReference,
                                    dataViewModel
                                )
                           // }
                        }


                    }
                }


            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Bottom
            ) {
                CameraControls(
                    cameraUIAction,
                    cameraViewModel,
                    databaseReference,
                    newItemName,
                    Accurancy,
                    navController
                )
            }
        }

}


fun SaveToDataBase(
    context: Context,
    databaseReference: DatabaseReference,
    matrix: Array<DoubleArray>,
    newItemName: String,
    Accurancy: String
) {

    val matrixRows= listOf(
        ForStoreData(matrix[0][0],matrix[0][1],Accurancy.toDouble()),
        ForStoreData(matrix[1][0],matrix[1][1],Accurancy.toDouble()),
        ForStoreData(matrix[2][0],matrix[2][1],Accurancy.toDouble()),
        ForStoreData(matrix[3][0],matrix[3][1],Accurancy.toDouble()),
        ForStoreData(matrix[4][0],matrix[4][1],Accurancy.toDouble()),
        ForStoreData(matrix[5][0],matrix[5][1],Accurancy.toDouble()),
        ForStoreData(matrix[6][0],matrix[6][1],Accurancy.toDouble()),
        ForStoreData(matrix[7][0],matrix[7][1],Accurancy.toDouble())
    )




    //val Exname= databaseReference.push().key!!

    databaseReference.child(newItemName).setValue(matrixRows).addOnCompleteListener{
        Toast.makeText(context,"Data saved", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener{err->
        Toast.makeText(context,"Error ${err.message}", Toast.LENGTH_SHORT).show()

    }

}


suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

@Composable
fun CameraControls(
    cameraUIAction: (CameraUIAction) -> Unit,
    cameraViewModel: CameraViewModel,
    databaseReference: DatabaseReference,
    newItemName: String,
    Accurancy: String,
    navController: NavHostController
) {

    val context= LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.background(MaterialTheme.colors.surface),
            .background(color = Color.Gray)
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CameraControl(
            //Icons.Sharp.FlipCameraAndroid,
            R.drawable.ic_switchcamera1,
            R.string.icn_camera_view_switch_camera_content_description,
           // modifier= Modifier .size(50.dp),
            onClick = { cameraUIAction(CameraUIAction.OnSwitchCameraClick) }
        )

        Spacer(modifier = Modifier.width(20.dp))

        //kanw add px button gia meta to capture video an 8elw na apo8ikeutei h mporw na balw kai allo ena gia retake
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    cameraViewModel.setTafterTbefore(10,10)
                    cameraViewModel.Restart()
                    cameraViewModel.matrixTrigger.value=false
                },
                //modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_restart),
                    contentDescription = "Restart"
                )
            }
            Text(
                text = "Restart",
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center
            )
        }
        //mexri edw

        Spacer(modifier = Modifier.width(20.dp))

            if (cameraViewModel.timeAfterPoseDetect.value == 0)
            //kanw add px button gia meta to capture video an 8elw na apo8ikeutei h mporw na balw kai allo ena gia retake
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = {
                            SaveToDataBase(
                                context,
                                databaseReference,
                                cameraViewModel.matrix,
                                newItemName,
                                Accurancy
                            )
                            navController.navigate("main_screen")
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = "Save"
                        )
                    }
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center
                    )
                }
            }


    }
}



@Composable
fun CameraControl(
    //imageVector: ImageVector,
    drawableId: Int,
    contentDescId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Icon(
                //imageVector = imageVector,
                painter = painterResource(id = drawableId),
                contentDescription = stringResource(id = contentDescId),
                modifier = modifier,
                tint = Color.Black
            )
        }
        Text(
            text = "Switch",
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
    }

}


private fun bindAnalysisUseCase(
    lensFacing: Int,
    setSourceInfo: (SourceInfo) -> Unit,
    onPoseDetected: (Pose) -> Unit
): ImageAnalysis? {
    val poseProcessor = try {
        PoseDetectorProcessor()
    } catch (e: Exception) {
        Log.e("CAMERA", "Can not create pose processor", e)
        return null
    }
    val builder = ImageAnalysis.Builder()
    val analysisUseCase = builder.build()

    var sourceInfoUpdated = false

    analysisUseCase.setAnalyzer(
        TaskExecutors.MAIN_THREAD
    ) { imageProxy: ImageProxy ->
        if (!sourceInfoUpdated) {
            setSourceInfo(obtainSourceInfo(lensFacing, imageProxy))
            sourceInfoUpdated = true
        }
        try {
            poseProcessor.processImageProxy(imageProxy, onPoseDetected)
        } catch (e: MlKitException) {
            Log.e(
                "CAMERA", "Failed to process image. Error: " + e.localizedMessage
            )
        }
    }
    return analysisUseCase
}

private fun obtainSourceInfo(lens: Int, imageProxy: ImageProxy): SourceInfo {
    val isImageFlipped = lens == CameraSelector.LENS_FACING_FRONT
    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
    return if (rotationDegrees == 0 || rotationDegrees == 180) {
        SourceInfo(
            height = imageProxy.height, width = imageProxy.width, isImageFlipped = isImageFlipped
        )
    } else {
        SourceInfo(
            height = imageProxy.width, width = imageProxy.height, isImageFlipped = isImageFlipped
        )
    }
}

private fun calculateScale(
    constraints: Constraints,
    sourceInfo: SourceInfo,
    scaleType: PreviewScaleType
): Float {
    val heightRatio = constraints.maxHeight.toFloat() / sourceInfo.height
    val widthRatio = constraints.maxWidth.toFloat() / sourceInfo.width
    return when (scaleType) {
        PreviewScaleType.FIT_CENTER -> kotlin.math.min(heightRatio, widthRatio)
        PreviewScaleType.CENTER_CROP -> kotlin.math.max(heightRatio, widthRatio)
    }
}

data class SourceInfo(
    val width: Int,
    val height: Int,
    val isImageFlipped: Boolean,
)

private enum class PreviewScaleType {
    FIT_CENTER,
    CENTER_CROP
}