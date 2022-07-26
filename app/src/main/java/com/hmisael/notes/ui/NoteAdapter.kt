package com.hmisael.notes.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmisael.notes.databinding.NoteItemBinding
import com.hmisael.notes.db.entity.Note

class NoteAdapter(val noteClickDeleteInterface: NoteClickDeleteInterface,
                  val noteClickInterface: NoteClickInterface) :
                    RecyclerView.Adapter<NoteAdapter.NotaViewHolder>() {

    //Crear variable de lista de Notas
    private val allNotes = ArrayList<Note>()

    //Crear clase View Holder
    class NotaViewHolder(val itemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        return NotaViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        //Asignar los datos de c/ítem del RecyclerView
        holder.itemBinding.idTVNote.setText(allNotes.get(position).title)
        val fecha = "Fecha de modificación: " + allNotes.get(position).date
        holder.itemBinding.idTVDate.setText(fecha)
        //Agregar click listener para el ícono de borrado de c/item
        holder.itemBinding.idIVDelete.setOnClickListener {
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