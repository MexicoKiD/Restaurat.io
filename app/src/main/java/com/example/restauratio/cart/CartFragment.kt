package com.example.restauratio.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter

    private val actionCartToPop = R.id.action_cartFragment_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(cartViewModel)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = cartAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            Log.d("CartFragment", "Observed cart items: $cartItems")
            cartAdapter.setCartItems(cartItems)
        }

        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionCartToPop)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}