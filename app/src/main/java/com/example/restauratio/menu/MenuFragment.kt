package com.example.restauratio.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restauratio.R
import com.example.restauratio.cart.CartViewModel
import com.example.restauratio.databinding.FragmentMenuBinding
import com.example.restauratio.loginSession.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val menuViewModel: MenuViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val actionMenuToAboutUs = R.id.action_menu_to_aboutUs
    private val actionMenuToReservation = R.id.action_menu_to_reservationView
    private val actionMenuToRules = R.id.action_menu_to_rulesView
    private val actionMenuToPrivacyPolicy = R.id.action_menu_to_privacyPolicy
    private val actionLogout = R.id.action_menu_pop
    private val actionMenuToAlerts = R.id.action_menu_to_alerts
    private val actionMenuToProfile = R.id.action_menu_to_profile
    private val actionMenuToOrders = R.id.action_menu_to_orders
    private val actionMenuToCart = R.id.action_menu_to_cartFragment

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
                R.id.reservation -> {
                    findNavController().navigate(actionMenuToReservation)
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

        val dishAdapter = DishAdapter(cartViewModel)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = dishAdapter

        menuViewModel.dishes.observe(viewLifecycleOwner) { dishes ->
            dishAdapter.setDishes(dishes)
        }
        menuViewModel.loadDishes(categoryId = null, name = null)

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
