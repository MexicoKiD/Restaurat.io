package com.example.restauratio.delivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentDeliveryBinding

class DeliveryFragment : Fragment() {

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!

    private val actionDeliveryToPayment = R.id.action_deliveryFragment_to_paymentFragment
    private val actionDeliveryPop = R.id.action_deliveryFragment_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)

        binding.buttonSave.setOnClickListener {
            findNavController().navigate(actionDeliveryToPayment)
        }
        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionDeliveryPop)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}