package com.example.restauratio.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val orderViewModel: OrderViewModel by viewModels()

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val actionOrderToPop = R.id.action_orders_pop

    private lateinit var orderAdapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        orderAdapter = OrderAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = orderAdapter

        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionOrderToPop)
        }

        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            orderAdapter.submitList(orders)
        }

        orderViewModel.getOrders(null)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}