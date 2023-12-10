package com.example.restauratio.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentChangePasswordBinding
import com.example.restauratio.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private val viewModel: ChangePasswordViewModel by viewModels()

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val actionChangePasswordPop = R.id.action_changePasswordFragment_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        binding.imageView4.setOnClickListener {
            findNavController().navigate(actionChangePasswordPop)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button4.setOnClickListener {
            val password = binding.passwordEditText.text.toString()
            val passwordAgain = binding.passwordAgainEditText.text.toString()

            lifecycleScope.launch {
                try {
                    viewModel.resetPassword(password, passwordAgain)
                    Toast.makeText(requireContext(), "Hasło zmienione", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Wystąpił błąd: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}