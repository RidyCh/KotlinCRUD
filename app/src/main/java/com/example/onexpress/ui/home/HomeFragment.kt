package com.example.onexpress.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.InvalidationTracker
import com.example.onexpress.R
import com.example.onexpress.data.entity.Game
import com.example.onexpress.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val gameViewModel: GameViewModel by viewModels()

    // Adapter
//    private val adapter = GameAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup RecyclerView with adapter
        val adapter = GameAdapter(
            updateGame = { game -> updateGameDialog(game) },  // Handle update action
            deleteGame = { game -> deleteGameDialog(game) }   // Handle delete action
        )
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // Observe the LiveData from the ViewModel
        gameViewModel.getAllGames.observe(viewLifecycleOwner, { games ->
            adapter.setGames(games)
        })

        // Floating Action Button to navigate to Add Game Fragment
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addGame)
        }

        // Add options menu (e.g., delete all games)
        setHasOptionsMenu(true)

        return view
    }


    private fun updateGameDialog(game: Game) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_edit
            , null)

        val editTextName = dialogLayout.findViewById<EditText>(R.id.editTextName)
        val editTextDescription = dialogLayout.findViewById<EditText>(R.id.editTextDeveloper)

        // Set current game details in the dialog
        editTextName.setText(game.name)
        editTextDescription.setText(game.developer)

        builder.setView(dialogLayout)
        builder.setTitle("Update Game")
        builder.setPositiveButton("Update") { _, _ ->
            // Update game details
            val updatedGame = Game(game.id, editTextName.text.toString(), editTextDescription.text.toString())
            gameViewModel.updateGame(updatedGame)
            Toast.makeText(requireContext(), "Game updated successfully!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun deleteGameDialog(game: Game) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Game")
        builder.setMessage("Are you sure you want to delete ${game.name}?")

        builder.setPositiveButton("Yes") { _, _ ->
            // Delete the game
            gameViewModel.deleteGame(game)
            Toast.makeText(requireContext(), "Game deleted successfully!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}