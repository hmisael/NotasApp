package com.hmisael.notes.db


import androidx.lifecycle.LiveData
import com.hmisael.notes.db.NotesDao
import com.hmisael.notes.db.entity.Note

class NoteRepository(private val notesDao: NotesDao) {

    //Obtener todas las notas guardadas desde la clase DAO
    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    //Agregar una nueva nota
    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    //Borrar una nota
    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    //Actualiza los datos de una nota específica
    suspend fun update(note: Note){
        notesDao.update(note)
    }
}