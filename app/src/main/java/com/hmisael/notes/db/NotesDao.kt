package com.hmisael.notes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hmisael.notes.db.entity.Note

@Dao
interface NotesDao {

    //Método para agregar una nueva entrada a la base de datos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Note)

    //Método para eliminar una nota específica de la base de datos
    @Delete
    suspend fun delete(note: Note)

    //Método para obtener todas las notas guardadas de la base de datos
    //Se describe un orden ascendente por fecha de modificación
    @Query("Select * from notes order by date ASC")
    fun getAllNotes(): LiveData<List<Note>>

    //Método para actualizar una nota específica
    @Update
    suspend fun update(note: Note)




}