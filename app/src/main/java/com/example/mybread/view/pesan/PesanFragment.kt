package com.example.mybread.view.pesan

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mybread.databinding.FragmentPesanBinding
import com.example.mybread.util.SessionManager
import com.example.mybread.viewmodel.PesananViewModel

class PesanFragment : Fragment() {
    private lateinit var binding: FragmentPesanBinding
    private lateinit var viewModel: PesananViewModel

    private var breadId: Int = 0
    private var price: Int = 0
    private var latitude: String = "0.0"
    private var longitude: String = "0.0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPesanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[PesananViewModel::class.java]

        val args = PesanFragmentArgs.fromBundle(requireArguments())
        breadId = args.breadId
        price = args.price

        binding.txtBreadId.text = "Bread ID: $breadId"
        binding.txtPrice.text = "Harga satuan: Rp $price"

        requestLocationPermission()
        getCurrentLocation { lat, lon ->
            latitude = lat
            longitude = lon
        }

        binding.btnConfirm.setOnClickListener {
            val quantityStr = binding.edtQuantity.text.toString()
            val quantity = quantityStr.toIntOrNull()

            val session = SessionManager(requireContext())
            val userId = session.getUserId()

            if (quantity != null && quantity > 0) {
                viewModel.buatPesanan(userId, breadId, quantity, price, latitude, longitude)
                Toast.makeText(context, "Pesanan berhasil dibuat", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(context, "Jumlah tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
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
