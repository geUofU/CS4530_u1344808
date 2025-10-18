package com.example.assignment3

import com.example.assignment3.room.CourseDao
import com.example.assignment3.room.CourseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Repository (val scope: CoroutineScope, private val dao: CourseDao){
    val allCourses: Flow<List<CourseEntity?>> = dao.getAllCourses()

    fun addCourse(dep: String, num: String, loc: String){
        scope.launch{
            delay(1000)
            val courseObj = CourseEntity(department = dep, courseNumber = num, location = loc)
            dao.insertCourse(courseObj)
        }
    }

    fun deleteCourse(dep: String, num: String, loc: String){
        scope.launch{
            delay(1000)
            val courseObj = CourseEntity(department = dep, courseNumber = num, location = loc)
            dao.deleteCourse(courseObj)
        }
    }
}