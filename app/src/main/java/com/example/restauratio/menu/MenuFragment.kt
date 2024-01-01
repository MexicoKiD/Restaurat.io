package com.example.restauratio.menu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restauratio.R
import com.example.restauratio.cart.CartAdapter
import com.example.restauratio.cart.CartFragment
import com.example.restauratio.cart.CartViewModel
import com.example.restauratio.databinding.FragmentMenuBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.products.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val menuViewModel: MenuViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by activityViewModels()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var dishAdapter: DishAdapter
    private lateinit var cartAdapter: CartAdapter

    private val actionMenuToAboutUs = R.id.action_menu_to_aboutUs
    private val actionMenuToRules = R.id.action_menu_to_rulesView
    private val actionMenuToPrivacyPolicy = R.id.action_menu_to_privacyPolicy
    private val actionLogout = R.id.action_menu_pop
    private val actionMenuToAlerts = R.id.action_menu_to_alerts
    private val actionMenuToProfile = R.id.action_menu_to_profile
    private val actionMenuToOrders = R.id.action_menu_to_orders
    private val actionMenuToCart = R.id.action_menu_to_cartFragment
    private val actionMenuToProductDetails = R.id.action_global_productDetailFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        val drawerLayout = binding.drawerLayout
        val mainView: View = binding.root

        mainView.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        binding.hamburgerButton.setOnClickListener {
            onHamburgerButtonClick()
        }
        binding.imageView6.setOnClickListener{
            findNavController().navigate(actionMenuToAlerts)
        }
        binding.imageView2.setOnClickListener{
            findNavController().navigate(actionMenuToProfile)
        }
        binding.imageView7.setOnClickListener{
            findNavController().navigate(actionMenuToOrders)
        }
        binding.imageView.setOnClickListener {
            findNavController().navigate(actionMenuToCart)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about_us -> {
                    findNavController().navigate(actionMenuToAboutUs)
                }
                R.id.rules -> {
                    findNavController().navigate(actionMenuToRules)
                }
                R.id.privacy_policy -> {
                    findNavController().navigate(actionMenuToPrivacyPolicy)
                }
                R.id.logout -> {
                    sessionManager.logout()
                    findNavController().navigate(actionLogout)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.loadCartItemsFromPrefs()

        dishAdapter = DishAdapter(cartViewModel) { clickedDish ->
            productDetailsViewModel.setSelectedDish(clickedDish)
            findNavController().navigate(actionMenuToProductDetails)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = dishAdapter

        menuViewModel.dishes.observe(viewLifecycleOwner) { dishes ->
            dishAdapter.setDishes(dishes)
        }
        menuViewModel.loadDishes(categoryId = null, name = null)

        cartAdapter = CartAdapter(cartViewModel) { clickedDish ->
            productDetailsViewModel.setSelectedDish(clickedDish)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            Log.d("CartFragment", "Observed cart items: $cartItems")
            cartAdapter.setCartItems(cartItems)
            val cartItemCount = cartItems.sumOf { it.quantity }
            binding.textView57.text = cartItemCount.toString()
            binding.frameLayout6.isVisible = binding.textView57.text.toString() != "0"
        }

        binding.textInputLayout3.editText?.addTextChangedListener { text ->
            val searchQuery = text.toString().trim()
            menuViewModel.loadDishes(categoryId = null, name = null, searchQuery = searchQuery)
        }
    }

    private fun onHamburgerButtonClick() {
        val drawerLayout = binding.drawerLayout

        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START)
        } else {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (sessionManager.isLoggedIn()) {
                requireActivity().finish()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
