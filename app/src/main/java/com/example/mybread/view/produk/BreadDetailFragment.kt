package com.example.mybread.view.produk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mybread.databinding.FragmentBreadDetailBinding
import com.example.mybread.viewmodel.BreadViewModel

class BreadDetailFragment : Fragment() {
    private lateinit var binding: FragmentBreadDetailBinding
    private lateinit var viewModel: BreadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreadDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = BreadDetailFragmentArgs.fromBundle(requireArguments()).breadId

        viewModel = ViewModelProvider(this).get(BreadViewModel::class.java)
        viewModel.load(id)

        viewModel.breadSingleLD.observe(viewLifecycleOwner) { bread ->
            binding.txtDetailName.text = bread.name
            binding.txtDetailPrice.text = "Rp ${bread.price}"
            binding.txtDetailDesc.text = bread.desc
        }
    }
}
