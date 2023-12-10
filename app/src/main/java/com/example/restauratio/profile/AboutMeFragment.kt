package com.example.restauratio.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentAboutMeBinding
import com.example.restauratio.loginSession.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutMeFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var _binding: FragmentAboutMeBinding? = null
    private val binding get() = _binding!!
    private val actionAboutMeToPop = R.id.action_aboutMe_pop
    private val actionAboutMeToEditDeliveryAddress = R.id.action_aboutMe_to_editDeliveryAddress

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutMeBinding.inflate(inflater, container, false)

        binding.button3.setOnClickListener{
            findNavController().navigate(actionAboutMeToEditDeliveryAddress)
        }

        binding.imageView4.setOnClickListener{
            findNavController().navigate(actionAboutMeToPop)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData = sessionManager.getUserData()
        setUserData(userData)
    }

    private fun setUserData(userData: UserDataModel) {
        binding.textView33.text = userData.firstName
        binding.textView39.text =  userData.address
    }
}