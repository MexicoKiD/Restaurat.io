package com.example.restauratio.sidemenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentAboutUsLocalizationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AboutUsLocalization : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentAboutUsLocalizationBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private val actionAboutUsLocalizationToAboutUs = R.id.action_aboutUsLocalization_to_aboutUs
    private val actionAboutUsLocalizationToAboutUsOpinions =
        R.id.action_aboutUsLocalization_to_aboutUsOpinions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutUsLocalizationBinding.inflate(inflater, container, false)

        binding.imageView14.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:123456789")
            startActivity(intent)
        }

        binding.imageView15.setOnClickListener{
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=52.4056354,16.9284616")
            )
            startActivity(intent)
        }

        binding.textView10.setOnClickListener{
            findNavController().navigate(actionAboutUsLocalizationToAboutUs)
        }
        binding.imageView12.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.textView12.setOnClickListener{
            findNavController().navigate(actionAboutUsLocalizationToAboutUsOpinions)
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val location = LatLng(52.4056354,16.9284616)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        googleMap.addMarker(MarkerOptions().position(location).title("Restaurat.io"))
    }
}