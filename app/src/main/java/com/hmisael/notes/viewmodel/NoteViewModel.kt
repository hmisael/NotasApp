package com.hmisael.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmisael.notes.db.entity.Note
import com.hmisael.notes.db.NoteRepository
import com.hmisael.notes.db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel (application: Application) : AndroidViewModel(application) {

    //Crear variable para la lista de notas
    val allNotes : LiveData<List<Note>>
    //Crear variable para el Repositorio
    val repository : NoteRepository

    //Inicializar el dao, repositorio y la lista de notas
    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    //Crear nuevo método para borrar una nota, invocando método del repositorio
    fun deleteNote (note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    //Crear nuevo método para modificar una nota, invocando método del repositorio
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    //Crear nuevo método para agregar una nota, invocando método del repositorio
    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}