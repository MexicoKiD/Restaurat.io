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
import com.example.restauratio.databinding.FragmentAboutUsBinding


class AboutUs : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!
    private val actionAboutUsToAboutUsLocalization = R.id.action_aboutUs_to_aboutUsLocalization
    private val actionAboutUsToAboutUsOpinions = R.id.action_aboutUs_to_aboutUsOpinions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)

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

        binding.textView11.setOnClickListener{
            findNavController().navigate(actionAboutUsToAboutUsLocalization)
        }
        binding.imageView12.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.textView12.setOnClickListener{
            findNavController().navigate(actionAboutUsToAboutUsOpinions)
        }

        return binding.root
    }
}