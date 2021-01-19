package com.example.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.database.GameBacklogDatabase
import com.example.madlevel5task2.dao.GameDao
import com.example.madlevel5task2.model.Game


class GameRepository(context: Context) {
    private val gameDao: GameDao


    init {
        val database = GameBacklogDatabase.getDatabase(context)
        gameDao =  database!!.gameDao()
    }

    fun getGameBacklog(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames() ?: MutableLiveData(emptyList())
    }


    suspend fun updateBacklog(game: Game) {
        gameDao.updateGame(game)
    }

    suspend fun insertGame(game: Game ) {
        gameDao?.insertGame(game);
    }

    suspend fun deleteAll(){
        gameDao?.deleteAll()
    }
}
