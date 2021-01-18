package com.example.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.madlevel5task2.model.Game

@Dao
interface GameBacklogDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM gamebacklogtable")
    fun getAllGames(): LiveData<List<Game?>>

    @Update
    suspend fun updateGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM gamebacklogtable")
    suspend fun deleteGames()

}