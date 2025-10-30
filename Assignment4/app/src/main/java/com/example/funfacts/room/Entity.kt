package com.example.funfacts.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "funfacts")
data class FunFactEntity(val text: String,
                         @PrimaryKey(autoGenerate = true) val id: Int = 0)