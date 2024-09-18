package com.example.onexpress.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.onexpress.data.MyDatabase
import com.example.onexpress.data.entity.Game
import com.example.onexpress.data.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application): AndroidViewModel(application) {

    val getAllGames: LiveData<List<Game>>
    private val repository: GameRepository

    init {
        val gameDao = MyDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gameDao)
        getAllGames = repository.getAllGames
    }

    fun addGame(game: Game){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGame(game)
        }
    }

    fun updateGame(game: Game){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGame(game)
        }
    }

    fun deleteGame(game: Game){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGame(game)
        }
    }
}