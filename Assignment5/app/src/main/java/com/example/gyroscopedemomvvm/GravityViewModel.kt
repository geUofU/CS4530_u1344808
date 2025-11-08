package com.example.gyroscopedemomvvm


import android.hardware.SensorManager
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope


class GravityViewModel(private val repository: GravityRepository) : ViewModel() {

    val gravReading = repository.getGravFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GravReading(System.currentTimeMillis(), 0.dp, 0.dp)
        )

    fun updateBounds(xmax: Dp, xmin: Dp, ymax: Dp, ymin: Dp, ballSize: Dp){
        repository.maxWidth = xmax
        repository.minWidth = xmin
        repository.maxHeight = ymax
        repository.minHeight = ymin
        repository.ballSize = ballSize
    }

    fun unregisterThings(){ repository.sensorManager.unregisterListener(repository.listener) }
    fun reregisterThings(){ repository.sensorManager.registerListener(repository.listener, repository.sensor, SensorManager.SENSOR_DELAY_UI) }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GravityApp)
                GravityViewModel(application.gyroscopeRepository)
            }
        }
    }
}
