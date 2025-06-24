package com.example.mybread.view.laporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybread.R
import com.example.mybread.databinding.FragmentLaporanBinding
import com.example.mybread.model.pesanan.Pesanan
import com.example.mybread.viewmodel.LaporanViewModel

class LaporanFragment : Fragment() {
    private lateinit var binding: FragmentLaporanBinding
    private lateinit var viewModel: LaporanViewModel
    private lateinit var adapter: LaporanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaporanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[LaporanViewModel::class.java]
        adapter = LaporanAdapter(arrayListOf()) { pesananId ->
            viewModel.updateStatus(pesananId, "accepted")
        }

        binding.rvLaporan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLaporan.adapter = adapter

        observeViewModel()
        viewModel.fetchAllLaporan()
    }

    private fun observeViewModel() {
        viewModel.laporanLD.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }
}
