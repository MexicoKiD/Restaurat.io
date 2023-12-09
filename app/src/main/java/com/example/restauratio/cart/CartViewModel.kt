package com.example.restauratio.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restauratio.menu.DishModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _cartItems = MutableLiveData<List<DishModel>>()
    val cartItems: LiveData<List<DishModel>> get() = _cartItems

    fun addToCart(dish: DishModel) {
        val currentItems = _cartItems.value.orEmpty().toMutableList()
        currentItems.add(dish)
        _cartItems.value = currentItems
    }
}