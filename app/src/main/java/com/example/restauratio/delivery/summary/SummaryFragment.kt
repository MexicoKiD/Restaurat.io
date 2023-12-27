package com.example.restauratio.delivery.summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restauratio.R
import com.example.restauratio.cart.CartViewModel
import com.example.restauratio.databinding.FragmentSummaryBinding
import com.example.restauratio.delivery.DeliveryViewModel
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.profile.aboutme.UserDataModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val cartViewModel: CartViewModel by activityViewModels()
    private val deliveryViewModel: DeliveryViewModel by activityViewModels()

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

        cartViewModel.loadCartItemsFromPrefs()

        summaryAdapter = SummaryAdapter(cartViewModel.cartItems)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = summaryAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            Log.d("CartFragment", "Observed cart items: $cartItems")
            summaryAdapter.setCartItems(cartItems)
            val totalSum = cartItems.sumOf { it.price * it.quantity }
            binding.buttonSave.text = "Zamawiam i płacę: ${String.format("%.2f zł", totalSum)}"
        }

        val loggedInUserId = sessionManager.getLoggedInUserId()
        val email = sessionManager.getUserEmail()

        if (loggedInUserId != -1) {
            deliveryViewModel.fetchUserData(loggedInUserId)

            deliveryViewModel.userData.observe(viewLifecycleOwner) { userData ->
                updateUI(userData)
            }
        }

        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionSummaryPop)
        }

        binding.buttonSave.setOnClickListener {
            findNavController().popBackStack(R.id.menu, true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateUI(user: UserDataModel) {
        binding.textView33.text = user.firstName
        binding.textView39.text = user.lastName
        binding.textView60.text = user.email
        binding.textView40.text = user.address
        binding.textView63.text = user.city
        binding.textView62.text = user.postalCode
        binding.textView61.text = user.phone
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}