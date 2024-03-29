package com.example.restauratio.delivery.summary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val nonEmptyButtonColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.secondary_color)
    }
    private val emptyButtonColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.secondary_color_light)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)

        val args: SummaryFragmentArgs by navArgs()
        val deliveryMethodId = args.deliveryMethod
        val paymentMethodId = args.paymentMethod
        val description = args.description

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

        if (deliveryMethodId == 3) {
            binding.textView69.text = "Odbiór osobisty"
            binding.textView40.visibility = View.GONE
            binding.textView62.visibility = View.GONE
            binding.textView63.visibility = View.GONE
        } else binding.textView69.visibility = View.GONE

        when (paymentMethodId) {
            4 -> {
                binding.textView42.text = "Gotówka"
            }
            5 -> {
                binding.textView42.text = "Karta przy odbiorze"
            }
            6 -> {
                binding.textView42.text = "BLIK"
            }
            else -> {
                binding.textView42.text = "Nie wybrano metody płatności."
            }
        }

        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionSummaryPop)
        }

        binding.buttonSave.setOnClickListener {

            val shippingInformation = ShippingInformation(
                loggedInUserId,
                email,
                binding.textView33.text.toString(),
                binding.textView39.text.toString(),
                binding.textView40.text.toString(),
                binding.textView63.text.toString(),
                binding.textView62.text.toString(),
                "Polska",
                binding.textView61.text.toString()
            )

            val orderDetails = cartViewModel.cartItems.value?.map {
                OrderDetail(it.id, it.quantity)
            } ?: emptyList()

            val deliveryType = if (binding.textView69.text == "Odbiór osobisty") 3 else 2

            val paymentType = when (binding.textView42.text) {
                "Gotówka" -> 4
                "Karta przy odbiorze" -> 5
                "BLIK" -> 6
                else -> 0
            }

            val createOrderRequest = CreateOrderRequest(
                shippingInformation,
                orderDetails,
                deliveryType,
                paymentType,
                description
            )

            when (paymentType) {
                6 -> {
                    deliveryViewModel.createOrder(createOrderRequest)

                    deliveryViewModel.orderId.observe(viewLifecycleOwner) { orderId ->
                        orderId?.let {
                            deliveryViewModel.initiatePayment(orderId)
                        }
                    }

                    deliveryViewModel.paymentResponse.observe(viewLifecycleOwner) { paymentResponse ->
                        paymentResponse?.let {
                            Toast.makeText(requireContext(), "Zamówienie utworzone, za chwilę zostaniesz przekierowany do płatności", Toast.LENGTH_LONG).show()
                            val redirectUrl = paymentResponse.redirectUrl
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl))
                            startActivity(intent)
                            cartViewModel.clearCart()
                            findNavController().popBackStack(R.id.menu, true)
                            findNavController().navigate(action)
                        }
                    }
                }
                else -> {
                    deliveryViewModel.createOrder(createOrderRequest)
                    cartViewModel.clearCart()
                    findNavController().popBackStack(R.id.menu, true)
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "Zamówienie utworzone, szczegóły wyślemy Ci na maila.", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            binding.buttonSave.isEnabled = isChecked
            binding.buttonSave.setBackgroundColor(
                if (isChecked) nonEmptyButtonColor
                else emptyButtonColor
            )
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