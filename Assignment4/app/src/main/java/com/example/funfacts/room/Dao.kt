package com.example.funfacts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface FunFactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFunFact (funFact: FunFactEntity)

    @Query("select * from funfacts order by id desc")
    fun getAllFacts(): Flow<List<FunFactEntity>>
}