package com.example.restauratio.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentEditAddressBinding

class EditDeliveryAddress : Fragment() {

    private var _binding: FragmentEditAddressBinding? = null
    private val binding get() = _binding!!
    private val actionEditDeliveryAddressToPop = R.id.action_editDeliveryAddress_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAddressBinding.inflate(inflater, container, false)

        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionEditDeliveryAddressToPop)
        }

        binding.button3.setOnClickListener{
            findNavController().navigate(actionEditDeliveryAddressToPop)
        }

        return binding.root
    }
}