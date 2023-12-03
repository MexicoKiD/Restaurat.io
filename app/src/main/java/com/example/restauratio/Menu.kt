package com.example.restauratio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.restauratio.databinding.FragmentMenuBinding

class Menu : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val actionMenuToAboutUs = R.id.action_menu_to_aboutUs
    private val actionMenuToReservation = R.id.action_menu_to_reservationView
    private val actionMenuToRules = R.id.action_menu_to_rulesView
    private val actionMenuToPrivacyPolicy = R.id.action_menu_to_privacyPolicy
    private val actionLogout = R.id.action_menu_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        val drawerLayout = binding.drawerLayout
        val mainView: View = binding.root

        fun onHamburgerButtonClick() {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            } else {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        mainView.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        binding.hamburgerButton.setOnClickListener {
            onHamburgerButtonClick()
        }


        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about_us -> {
                    findNavController().navigate(actionMenuToAboutUs)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.reservation -> {
                    findNavController().navigate(actionMenuToReservation)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.rules -> {
                    findNavController().navigate(actionMenuToRules)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.privacy_policy -> {
                    findNavController().navigate(actionMenuToPrivacyPolicy)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.logout -> {
                    findNavController().navigate(actionLogout)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        //setHasOptionsMenu(true)


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
