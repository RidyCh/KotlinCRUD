package com.example.onexpress.ui.home

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onexpress.R
import com.example.onexpress.data.entity.Game
import com.example.onexpress.databinding.FragmentAddGameBinding

class AddGameFragment : Fragment() {
    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!

    // ViewModel untuk menambahkan game
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)

        binding.addBtn.setOnClickListener {
            insertGameToDatabase()
        }

        return binding.root
    }

    private fun insertGameToDatabase() {
        val title = binding.etName.text.toString()
        val developer = binding.etDeveloper.text.toString()

        if (inputCheck(title, developer)) {
            // Membuat object Game baru
            val game = Game(0, title, developer)

            // Menambahkan game ke database
            gameViewModel.addGame(game)

            // Tampilkan pesan sukses
            Toast.makeText(requireContext(), "Game added successfully!", Toast.LENGTH_LONG).show()

            // Navigasi kembali ke HomeFragment
            findNavController().navigate(R.id.action_addGame_to_navigation_home)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }



    // Mengecek apakah semua input terisi
    private fun inputCheck(title: String, description: String): Boolean {
        return !(title.isEmpty() || description.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}