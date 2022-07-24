package com.hmisael.notes.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hmisael.notes.R
import com.hmisael.notes.db.entity.Note

class NoteAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) :    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    //Crear variable de lista de Notas
    private val allNotes = ArrayList<Note>()

    //Crear clase View Holder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Crear e inicializar los elementos de la vista
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Inflar el layout para cada ítem del RecyclerView
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Asignar los datos de c/ítem del RecyclerView
        holder.noteTV.setText(allNotes.get(position).titulo)
        val fecha = "Fecha de modificación: " + allNotes.get(position).fecha
        holder.dateTV.setText(fecha)
        //Agregar click listener para el ícono de borrado de c/item
        holder.deleteIV.setOnClickListener {
            //Invocar un método de borrado al click interface
            // enviando la posición actual
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }

        //Agregar un click listener al ítem del RecyclerView
        holder.itemView.setOnClickListener {
            //Invocar un método de selección al click interface
            // enviando la posición actual
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        //Devuelve tamaño de la lista
        return allNotes.size
    }

    //Actualizar el listado de notas
    fun updateList(newList: List<Note>) {
        //Limpiar el listado de notas
        allNotes.clear()
        //Agregar una nueva lista de notas
        allNotes.addAll(newList)
        //Método para notificar al adapter la actualización del listado de notas
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface {
    //Evento de click en ícono de borrado
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    //Evento de click en un ítem (nota) para actualizar
    fun onNoteClick(note: Note)
}