package com.example.restauratio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.restauratio.databinding.FragmentProfileBinding
import com.example.restauratio.loginSession.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val actionProfileToPop = R.id.action_profile_pop
    private val actionProfileToAboutMe = R.id.action_profile_to_aboutMe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionProfileToPop)
        }

        binding.imageView30.setOnClickListener{
            findNavController().navigate(actionProfileToAboutMe)
        }

        binding.imageView32.setOnClickListener{
            val customDialog = DialogPopup()
            customDialog.setMessage("Czy na pewno chcesz usunąć konto?")
            customDialog.setConfirmationMessage("Konto usunięte")
            customDialog.show(requireActivity().supportFragmentManager, "CustomDialogFragment")
        }

        binding.imageView33.setOnClickListener{
            val customDialog = DialogPopup()
            customDialog.setMessage("Czy na pewno chcesz się wylogować?")
            customDialog.setConfirmationMessage("Wylogowano")
            customDialog.show(requireActivity().supportFragmentManager, "CustomDialogFragment")
        }

        binding.imageView31.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://shorturl.at/ahE67")))
        }

        return binding.root
    }
}