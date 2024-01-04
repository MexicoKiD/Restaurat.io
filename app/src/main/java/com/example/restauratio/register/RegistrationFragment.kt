package com.example.restauratio.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentRegistrationBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.utils.ValidationUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val registerViewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val action = R.id.action_registration_pop

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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        binding.text3.setOnClickListener {
            findNavController().navigate(action)
        }
        binding.imageView4.setOnClickListener {
            findNavController().navigate(action)
        }

        val textFields = listOf(
            binding.emailEditText,
            binding.passwordEditText,
            binding.passwordAgainEditText,
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

        binding.button.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val passwordAgain = binding.passwordAgainEditText.text.toString().trim()
            val address = binding.addressEditText.text.toString().trim()
            val city = binding.cityEditText.text.toString().trim()
            val postalCode = binding.postalCodeEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val firstName = binding.nameEditText.text.toString().trim()
            val lastName = binding.surnameEditText.text.toString().trim()

            if (ValidationUtils.validateRegistrationForm(
                    email,
                    password,
                    passwordAgain,
                    address,
                    city,
                    postalCode,
                    phone,
                    firstName,
                    lastName
                )
            ) {
                registerViewModel.register(
                    email,
                    password,
                    passwordAgain,
                    firstName,
                    lastName,
                    address,
                    city,
                    postalCode,
                    "Polska",
                    phone,
                    {
                        findNavController().navigate(action)
                        showMessage("Konto zarejestrowane. Na maila został wysłany link aktywacyjny.")
                    }
                ) { showMessage("Błąd rejestracji. Sprawdz wprowadzone i połączenie z internetem.") }
            } else {
                showValidationError()
            }
        }
        validateFormFields()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showValidationError() {
        Toast.makeText(requireContext(), "Proszę wypełnić poprawnie wszystkie pola", Toast.LENGTH_LONG).show()
        binding.textView72.visibility = if (!ValidationUtils.isValidName(binding.nameEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView71.visibility = if (!ValidationUtils.isValidName(binding.surnameEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView73.visibility = if (!ValidationUtils.isValidEmail(binding.emailEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView74.visibility = if (!ValidationUtils.isValidPassword(binding.passwordEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView75.visibility = if (binding.passwordEditText.text.toString().trim() != binding.passwordAgainEditText.text.toString().trim()) View.VISIBLE else View.GONE
        binding.textView76.visibility = if (!ValidationUtils.isValidAddress(binding.addressEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView77.visibility = if (!ValidationUtils.isValidCity(binding.cityEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView78.visibility = if (!ValidationUtils.isValidPostalCode(binding.postalCodeEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView79.visibility = if (!ValidationUtils.isValidPhone(binding.phoneEditText.text.toString().trim())) View.VISIBLE else View.GONE
    }

    private fun validateFormFields() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val passwordAgain = binding.passwordAgainEditText.text.toString().trim()
        val address = binding.addressEditText.text.toString().trim()
        val city = binding.cityEditText.text.toString().trim()
        val postalCode = binding.postalCodeEditText.text.toString().trim()
        val phone = binding.phoneEditText.text.toString().trim()
        val firstName = binding.nameEditText.text.toString().trim()
        val lastName = binding.surnameEditText.text.toString().trim()

        val anyFieldEmpty = email.isEmpty() || password.isEmpty() || passwordAgain.isEmpty() ||
                address.isEmpty() || city.isEmpty() || postalCode.isEmpty() || phone.isEmpty() ||
                firstName.isEmpty() || lastName.isEmpty()

        binding.button.isEnabled = !anyFieldEmpty
        binding.button.setBackgroundColor(if (!anyFieldEmpty) nonEmptyButtonColor else emptyButtonColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}