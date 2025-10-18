package com.example.assignment3

import android.app.Application
import androidx.room.Room
import com.example.assignment3.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CoursesApp: Application(){
val scope = CoroutineScope(SupervisorJob())

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "myDB"
        ).build()
    }

    val repository by lazy { Repository(scope, db.courseDao()) }
}