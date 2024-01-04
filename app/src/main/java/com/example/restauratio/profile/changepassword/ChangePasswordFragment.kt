package com.example.restauratio.profile.changepassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentChangePasswordBinding
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.utils.ValidationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: ChangePasswordViewModel by viewModels()

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val action = R.id.action_global_login
    private val actionChangePasswordPop = R.id.action_changePasswordFragment_pop

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
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionChangePasswordPop)
        }

        val textFields = listOf(
            binding.passwordEditText,
            binding.passwordAgainEditText,
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

        binding.button4.setOnClickListener {
            val password = binding.passwordEditText.text.toString().trim()
            val passwordAgain = binding.passwordAgainEditText.text.toString().trim()

            if (ValidationUtils.validatePasswordChangeForm(password, passwordAgain)) {
                lifecycleScope.launch {
                    viewModel.resetPassword(password, passwordAgain)
                }
                    Toast.makeText(requireContext(), "Hasło zmienione", Toast.LENGTH_LONG).show()
                    sessionManager.logout()
                    findNavController().popBackStack(R.id.login, true)
                    findNavController().navigate(action)
            }else {
                showValidationError()
            }
        }
        validateFormFields()
    }

    private fun showValidationError() {
        Toast.makeText(requireContext(), "Proszę wypełnić poprawnie wszystkie pola", Toast.LENGTH_LONG).show()
        binding.textView74.visibility = if (!ValidationUtils.isValidPassword(binding.passwordEditText.text.toString().trim())) View.VISIBLE else View.GONE
        binding.textView75.visibility = if (binding.passwordEditText.text.toString().trim() != binding.passwordAgainEditText.text.toString().trim()) View.VISIBLE else View.GONE
    }

    private fun validateFormFields() {
        val password = binding.passwordEditText.text.toString().trim()
        val passwordAgain = binding.passwordAgainEditText.text.toString().trim()

        val anyFieldEmpty = password.isEmpty() || passwordAgain.isEmpty()

        binding.button4.isEnabled = !anyFieldEmpty
        binding.button4.setBackgroundColor(if (!anyFieldEmpty) nonEmptyButtonColor else emptyButtonColor)
    }
}