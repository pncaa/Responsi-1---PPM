package com.example.responsi1mobileh1d023033

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.bumptech.glide.Glide
import com.example.responsi1mobileh1d023033.data.network.RetrofitInstance
import com.example.responsi1mobileh1d023033.databinding.ActivityMainBinding
import com.example.responsi1mobileh1d023033.ui.fragment.CoachFragment
import com.example.responsi1mobileh1d023033.ui.fragment.HistoryFragment
import com.example.responsi1mobileh1d023033.ui.fragment.TeamFragment
import com.example.responsi1mobileh1d023033.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val api = RetrofitInstance.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data utama tim (logo, nama, negara)
        fetchTeamMainData()

        // Navigasi antar fragment (ganti seluruh tampilan)
        binding.CardPlayers.setOnClickListener {
            showFragment(TeamFragment())
        }

        binding.CardCoach.setOnClickListener {
            showFragment(CoachFragment())
        }

        binding.CardHistory.setOnClickListener {
            showFragment(HistoryFragment())
        }

        // Tangani tombol back agar kembali ke tampilan utama
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // Jika sudah tidak ada fragment di back stack → tampilkan lagi home
                binding.homeLayout.visibility = View.VISIBLE
                binding.fragmentContainer.visibility = View.GONE
            }
        }
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        // Sembunyikan tampilan utama
        binding.homeLayout.visibility = View.GONE
        // Tampilkan container fragment
        binding.fragmentContainer.visibility = View.VISIBLE

        // Ganti fragment
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    private fun fetchTeamMainData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = api.getTeam(Constants.TEAM_ID, Constants.API_TOKEN)
                if (response.isSuccessful) {
                    val team = response.body()
                    team?.let {
                        binding.tvName.text = it.name
                        binding.tvArea.text = it.area.name

                        Glide.with(this@MainActivity)
                            .load(it.crest)
                            .into(binding.ivLogo)
                    }
                } else {
                    binding.tvName.text = "Failed to load team"
                    binding.tvArea.text = ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
                binding.tvName.text = "Error fetching data"
                binding.tvArea.text = ""
            }
        }
    }
}
