package com.example.mybread.view.auth

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mybread.databinding.FragmentSignInBinding
import com.example.mybread.util.SessionManager
import com.example.mybread.view.MainActivity
import com.example.mybread.viewmodel.UserViewModel

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        requestLocationPermission()

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Username and Password must not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            getCurrentLocation { latitude, longitude ->
                Log.d("LOCATION", "Latitude: $latitude, Longitude: $longitude")
                viewModel.login(username, password)
                observeLogin(latitude, longitude)
            }
        }

        binding.txtRegister.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeLogin(latitude: String, longitude: String) {
        viewModel.loginResultLD.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                viewModel.updateUserLocation(user.username, latitude, longitude)

                val session = SessionManager(requireContext())
                session.saveLoginSession(user.id, user.username, user.password, user.role)


                Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    private fun getCurrentLocation(callback: (String, String) -> Unit) {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback("0.0", "0.0")
            return
        }

        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            callback(location.latitude.toString(), location.longitude.toString())
        } else {
            callback("0.0", "0.0")
        }
    }
}
