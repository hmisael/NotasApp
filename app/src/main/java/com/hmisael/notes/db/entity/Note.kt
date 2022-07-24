package com.hmisael.notes.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//Nombre de la tabla
@Entity(tableName = "notes")
@Parcelize
data class Note (
    // Id llave primaria, autoincremental ,con valor 0L por defecto
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "title")
            val titulo :String,
            @ColumnInfo(name = "description")
            val descripcion :String,
            @ColumnInfo(name = "date")
            val fecha :String) : Parcelable


