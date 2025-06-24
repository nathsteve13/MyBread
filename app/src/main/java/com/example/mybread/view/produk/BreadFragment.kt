package com.example.mybread.view.produk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybread.databinding.FragmentBreadBinding
import com.example.mybread.util.SessionManager
import com.example.mybread.viewmodel.BreadViewModel
import com.example.mybread.view.produk.BreadAdapter

class BreadFragment : Fragment() {
    private lateinit var binding: FragmentBreadBinding
    private lateinit var viewModel: BreadViewModel

    private val breadAdapter = BreadAdapter(arrayListOf()) { bread ->
        val action = BreadFragmentDirections.actionBreadFragmentToBreadDetailFragment(bread.id)
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(BreadViewModel::class.java)
        viewModel.refresh()

        binding.recViewBread.layoutManager = LinearLayoutManager(context)
        binding.recViewBread.adapter = breadAdapter

        super.onViewCreated(view, savedInstanceState)

        val session = SessionManager(requireContext())
        val role = session.getRole()

        if (role != "admin") {
            binding.btnAddBread.visibility = View.GONE
        }

        binding.btnAddBread.setOnClickListener {
            val action = BreadFragmentDirections.actionBreadFragmentToBreadAddFragment()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.breadLD.observe(viewLifecycleOwner) {
            breadAdapter.updateBreadList(it)
        }
    }
}
