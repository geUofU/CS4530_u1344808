package com.example.funfacts

import android.util.Log
import com.example.funfacts.room.FunFactDao
import com.example.funfacts.room.FunFactEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.random.Random


class FunFactRepository(val scope: CoroutineScope,
                        private val dao: FunFactDao) {

    val allFunFacts: Flow<List<FunFactEntity?>> = dao.getAllFacts()

    fun addFact(text: String){
        scope.launch{
            delay(1000)
            val factObj = FunFactEntity(text = text)
            dao.insertFunFact(factObj)
        }
    }
}