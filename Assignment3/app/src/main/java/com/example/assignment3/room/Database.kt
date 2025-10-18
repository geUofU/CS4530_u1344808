package com.example.assignment3.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
abstract class CoursesDatabase: RoomDatabase(){
    abstract fun courseDao(): CourseDao
}