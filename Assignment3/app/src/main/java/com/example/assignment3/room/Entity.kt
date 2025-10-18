package com.example.assignment3.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val department: String,
    val courseNumber: String,
    val location: String
)
