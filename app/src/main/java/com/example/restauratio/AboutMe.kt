package com.example.restauratio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.restauratio.databinding.FragmentAboutMeBinding

class AboutMe : Fragment() {

    private var _binding: FragmentAboutMeBinding? = null
    private val binding get() = _binding!!
    private val actionAboutMeToPop = R.id.action_aboutMe_pop
    private val actionAboutMeToEditDeliveryAddress = R.id.action_aboutMe_to_editDeliveryAddress

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutMeBinding.inflate(inflater, container, false)

        binding.button3.setOnClickListener{
            findNavController().navigate(actionAboutMeToEditDeliveryAddress)
        }

        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionAboutMeToPop)
        }

        return binding.root
    }
}