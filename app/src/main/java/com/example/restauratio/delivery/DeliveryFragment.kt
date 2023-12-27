package com.example.restauratio.delivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentDeliveryBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.profile.aboutme.UserDataModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val deliveryViewModel: DeliveryViewModel by activityViewModels()

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!

    private val actionDeliveryToPayment = R.id.action_deliveryFragment_to_paymentFragment
    private val actionDeliveryPop = R.id.action_deliveryFragment_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)

        binding.buttonSave.setOnClickListener {
            findNavController().navigate(actionDeliveryToPayment)
        }
        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionDeliveryPop)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loggedInUserId = sessionManager.getLoggedInUserId()
        val email = sessionManager.getUserEmail()

        if (loggedInUserId != -1) {
            deliveryViewModel.fetchUserData(loggedInUserId)

            deliveryViewModel.userData.observe(viewLifecycleOwner) { userData ->
                updateUI(userData)
            }
        }

        binding.buttonSave.setOnClickListener {
            val updatedUserData = UserDataModel(
                id = loggedInUserId,
                firstName = binding.nameEditText.text.toString(),
                lastName = binding.surnameEditText.text.toString(),
                address = binding.addressEditText.text.toString(),
                city = binding.cityEditText.text.toString(),
                postalCode = binding.postalCodeEditText.text.toString(),
                phone = binding.phoneEditText.text.toString(),
                email = email,
                country = "Polska"
            )

            deliveryViewModel.updateUserData(updatedUserData)

            findNavController().navigate(actionDeliveryToPayment)
        }

    }

    private fun updateUI(user: UserDataModel) {
        binding.nameEditText.setText(user.firstName)
        binding.surnameEditText.setText(user.lastName)
        binding.emailEditText.setText(user.email)
        binding.addressEditText.setText(user.address)
        binding.cityEditText.setText(user.city)
        binding.postalCodeEditText.setText(user.postalCode)
        binding.phoneEditText.setText(user.phone)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}