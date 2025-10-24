package com.example.responsi1mobileh1d023033.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.responsi1mobileh1d023033.data.model.Team
import com.example.responsi1mobileh1d023033.data.network.RetrofitInstance
import com.example.responsi1mobileh1d023033.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    private val _team = MutableStateFlow<Team?>(null)
    val team: StateFlow<Team?> = _team

    fun fetchTeam() {
        viewModelScope.launch {
            try {
                // 🔹 P   anggil API dengan ID Sassuolo (471) dan token dari Constants
                val response = RetrofitInstance.api.getTeam(Constants.TEAM_ID, Constants.API_TOKEN)

                if (response.isSuccessful) {
                    val teamData = response.body()
                    _team.value = teamData

                    // 🔹 Tambahkan log debug
                    teamData?.let {
                        Log.d("PlayerList", "✅ Data tim diterima: ${it.name}")
                        Log.d("PlayerList", "Jumlah pemain: ${it.squad.size}")
                        it.squad.forEach { player ->
                            Log.d(
                                "PlayerList",
                                "Nama: ${player.name}, Posisi: ${player.position}, Negara: ${player.nationality}"
                            )
                        }
                    }
                } else {
                    Log.e("PlayerList", "❌ Gagal memuat data tim. Code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("PlayerList", "⚠️ Error saat fetchTeam(): ${e.message}")
                e.printStackTrace()
            }
        }
    }
}
