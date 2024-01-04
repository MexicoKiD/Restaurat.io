package com.example.restauratio.delivery

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentDeliveryBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.profile.aboutme.UserDataModel
import com.example.restauratio.utils.ValidationUtils
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

    private val nonEmptyButtonColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.secondary_color)
    }
    private val emptyButtonColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.secondary_color_light)
    }

    private var selectedDeliveryMethodId: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)

        binding.buttonSave.setOnClickListener {
            handleNextButtonClick()
        }
        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionDeliveryPop)
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

        binding.radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioButton3) {
                hideDeliveryFields()
            } else {
                showDeliveryFields()
            }
            validateFormFields()
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

        validateFormFields()
    }

    private fun handleNextButtonClick() {
        selectedDeliveryMethodId = if (binding.radioButton3.isChecked) {
            3 // Odbiór osobisty
        } else {
            2 // Dostawa
        }

        if (selectedDeliveryMethodId == 3) {
            proceedToNextStep()
        } else {
            val address = binding.addressEditText.text.toString().trim()
            val city = binding.cityEditText.text.toString().trim()
            val postalCode = binding.postalCodeEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val firstName = binding.nameEditText.text.toString().trim()
            val lastName = binding.surnameEditText.text.toString().trim()

            val anyFieldEmpty = address.isEmpty() || city.isEmpty() || postalCode.isEmpty() ||
                    phone.isEmpty() || firstName.isEmpty() || lastName.isEmpty()

            if (anyFieldEmpty) {
                showValidationError(selectedDeliveryMethodId)
            } else {
                if (ValidationUtils.validateUserData(
                        address,
                        city,
                        postalCode,
                        phone,
                        firstName,
                        lastName
                    )
                ) {
                    val updatedUserData = UserDataModel(
                        id = sessionManager.getLoggedInUserId(),
                        firstName = firstName,
                        lastName = lastName,
                        address = address,
                        city = city,
                        postalCode = postalCode,
                        phone = phone,
                        email = sessionManager.getUserEmail(),
                        country = "Polska"
                    )

                    deliveryViewModel.updateUserData(updatedUserData)
                    proceedToNextStep()
                } else {
                    showValidationError(selectedDeliveryMethodId)
                }
            }
        }
    }

    private fun showValidationError(selectedDeliveryMethodId: Int) {
        Toast.makeText(requireContext(), "Proszę wypełnić poprawnie wszystkie pola", Toast.LENGTH_LONG).show()
        binding.textView72.visibility = if (!ValidationUtils.isValidName(binding.nameEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView71.visibility = if (!ValidationUtils.isValidName(binding.surnameEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView76.visibility = if (selectedDeliveryMethodId != 3 && !ValidationUtils.isValidAddress(binding.addressEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView77.visibility = if (selectedDeliveryMethodId != 3 && !ValidationUtils.isValidCity(binding.cityEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView78.visibility = if (selectedDeliveryMethodId != 3 && !ValidationUtils.isValidPostalCode(binding.postalCodeEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView79.visibility = if (!ValidationUtils.isValidPhone(binding.phoneEditText.text.toString().trim())) View.VISIBLE else View.GONE
    }

    private fun validateFormFields() {
        if (binding.radioButton2.isChecked) {
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
        } else {
            binding.buttonSave.isEnabled = true
            binding.buttonSave.setBackgroundColor(nonEmptyButtonColor)

            binding.textView76.visibility = View.GONE
            binding.textView77.visibility = View.GONE
            binding.textView78.visibility = View.GONE
            binding.textView79.visibility = View.GONE
        }
    }

    private fun proceedToNextStep() {
        val action = DeliveryFragmentDirections.actionDeliveryFragmentToPaymentFragment(
            deliveryMethod = if (binding.radioButton3.isChecked) 3 else 2,
            description = binding.description.text.toString().trim())
        findNavController().navigate(action)
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

    private fun hideDeliveryFields() {
        binding.textView65.visibility = View.GONE
        binding.textInputLayout5.visibility = View.GONE
        binding.textInputLayout6.visibility = View.GONE
        binding.textInputLayout7.visibility = View.GONE
    }

    private fun showDeliveryFields() {
        binding.textView65.visibility = View.VISIBLE
        binding.textInputLayout5.visibility = View.VISIBLE
        binding.textInputLayout6.visibility = View.VISIBLE
        binding.textInputLayout7.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
