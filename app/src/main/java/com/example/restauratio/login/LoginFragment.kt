package com.example.restauratio.login

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentLoginBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.utils.ValidationUtils
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val loginViewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val actionLoginToRegistration = R.id.action_login_to_registration
    private val actionLoginToMenu = R.id.action_login_to_menu
    private val actionLoginToPasswordRemind = R.id.action_login_to_passwordRemindFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.text3.setOnClickListener {
            findNavController().navigate(actionLoginToRegistration)
        }
        binding.text5.setOnClickListener {
            findNavController().navigate(actionLoginToPasswordRemind)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(actionLoginToMenu)
        }

        val loginButton = binding.button
        val emailEditText = binding.emailEditText
        val passwordEditText = binding.passwordEditText

        buttonColorChange()

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateLoginButtonState(loginButton, emailEditText, passwordEditText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateLoginButtonState(loginButton, emailEditText, passwordEditText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        loginButton.isEnabled = false

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password)) {
                loginViewModel.login(
                    email,
                    password,
                    { handleLoginSuccess() },
                    { showError("Błąd logowania. Sprawdź wprowadzone dane i połączenie z internetem.") }
                )
            } else {
                Toast.makeText(requireContext(), "Niepoprawny email lub hasło", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleLoginSuccess() {
        sessionManager.getAuthToken().observe(viewLifecycleOwner) { authToken ->
            if (!authToken.isNullOrBlank()) {
                findNavController().navigate(actionLoginToMenu)
            } else {
                showError("Błąd logowania. Brak autoryzacji.")
            }
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun buttonColorChange() {
        binding.button.postDelayed({
            val buttonColor = if (binding.button.isEnabled) {
                R.color.secondary_color
            } else {
                R.color.secondary_color_light
            }
            binding.button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), buttonColor))
        }, 50)
    }

    private fun updateLoginButtonState(
        loginButton: Button,
        emailEditText: TextInputEditText,
        passwordEditText: TextInputEditText
    ) {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        val isNotEmpty = email.isNotBlank() && password.isNotBlank()
        val isValidEmail = ValidationUtils.isValidEmail(email)

        if (loginButton.isEnabled != (isNotEmpty && isValidEmail)) {
            loginButton.isEnabled = isNotEmpty && isValidEmail

            val buttonColor = if (loginButton.isEnabled) {
                R.color.secondary_color
            } else {
                R.color.secondary_color_light
            }
            loginButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), buttonColor))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}