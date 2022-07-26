package com.hmisael.notes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.hmisael.notes.*
import com.hmisael.notes.databinding.FragmentAddEditNoteBinding
import com.hmisael.notes.db.entity.Note
import com.hmisael.notes.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    //Binding del Fragment
    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    //Instancia de ViewModel
    lateinit var viewModel: NoteViewModel

    //Obtener argumentos vía navigation (nota)
    private val args: AddEditNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //Inflar la vista xml del Fragment mediante binding
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application

        //Inicializar ViewModel
        viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        //Obtengo recurso Nota mediante los argumentos de navigation
        val note = args.nota

        if (note==null){
            binding.fabBtnOk.setText(R.string.btn_text)
        }
        else{
            binding.fabBtnOk.setText(R.string.btn_text_mod)
            binding.etDescription.setText(note.description)
            binding.etTitle.setText(note.title)
        }

        //Click listener para guardar la nueva nota o una actualización de nota
        binding.fabBtnOk.setOnClickListener {
            //Obtener en una variable el título y descripción
            val titleNote = binding.etTitle.text.toString()
            val descriptionNote = binding.etDescription.text.toString()

            //Si el argumento es null, se guardan los datos en una nueva nota
            if (note == null ) {
                //Verifico que el usuario ingrese la información solicitada
                if (titleNote.isNotEmpty() && descriptionNote.isNotEmpty()) {
                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val today: String = dateFormat.format(Date())

                    //Se invoca método de ViewModel para guardar una nueva nota
                    viewModel.addNote(Note(0L,titleNote, descriptionNote, today))
                    Toast.makeText(context, "$titleNote agregada con éxito :)", Toast.LENGTH_LONG).show()

                    val direction = AddEditNoteFragmentDirections
                        .actionAddEditNoteFragmentToMainFragment()
                    view.findNavController().navigate(direction)
                }
                else{
                    Toast.makeText(context, "Por favor ingrese un título y una descripción", Toast.LENGTH_LONG).show()
                }
            //Si el argumento no es null, se actualizan los datos de la nota
            } else {
                //Los datos de la nota son actualizados
                if (titleNote.isNotEmpty() && descriptionNote.isNotEmpty()) {
                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val today: String = dateFormat.format(Date())

                    //Se invoca método de ViewModel para actualizar los datos de una nota
                    val updatedNote = Note(note.id, titleNote, descriptionNote, today)
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(context, "Nota actualizada con éxito :)", Toast.LENGTH_LONG).show()

                    val direction = AddEditNoteFragmentDirections
                        .actionAddEditNoteFragmentToMainFragment()
                    view.findNavController().navigate(direction)
                }
                else{
                    Toast.makeText(context, "Por favor ingrese un título y una descripción", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}