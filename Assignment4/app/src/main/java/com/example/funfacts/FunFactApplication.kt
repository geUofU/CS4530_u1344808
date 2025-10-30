package com.example.funfacts

import android.app.Application
import androidx.room.Room
import com.example.funfacts.room.FunFactDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob


class FunFactApplication: Application() {
    val scope = CoroutineScope(SupervisorJob())

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FunFactDatabase::class.java,
            "fun_fact_database"
        ).build()
    }

    val funFactRepository by lazy {FunFactRepository(scope, db.FunFactDao())}
}