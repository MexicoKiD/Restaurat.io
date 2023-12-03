package com.example.restauratio

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.restauratio.databinding.DialogPopupBinding
import okhttp3.internal.notify

class DialogPopup : DialogFragment() {

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
            showToast(confirmationMessage)
            dismiss()
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
}