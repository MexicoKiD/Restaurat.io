package com.example.restauratio.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentRegistrationBinding
import com.example.restauratio.loginSession.SessionManager
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        binding.text3.setOnClickListener {
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val passwordAgain = binding.passwordAgainEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val city = binding.cityEditText.text.toString()
            val postalCode = binding.postalCodeEditText.text.toString()
            val country = "Polska"
            val phone = binding.phoneEditText.text.toString()
            val firstName = binding.nameEditText.text.toString()
            val lastName = binding.surnameEditText.text.toString()

            registerViewModel.register(
                email,
                password,
                passwordAgain,
                firstName,
                lastName,
                address,
                city,
                postalCode,
                country,
                phone,
                {
                    findNavController().navigate(action)
                }
            ) { showError() }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(),"Błąd rejestracji", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}