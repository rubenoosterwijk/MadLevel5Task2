package com.example.madlevel5task2.database

import android.content.Context
import androidx.room.*
import com.example.madlevel5task2.converter.Converters
import com.example.madlevel5task2.dao.GameDao
import com.example.madlevel5task2.model.Game

@Database(entities = [Game::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameBacklogDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAME_BACKLOG_DB  "

        @Volatile
        private var INSTANCE: GameBacklogDatabase? = null

        fun getDatabase(context: Context): GameBacklogDatabase? {
            if (INSTANCE == null) {
                synchronized(GameBacklogDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GameBacklogDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }

    }

}
