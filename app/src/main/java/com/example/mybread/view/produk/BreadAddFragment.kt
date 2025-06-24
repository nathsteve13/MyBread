package com.example.mybread.view.produk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mybread.databinding.FragmentBreadAddBinding
import com.example.mybread.model.bread.Bread
import com.example.mybread.viewmodel.BreadViewModel

class BreadAddFragment : Fragment() {
    private lateinit var binding: FragmentBreadAddBinding
    private lateinit var viewModel: BreadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreadAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(BreadViewModel::class.java)

        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val price = binding.editPrice.text.toString()
            val desc = binding.editDesc.text.toString()

            if (name.isNotEmpty() && price.isNotEmpty()) {
                val newBread = Bread(name = name, price = price, desc = desc, image = "")
                viewModel.insert(newBread)

                Toast.makeText(requireContext(), "Roti berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(it).popBackStack()
            } else {
                Toast.makeText(requireContext(), "Harap isi semua data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
