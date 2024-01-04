package com.example.restauratio.profile.aboutme.editData

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentEditAddressBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.profile.aboutme.UserDataModel
import com.example.restauratio.profile.aboutme.UserViewModel
import com.example.restauratio.utils.ValidationUtils
import com.example.restauratio.utils.ValidationUtils.Companion.validateUserData
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
        _binding = FragmentEditAddressBinding.inflate(inflater, container, false)

        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionEditDeliveryAddressToPop)
        }

        val textFields = listOf(
            binding.addressEditText,
            binding.cityEditText,
            binding.postalCodeEditText,
            binding.phoneEditText,
            binding.nameEditText,
            binding.surnameEditText
        )

        for (textField in textFields) {
            textField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    validateFormFields()
                }
            })
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
            val address = binding.addressEditText.text.toString().trim()
            val city = binding.cityEditText.text.toString().trim()
            val postalCode = binding.postalCodeEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val firstName = binding.nameEditText.text.toString().trim()
            val lastName = binding.surnameEditText.text.toString().trim()

            if (validateUserData(
                    address,
                    city,
                    postalCode,
                    phone,
                    firstName,
                    lastName
                )) {
                val updatedUserData = UserDataModel(
                    id = loggedInUserId,
                    firstName = binding.nameEditText.text.toString().trim(),
                    lastName = binding.surnameEditText.text.toString().trim(),
                    address = binding.addressEditText.text.toString().trim(),
                    city = binding.cityEditText.text.toString().trim(),
                    postalCode = binding.postalCodeEditText.text.toString().trim(),
                    phone = binding.phoneEditText.text.toString().trim(),
                    email = email,
                    country = "Polska"
                )
                viewModel.updateUserData(updatedUserData)
                Toast.makeText(requireContext(), "Zapisano dane", Toast.LENGTH_LONG).show()
                findNavController().navigate(actionEditDeliveryAddressToPop)
            } else {
                showValidationError()
            }
        }
        validateFormFields()
    }

    private fun showValidationError() {
        Toast.makeText(requireContext(), "Proszę wypełnić poprawnie wszystkie pola", Toast.LENGTH_LONG).show()
        binding.textView72.visibility = if (!ValidationUtils.isValidName(binding.nameEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView71.visibility = if (!ValidationUtils.isValidName(binding.surnameEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView76.visibility = if (!ValidationUtils.isValidAddress(binding.addressEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView77.visibility = if (!ValidationUtils.isValidCity(binding.cityEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView78.visibility = if (!ValidationUtils.isValidPostalCode(binding.postalCodeEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView79.visibility = if (!ValidationUtils.isValidPhone(binding.phoneEditText.text.toString().trim())) View.VISIBLE else View.GONE
    }

    private fun validateFormFields() {
        val address = binding.addressEditText.text.toString().trim()
        val city = binding.cityEditText.text.toString().trim()
        val postalCode = binding.postalCodeEditText.text.toString().trim()
        val phone = binding.phoneEditText.text.toString().trim()
        val firstName = binding.nameEditText.text.toString().trim()
        val lastName = binding.surnameEditText.text.toString().trim()

        val anyFieldEmpty = address.isEmpty() || city.isEmpty() || postalCode.isEmpty() ||
                phone.isEmpty() || firstName.isEmpty() || lastName.isEmpty()

        binding.buttonSave.isEnabled = !anyFieldEmpty
        binding.buttonSave.setBackgroundColor(if (!anyFieldEmpty) nonEmptyButtonColor else emptyButtonColor)
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