package com.example.mybread.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mybread.databinding.FragmentSignUpBinding
import com.example.mybread.viewmodel.UserViewModel
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mybread.model.user.User

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            val repeatPassword = binding.txtRepeatPassword.text.toString()
            val name = binding.txtName.text.toString()

            if (password != repeatPassword) {
                Toast.makeText(context, "Password tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val role = if (username.lowercase() == "admin") "admin" else "customer"
            val user = User(username = username, password = password, name = name, latitude = "", longtitude = "", nomor_hp = "", role = role)

            viewModel.register(user)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.registerResultLD.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Register sukses, silakan login", Toast.LENGTH_SHORT).show()
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                Navigation.findNavController(requireView()).navigate(action)
            } else {
                Toast.makeText(context, "Username sudah digunakan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
