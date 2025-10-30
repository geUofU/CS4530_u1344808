package com.example.funfacts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewModelScope
import com.example.funfacts.room.FunFactEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class FunFact (var text:String, var source_url:String?=null)


class FunFactViewModel(val repository: FunFactRepository) : ViewModel()
{
    private val client = HttpClient(Android)
    {
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    // model which is my data
    val funListReadOnly : StateFlow<List<FunFactEntity?>> = repository.allFunFacts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // my methods that will update the model
    suspend fun addItem (item: String){
        try{
            val responseText: FunFact = client.get("https://uselessfacts.jsph.pl//api/v2/facts/random").body()
            repository.addFact(responseText.text)
        }
        catch (e: Exception)
        {
            Log.e("FunFact Activity", "Error fetching", e)
        }
    }
}

object FunFactViewModelProvider{
    val Factory = viewModelFactory {
        initializer{
            FunFactViewModel(
                (this[AndroidViewModelFactory.APPLICATION_KEY]
                        as FunFactApplication).funFactRepository
            )

        }

    }

}
