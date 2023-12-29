package com.example.restauratio.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val orderListViewModel: OrderListViewModel by viewModels()

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val actionOrderToPop = R.id.action_orders_pop

    private lateinit var recyclerView: RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView

        ordersAdapter = OrdersAdapter()
        recyclerView.adapter = ordersAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionOrderToPop)
        }

        orderListViewModel.getOrderList(15)
        var orderList: List<Order>

        orderListViewModel.orderListLiveData.observe(viewLifecycleOwner) { receivedOrderList ->
            orderList = receivedOrderList
            ordersAdapter.submitList(orderList)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}