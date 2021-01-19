package com.example.madlevel5task2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.item_card_game_backlog.view.*
import java.text.SimpleDateFormat

class GameBacklogAdapter (private val Games: List<Game>) : RecyclerView.Adapter<GameBacklogAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            itemView.tvGameTitle.text = game.title
            itemView.tvGamePlatforms.text = game.platform

            val sdf = SimpleDateFormat("d MMMM yyyy")
            itemView.tvGameReleaseDate.text = sdf.format(game.release).toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_game_backlog, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return Games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(Games[position])
    }


}