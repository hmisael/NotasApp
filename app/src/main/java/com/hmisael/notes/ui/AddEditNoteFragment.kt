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
    //var id = -1;

    //Obtener argumentos vía navigation (nota)
    private val args: AddEditNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val nota = args.nota

        if (nota==null){
            binding.fabBtnOk.setText(R.string.btn_text)
        }
        else{
            binding.fabBtnOk.setText(R.string.btn_text_mod)
            binding.etDescription.setText(nota.descripcion)
            binding.etTitle.setText(nota.titulo)
        }

        //Click listener para guardar la nueva nota o una actualización de nota
        binding.fabBtnOk.setOnClickListener {
            //Obtener en una variable el título y descripción
            val tituloNota = binding.etTitle.text.toString()
            val descripcionNota = binding.etDescription.text.toString()

            //Si el argumento es null, se guardan los datos en una nueva nota
            if (nota == null ) {
                //Verifico que el usuario ingrese la información solicitada
                if (tituloNota.isNotEmpty() && descripcionNota.isNotEmpty()) {
                    val formatoFecha = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val fechaActual: String = formatoFecha.format(Date())

                    //Se invoca método de ViewModel para guardar una nueva nota
                    viewModel.addNote(Note(0L,tituloNota, descripcionNota, fechaActual))
                    Toast.makeText(context, "$tituloNota agregada con éxito :)", Toast.LENGTH_LONG).show()

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
                if (tituloNota.isNotEmpty() && descripcionNota.isNotEmpty()) {
                    val date = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val fechaActual: String = date.format(Date())

                    //Se invoca método de ViewModel para actualizar los datos de una nota
                    val updatedNote = Note(nota.id, tituloNota, descripcionNota, fechaActual)
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