package com.example.assignment3.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    suspend fun deleteCourse(course: CourseEntity){
        val courseNum = course.courseNumber
        val department = course.department
        val location = course.location
        deleteCourseActual(department, courseNum, location)
    }

    @Query("delete from courses where department = :department AND courseNumber = :courseNum " +
            "AND location = :location")
    suspend fun deleteCourseActual(department: String, courseNum: String, location: String)

    @Query("select * from courses order by id desc")
    fun getAllCourses():Flow<List<CourseEntity>>
}