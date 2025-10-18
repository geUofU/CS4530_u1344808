package com.example.assignment3

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.assignment3.room.CourseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer

class MyViewModel(private val repository: Repository) : ViewModel() {
    // Model (using repository)

    val coursesReadOnly: Flow<List<CourseEntity?>> =repository.allCourses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Add a course to the list
    fun addCourse(inputMap: Map<String, String>){
        val dep = inputMap["dep"]
        val num = inputMap["num"]
        val loc = inputMap["loc"]
        if(dep != null && num != null && loc != null){
            repository.addCourse(dep, num, loc)
        }
    }

    // Remove a course from the list
    fun removeCourse(inputMap: Map<String, String>){
        val dep = inputMap["dep"]
        val num = inputMap["num"]
        val loc = inputMap["loc"]
        if(dep != null && num != null && loc != null){
            repository.deleteCourse(dep, num, loc)
        }
    }
}

object MyViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MyViewModel(
                (this[AndroidViewModelFactory.APPLICATION_KEY]
                        as CoursesApp).repository
            )
        }
    }
}