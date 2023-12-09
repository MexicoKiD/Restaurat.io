package com.example.restauratio


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.restauratio.databinding.DialogPopupBinding
import com.example.restauratio.login.LoginFragment
import com.example.restauratio.loginSession.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogPopup : DialogFragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var _binding: DialogPopupBinding? = null
    private val binding get() = _binding!!
    private var message: String = ""
    private var confirmationMessage: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DialogPopupBinding.inflate(inflater, container, false)

        binding.message.text = message

        binding.btnYes.setOnClickListener{
            if (message == "Czy na pewno chcesz usunąć konto?"){
                //TODO dodać usuwanie konta
                showToast(confirmationMessage)
                sessionManager.logout()
                returnToLogin()
                dismiss()
            } else {
                showToast(confirmationMessage)
                sessionManager.logout()
                returnToLogin()
                dismiss()
            }
        }

        binding.btnNo.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun setConfirmationMessage(message: String) {
        this.confirmationMessage = message
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun returnToLogin(){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragmentTransaction.replace(R.id.fragmentContainerView, LoginFragment())
        fragmentTransaction.commit()
    }
}