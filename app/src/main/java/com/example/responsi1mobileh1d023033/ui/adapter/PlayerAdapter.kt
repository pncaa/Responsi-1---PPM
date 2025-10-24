package com.example.responsi1mobileh1d023033.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.responsi1mobileh1d023033.data.model.Player
import com.example.responsi1mobileh1d023033.databinding.ItemPlayerBinding

class PlayerAdapter(
    private val players: List<Player>,
    private val onItemClick: ((Player) -> Unit)? = null // opsional listener
) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            binding.apply {
                // Set teks
                tvName.text = player.name ?: "Unknown"
                tvPos.text = player.position ?: "Unknown"
                tvNation.text = player.nationality ?: "Unknown"

                // Warna background berdasarkan posisi
                val positionNormalized = player.position?.lowercase()?.trim()
                val color = when (positionNormalized) {
                    "goalkeeper", "gk" -> 0xFFFFFF00.toInt() // Kuning
                    "defender", "df" -> 0xFF2196F3.toInt()   // Biru
                    "midfielder", "mf" -> 0xFF4CAF50.toInt() // Hijau
                    "forward", "fw", "striker" -> 0xFFF44336.toInt() // Merah
                    else -> 0xFFFFFFFF.toInt() // Putih default
                }
                cardView.setCardBackgroundColor(color)

                // Listener klik opsional
                root.setOnClickListener {
                    onItemClick?.invoke(player)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(players[position])
    }
}
