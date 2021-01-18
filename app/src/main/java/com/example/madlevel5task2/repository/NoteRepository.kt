package com.example.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.madlevel5task2.dao.NoteDao
import com.example.madlevel5task2.database.NotepadRoomDatabase
import com.example.madlevel5task2.model.Note

class NoteRepository(context: Context)  {

   private val noteDao: NoteDao

    init {
       val database = NotepadRoomDatabase.getDatabase(context)
       noteDao = database!!.noteDao()
   }

   fun getNotepad(): LiveData<Note?> {
       return noteDao.getNotepad()
   }

   suspend fun updateNotepad(note: Note) {
       noteDao.updateNote(note)
   }

}
