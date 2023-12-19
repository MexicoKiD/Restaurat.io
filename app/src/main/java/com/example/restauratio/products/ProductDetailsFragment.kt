package com.example.restauratio.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.restauratio.R
import com.example.restauratio.databinding.FragmentProductDetailBinding

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val productDetailsViewModel: ProductDetailsViewModel by activityViewModels()

    private val action = R.id.action_productDetailFragment_pop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDetailsViewModel.selectedDish.observe(viewLifecycleOwner) { dish ->
            if (dish != null) {
                binding.textView59.text = dish.name
                binding.textView62.text = String.format("%.2f zł", dish.price)
                binding.textView63.text = dish.description
            }

            binding.imageView42.setOnClickListener {
                findNavController().navigate(action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
