package com.example.restauratio.profile.aboutme.editData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentEditAddressBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.profile.aboutme.UserDataModel
import com.example.restauratio.profile.aboutme.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditDeliveryAddress : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var _binding: FragmentEditAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by activityViewModels()
    private val actionEditDeliveryAddressToPop = R.id.action_editDeliveryAddress_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAddressBinding.inflate(inflater, container, false)

        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionEditDeliveryAddressToPop)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loggedInUserId = sessionManager.getLoggedInUserId()
        val email = sessionManager.getUserEmail()

        viewModel.fetchUserData(loggedInUserId)

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            updateUI(userData)
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

            viewModel.updateUserData(updatedUserData)

            findNavController().navigate(actionEditDeliveryAddressToPop)
        }
    }

    private fun updateUI(user: UserDataModel) {
        binding.nameEditText.setText(user.firstName)
        binding.surnameEditText.setText(user.lastName)
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