package com.example.madlevel5task1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task1.R
import com.example.madlevel5task1.databinding.FragmentAddNoteBinding
import com.example.madlevel5task1.databinding.FragmentNotepadBinding
import com.example.madlevel5task1.viewmodel.NoteViewModel

class AddNoteFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModels()

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            saveNote()
        }
        observeNote()
    }

    private fun observeNote() {
//fill the text fields with the current text and title from the viewmodel
        viewModel.note.observe(viewLifecycleOwner, { note ->
            note?.let {
                binding.tilNoteTitle.editText?.setText(it.title)
                binding.tilNoteText.editText?.setText(it.text)
            }

        })

        viewModel.error.observe(viewLifecycleOwner, { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.success.observe(viewLifecycleOwner, {
            //"pop" the backstack, this means we destroy this    fragment and go back to the RemindersFragment
            findNavController().popBackStack()
        })
    }

    private fun saveNote() {
        viewModel.updateNote(
            binding.tilNoteTitle.editText?.text.toString(),
            binding.tilNoteText.editText?.text.toString()
        )
    }





}