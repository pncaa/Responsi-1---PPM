package com.example.responsi1mobileh1d023033.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.responsi1mobileh1d023033.data.network.RetrofitInstance
import com.example.responsi1mobileh1d023033.databinding.FragmentTeamBinding
import com.example.responsi1mobileh1d023033.ui.adapter.PlayerAdapter
import com.example.responsi1mobileh1d023033.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamFragment : Fragment() {

    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitInstance.api

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol kembali ke halaman utama
        binding.btnKembali.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Atur layout recycler
        binding.rvPlayers.layoutManager = LinearLayoutManager(requireContext())

        // Ambil data pemain
        fetchPlayers()
    }

    private fun fetchPlayers() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = api.getTeam(Constants.TEAM_ID, Constants.API_TOKEN)
                if (response.isSuccessful) {
                    val team = response.body()
                    team?.let {
                        // Ambil hanya pemain (yang punya posisi)
                        val players = it.squad.filter { player ->
                            !player.position.isNullOrBlank()
                        }

                        if (players.isNotEmpty()) {
                            binding.rvPlayers.adapter = PlayerAdapter(players)
                            binding.tvError.visibility = View.GONE
                        } else {
                            showError("Tidak ada data pemain ditemukan.")
                        }
                    } ?: showError("Data tim kosong.")
                } else {
                    showError("Gagal memuat pemain (kode: ${response.code()}).")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError("Terjadi kesalahan jaringan: ${e.message}")
            }
        }
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
