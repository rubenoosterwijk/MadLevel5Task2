package com.example.madlevel5task1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.madlevel5task1.R
import com.example.madlevel5task1.databinding.FragmentNotepadBinding
import com.example.madlevel5task1.viewmodel.NoteViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [NotepadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotepadFragment : Fragment() {


    private var _binding: FragmentNotepadBinding? = null
    private val binding get() = _binding!!


    // TODO: Rename and change types of parameters
    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAddNoteResult()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotepadBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeAddNoteResult() {
        viewModel.note.observe(viewLifecycleOwner, Observer{ note ->
            note?.let {
                binding.tvNoteTitle.text = it.title
                binding.tvLastUpdated.text = getString(R.string.last_updated, it.lastUpdated.toString())
                binding.tvNoteText.text = it.text
            }
        })
    }

}