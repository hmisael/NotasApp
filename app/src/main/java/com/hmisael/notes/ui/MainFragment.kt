package com.hmisael.notes.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmisael.notes.*
import com.hmisael.notes.databinding.FragmentMainBinding
import com.hmisael.notes.db.entity.Note
import com.hmisael.notes.viewmodel.NoteViewModel


class MainFragment : Fragment(R.layout.fragment_main),
                    NoteClickInterface,NoteClickDeleteInterface {

    //Binding del Fragment
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //Instancia de ViewModel
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflar la vista xml del Fragment mediante binding
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Setear el LayoutManager de RecyclerView
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())

        //Inicializar Adapter
        val noteAdapter = NoteAdapter(this, this)

        //Asignar el Adapter para el RecyclerView
        binding.rvNotes.adapter = noteAdapter

        val application = requireNotNull(this.activity).application

        //Inicializar el ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)


        //Invicar el método para obtener todas las notas guardadas
        //desde el ViewModel para observar los cambios de la lista
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                //Actualizar la lista
                if (list.isEmpty()) {
                    noteAdapter.updateList(it)
                    binding.rvNotes.visibility = View.GONE
                    binding.tvNull.visibility = View.VISIBLE

                }
                else{
                    noteAdapter.updateList(it)
                    binding.rvNotes.visibility = View.VISIBLE
                    binding.tvNull.visibility = View.GONE
                }

            }
        })

        binding.fabBtn.setOnClickListener {
            //Click listener del fab button
            //para abrir el Fragment que permite agregar o modificar una nota
            //it.findNavController().navigate(R.id.action_mainFragment_to_addEditNoteFragment)

            val direction = MainFragmentDirections
                .actionMainFragmentToAddEditNoteFragment(null)
            view.findNavController().navigate(direction)
        }

    }

    override fun onNoteClick(note: Note) {
        //Abre el Fragment para modificar la nota seleccionada
        val direction = MainFragmentDirections.actionMainFragmentToAddEditNoteFragment(note)
        view?.findNavController()?.navigate(direction)
    }

    override fun onDeleteIconClick(note: Note) {
        val addDialog = AlertDialog.Builder(activity!!)
        addDialog.setTitle("Eliminar Nota")
        addDialog.setMessage("¿Desea eliminar la nota seleccionada?")
        addDialog.setPositiveButton("Eliminar", DialogInterface.OnClickListener{
                dialog, id ->
            //Se usa el método del ViewModel para eliminar la nota seleccionada
            viewModel.deleteNote(note)

            //Informar al usuario
            Toast.makeText(context, "Nota ${note.titulo} eliminada", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        })
        addDialog.setNegativeButton("Cancelar", DialogInterface.OnClickListener{
                dialog, id ->
            dialog.dismiss()
        })
        addDialog.create()
        addDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}