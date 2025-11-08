package com.example.gyroscopedemomvvm

import android.app.Application
import android.content.Context
import android.hardware.SensorManager

class GravityApp : Application() {

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val gyroscopeRepository by lazy {
        GravityRepository(sensorManager)
    }
}
