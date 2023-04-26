package com.example.cameratestbymike.PoseDetect

import com.google.mlkit.vision.pose.PoseLandmark
import kotlin.math.atan2

fun getAngle(firstPoint: PoseLandmark?, midPoint: PoseLandmark?, lastPoint: PoseLandmark?): Double {
    if (firstPoint == null || midPoint == null || lastPoint == null) {
        // Handle null values
        return 0.0
    }

    var angle = Math.toDegrees(
        atan2(1.0* lastPoint!!.position.y - midPoint!!.position.y,
            1.0*lastPoint.position.x - midPoint.position.x)
                - atan2(firstPoint!!.position.y - midPoint.position.y,
            firstPoint.position.x - midPoint.position.x)
    )
    angle = Math.abs(angle) // Angle should never be negative
    if(angle > 180)
        angle = 360.0 - angle // Always get the acute representation of the angle

    return  angle
}