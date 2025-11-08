package com.example.gyroscopedemomvvm


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

data class GravReading(val dt: Long, val x: Dp, val y: Dp)

//repository and model
class GravityRepository( val sensorManager: SensorManager) {
    var lastTime = System.currentTimeMillis()
    var xVel = 0f
    var yVel = 0f
    var xPos = 0.dp
    var yPos = 0.dp

    var maxWidth = 0.dp
    var minWidth = 0.dp
    var maxHeight = 0.dp
    var minHeight= 0.dp
    var ballSize = 0.dp

    val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    val sensor = gravitySensor ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var listener = object : SensorEventListener{
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {}
    }

    fun getGravFlow(): Flow<GravReading> = channelFlow {
        if (sensor == null) {
            return@channelFlow
        }

        listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                // Calc dt
                val currTime = System.currentTimeMillis()
                val dt = currTime - lastTime

                lastTime = currTime

                xVel += event.values[0] * dt * -0.01f
                yVel += event.values[1] * dt * 0.01f

                xPos += (xVel * dt * 0.01f).dp
                yPos += (yVel * dt * 0.01f).dp

                if(xPos > maxWidth-ballSize){
                    xVel = 0f
                    xPos = maxWidth-ballSize
                }else if(xPos < 0.dp){
                    xVel = 0f
                    xPos = 0.dp
                }

                if(yPos > maxHeight - ballSize){
                    yVel = 0f
                    yPos = maxHeight-ballSize
                }else if(yPos < 0.dp){
                    yVel = 0f
                    yPos = 0.dp
                }
                trySendBlocking(GravReading(dt, xPos, yPos))
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }


        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        awaitClose { sensorManager.unregisterListener(listener) }
    }
}
