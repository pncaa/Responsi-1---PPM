package com.example.responsi1mobileh1d023033.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.responsi1mobileh1d023033.data.network.RetrofitInstance
import com.example.responsi1mobileh1d023033.databinding.FragmentCoachBinding
import com.example.responsi1mobileh1d023033.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoachFragment : Fragment() {

    private var _binding: FragmentCoachBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitInstance.api

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoachBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = api.getTeam(Constants.TEAM_ID, Constants.API_TOKEN)
                if (response.isSuccessful) {
                    val team = response.body()
                    val coach = team?.coach
                    coach?.let {
                        binding.tvCoach.text = it.name
                        binding.tvNation.text = it.nationality
                    }
                } else {
                    binding.tvCoach.text = "Gagal memuat pelatih"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                binding.tvCoach.text = "Terjadi kesalahan jaringan"
            }
        }

        binding.btnKembali.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
