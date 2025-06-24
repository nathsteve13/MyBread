package com.example.mybread.view.pesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybread.R
import com.example.mybread.databinding.FragmentPesananBinding
import com.example.mybread.util.SessionManager
import com.example.mybread.viewmodel.PesananViewModel

class PesananFragment : Fragment() {
    private lateinit var binding: FragmentPesananBinding
    private lateinit var viewModel: PesananViewModel
    private val adapter = PesananAdapter(arrayListOf()) { pesananId ->
        val action = PesananFragmentDirections.actionPesananFragmentToDetailPesananFragment(pesananId)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userId = SessionManager(requireContext()).getUserId()
        viewModel = ViewModelProvider(this).get(PesananViewModel::class.java)
        viewModel.getPesananByUser(userId)

        binding.recViewPesanan.layoutManager = LinearLayoutManager(context)
        binding.recViewPesanan.adapter = adapter

        viewModel.pesananLD.observe(viewLifecycleOwner) {
            adapter.updatePesananList(it)
        }
    }
}
