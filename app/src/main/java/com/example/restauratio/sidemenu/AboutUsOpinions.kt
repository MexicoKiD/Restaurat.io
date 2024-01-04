package com.example.restauratio.sidemenu

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentAboutUsOpinionsBinding

class AboutUsOpinions : Fragment() {

    private var _binding: FragmentAboutUsOpinionsBinding? = null
    private val binding get() = _binding!!
    private val actionAboutUsOpinionsToAboutUs = R.id.action_aboutUsOpinions_to_aboutUs
    private val actionAboutUsOpinionsToAboutUsLocalization =
        R.id.action_aboutUsOpinions_to_aboutUsLocalization

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutUsOpinionsBinding.inflate(inflater, container, false)

        binding.imageView14.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:123456789")
            startActivity(intent)
        }

        binding.imageView15.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=52.4056354,16.9284616")
            )
            startActivity(intent)
        }

        binding.textView10.setOnClickListener {
            findNavController().navigate(actionAboutUsOpinionsToAboutUs)
        }
        binding.imageView12.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.textView11.setOnClickListener {
            findNavController().navigate(actionAboutUsOpinionsToAboutUsLocalization)
        }

        webViewSetup()

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetup() {

        val webView = binding.webView
        webView.webViewClient = WebViewClient()

        webView.apply {
            loadUrl("https://www.google.com/search?client=firefox-b-d&q=supreme+d%C3%B6ner+kebab+pozna%C5%84+opinie")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }
}