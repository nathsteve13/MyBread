package com.example.mybread.view.pesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybread.R
import com.example.mybread.databinding.FragmentDetailPesananBinding
import com.example.mybread.viewmodel.PesananViewModel

class DetailPesananFragment : Fragment() {
    private lateinit var binding: FragmentDetailPesananBinding
    private lateinit var viewModel: PesananViewModel
    private val adapter = DetailPesananAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pesananId = DetailPesananFragmentArgs.fromBundle(requireArguments()).pesananId
        viewModel = ViewModelProvider(this).get(PesananViewModel::class.java)
        viewModel.getDetailByPesanan(pesananId)

        binding.recViewDetailPesanan.layoutManager = LinearLayoutManager(context)
        binding.recViewDetailPesanan.adapter = adapter

        viewModel.detailPesananLD.observe(viewLifecycleOwner) {
            adapter.updateDetailList(it)
        }
    }
}
