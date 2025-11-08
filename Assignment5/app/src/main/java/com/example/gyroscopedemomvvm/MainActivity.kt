package com.example.gyroscopedemomvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gyroscopedemomvvm.ui.theme.GravityTheme

class MainActivity : ComponentActivity() {
    private val myVM: GravityViewModel by lazy{
        ViewModelProvider(this, GravityViewModel.Factory).get(GravityViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GravityTheme {
                GravityScreen(myVM)
            }
        }
    }

    override fun onPause(){
        super.onPause()
        myVM.unregisterThings()
    }

    override fun onResume(){
        super.onResume()
        myVM.reregisterThings()
    }

}

@Composable
fun GravityScreen(viewModel: GravityViewModel) {
    val gravReading by viewModel.gravReading.collectAsStateWithLifecycle()
    BoxWithConstraints(modifier = Modifier.fillMaxSize()){
        val BoxScope = this
        val ballSize = 50.dp

        viewModel.updateBounds(BoxScope.maxWidth, BoxScope.minWidth,
            BoxScope.maxHeight, BoxScope.minHeight, ballSize)


        Box(modifier = Modifier
            .offset(gravReading.x, gravReading.y)
            .size(50.dp)
            .background(Color.Red)
        ){}

        Text(text = "\nX = ${gravReading.x}, \nY = ${gravReading.y},\nMax Width = ${BoxScope.maxWidth}, " +
                "\nMin Width = ${BoxScope.minWidth}, \nMax Height =  ${BoxScope.maxHeight}")
    }
}

