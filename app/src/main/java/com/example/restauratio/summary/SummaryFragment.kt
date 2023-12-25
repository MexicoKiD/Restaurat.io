package com.example.restauratio.summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var summaryAdapter: SummaryAdapter

    private val actionSummaryPop = R.id.action_summaryFragment_pop
    private val action = R.id.action_global_menu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = summaryAdapter

        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionSummaryPop)
        }

        binding.buttonSave.setOnClickListener {
            findNavController().popBackStack(R.id.menu, true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}