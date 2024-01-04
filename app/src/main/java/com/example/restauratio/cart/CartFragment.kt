package com.example.restauratio.cart

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentCartBinding
import com.example.restauratio.databinding.FragmentEmptyCartBinding
import com.example.restauratio.products.ProductDetailsViewModel
import com.google.android.gms.dynamic.SupportFragmentWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by activityViewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by activityViewModels()

    private var _bindingCart: FragmentCartBinding? = null
    private val bindingCart get() = _bindingCart!!

    private var _bindingEmptyCart: FragmentEmptyCartBinding? = null
    private val bindingEmptyCart get() = _bindingEmptyCart!!

    private lateinit var cartAdapter: CartAdapter

    private val actionCartToPop = R.id.action_cartFragment_pop
    private val actionCartToProductDetails = R.id.action_global_productDetailFragment
    private val actionCartToDeliveryFragment = R.id.action_cartFragment_to_deliveryFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return if (cartViewModel.cartItems.value?.isEmpty() != false){
            _bindingEmptyCart = FragmentEmptyCartBinding.inflate(inflater, container, false)
            bindingEmptyCart.root
        } else {
            _bindingCart = FragmentCartBinding.inflate(inflater, container, false)
            bindingCart.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.loadCartItemsFromPrefs()

        if (_bindingCart != null) {

            cartAdapter = CartAdapter(cartViewModel) { clickedDish ->
                productDetailsViewModel.setSelectedDish(clickedDish)
                findNavController().navigate(actionCartToProductDetails)
            }

            bindingCart.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            bindingCart.recyclerView.adapter = cartAdapter

            cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
                Log.d("CartFragment", "Observed cart items: $cartItems")
                cartAdapter.setCartItems(cartItems)
                val totalSum = cartItems.sumOf { it.price * it.quantity }
                bindingCart.button4.text = "Zamów: ${String.format("%.2f zł", totalSum)}"

                val dishImageUrls = cartItems.associateBy({ it.id }, { "https://restaurantiowe.blob.core.windows.net/dish/${it.id}.jpg" })
                cartAdapter.setDishImageUrls(dishImageUrls)

                val isCartNotEmpty = cartItems.isNotEmpty()
                bindingCart.button4.isEnabled = isCartNotEmpty
                bindingCart.button4.backgroundTintList = if (isCartNotEmpty) {
                    ColorStateList.valueOf(resources.getColor(R.color.secondary_color, requireContext().theme))
                } else {
                    ColorStateList.valueOf(resources.getColor(R.color.secondary_color_light, requireContext().theme))
                }
            }

            bindingCart.imageView38.setOnClickListener {
                cartViewModel.clearCart()
                cartViewModel.saveCartItemsToPrefs()
                findNavController().navigate(actionCartToPop)
            }

            bindingCart.imageView4.setOnClickListener {
                findNavController().navigate(actionCartToPop)
            }

            bindingCart.button4.setOnClickListener {
                findNavController().navigate(actionCartToDeliveryFragment)
            }

        } else {
            bindingEmptyCart.imageView4.setOnClickListener {
                findNavController().navigate(actionCartToPop)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingCart = null
        _bindingEmptyCart = null
    }
}