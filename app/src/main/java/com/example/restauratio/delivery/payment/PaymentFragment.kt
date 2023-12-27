package com.example.restauratio.delivery.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val actionPaymentToSummary = R.id.action_paymentFragment_to_summaryFragment
    private val actionPaymentPop = R.id.action_paymentFragment_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)

        binding.buttonSave.setOnClickListener {
            findNavController().navigate(actionPaymentToSummary)
        }

        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionPaymentPop)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}