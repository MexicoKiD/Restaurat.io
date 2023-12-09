package com.example.restauratio.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentLoginBinding
import com.example.restauratio.loginSession.SessionManager
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.text3.setOnClickListener {
            findNavController().navigate(actionLoginToRegistration)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(actionLoginToMenu)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            loginViewModel.login(
                email,
                password,
                { handleLoginSuccess() },
                { showError() }
            )
        }
    }

    private fun handleLoginSuccess() {
        sessionManager.getAuthToken().observe(viewLifecycleOwner) { authToken ->
            if (!authToken.isNullOrBlank()) {
                findNavController().navigate(actionLoginToMenu)
            } else {
                showError()
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(),"Błąd logowania", Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}