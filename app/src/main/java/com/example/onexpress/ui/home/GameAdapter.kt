package com.example.onexpress.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onexpress.R
import com.example.onexpress.data.entity.Game

class GameAdapter(private val updateGame: (Game) -> Unit, private val deleteGame: (Game) -> Unit) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var gameList = emptyList<Game>()

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.game_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.game_description)
        val updateButton: ImageButton = itemView.findViewById(R.id.edit_button)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentGame = gameList[position]
        holder.titleTextView.text = currentGame.name
        holder.descriptionTextView.text = currentGame.developer

        // Handle update button click
        holder.updateButton.setOnClickListener {
            updateGame(currentGame)
        }

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            deleteGame(currentGame)
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun setGames(games: List<Game>) {
        this.gameList = games
        notifyDataSetChanged()
    }
}