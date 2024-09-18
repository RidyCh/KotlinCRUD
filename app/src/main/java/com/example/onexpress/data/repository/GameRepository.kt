package com.example.onexpress.data.repository

import androidx.lifecycle.LiveData
import com.example.onexpress.data.dao.GameDao
import com.example.onexpress.data.entity.Game

class GameRepository(private val gameDao: GameDao) {
    val getAllGames: LiveData<List<Game>> = gameDao.getAllGames()

    suspend fun addGame(game: Game){
        gameDao.addGame(game)
    }

    suspend fun updateGame(game: Game) {
        gameDao.updateGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }
}