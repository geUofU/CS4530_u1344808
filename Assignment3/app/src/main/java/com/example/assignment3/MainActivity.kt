package com.example.assignment3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.assignment3.ui.theme.Assignment3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                val vm : MyViewModel by viewModels{ MyViewModelProvider.Factory }
                CourseList(vm)
            }
        }
    }
}

@Composable
fun CourseList(vm: MyViewModel){
    Column(
        Modifier.fillMaxWidth().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        val observableList by vm.coursesReadOnly.collectAsState(emptyList())

        var department by remember {mutableStateOf("")}
        var courseNum by remember {mutableStateOf("")}
        var location by remember {mutableStateOf("")}

        OutlinedTextField(
            value = department,
            onValueChange = { newText -> department = newText},
            label = { Text("Department") }
        )
        OutlinedTextField(
            value = courseNum,
            onValueChange = { newText -> courseNum = newText},
            label = { Text("Course Number") }
        )
        OutlinedTextField(
            value = location,
            onValueChange = { newText -> location = newText},
            label = { Text("Location") }
        )

        Row{
            Button(onClick = {
                if(department != "" && courseNum != "" && location != ""){
                    vm.addCourse(mapOf("dep" to department, "num" to courseNum, "loc" to location))
                }
            }){Text("Add Course")}
            Button(onClick = {
                if(department != "" && courseNum != "" && location != ""){
                    vm.removeCourse(mapOf("dep" to department, "num" to courseNum, "loc" to location))
                }
            }){Text("Remove Course")}
        }

        Spacer(Modifier.height(20.dp))
        Text("Course List", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = Color.Blue)
        Row{
            LazyColumn{
                items(observableList) { Text("${it?.department?:""} ${it?.courseNumber?:""}",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold) }
            }
        }
    }
}