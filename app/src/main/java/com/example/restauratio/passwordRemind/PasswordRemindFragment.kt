package com.example.restauratio.passwordRemind

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
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentPasswordRemindBinding
import com.example.restauratio.utils.ValidationUtils
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordRemindFragment : Fragment() {

    private val passwordRemindViewModel: PasswordRemindViewModel by viewModels()

    private var _binding: FragmentPasswordRemindBinding? = null
    private val binding get() = _binding!!
    private val action = R.id.action_passwordRemindFragment_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRemindBinding.inflate(inflater, container, false)

        binding.imageView4.setOnClickListener{
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resetButton = binding.button
        val emailEditText = binding.emailEditText

        buttonColorChange()

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateLoginButtonState(resetButton, emailEditText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        resetButton.isEnabled = false

        binding.button.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            passwordRemindViewModel.passwordRemind(
                email,
                { showMessage("Wysłano przypomnienie na maila.") },
                { showMessage("Sprawdź wprowadzone dane lub sprawdź połączenie z internetem.") }
            )
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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
        resetButton: Button,
        emailEditText: TextInputEditText,
    ) {
        val email = emailEditText.text.toString().trim()

        val isNotEmpty = email.isNotBlank()
        val isValidEmail = ValidationUtils.isValidEmail(email)

        if (resetButton.isEnabled != (isNotEmpty && isValidEmail)) {
            resetButton.isEnabled = isNotEmpty && isValidEmail

            val buttonColor = if (resetButton.isEnabled) {
                R.color.secondary_color
            } else {
                R.color.secondary_color_light
            }
            resetButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), buttonColor))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}