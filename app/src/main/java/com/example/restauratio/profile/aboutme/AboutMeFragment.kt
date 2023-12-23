package com.example.restauratio.profile.aboutme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

    private val userViewModel: UserViewModel by viewModels()

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

        val loggedInUserId = sessionManager.getLoggedInUserId()

        if (loggedInUserId != -1) {
            userViewModel.fetchUserData(loggedInUserId)

            userViewModel.userData.observe(viewLifecycleOwner) { userData ->
                setUserData(userData)
            }
        }
    }

    private fun setUserData(userData: UserDataModel) {
        binding.textView33.text = userData.firstName
        binding.textView39.text = userData.lastName
        binding.textView60.text = userData.email
        binding.textView61.text = userData.phone
        binding.textView40.text = userData.address
        binding.textView62.text = userData.city
        binding.textView41.text = userData.postalCode
        binding.textView63.text = userData.country
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}