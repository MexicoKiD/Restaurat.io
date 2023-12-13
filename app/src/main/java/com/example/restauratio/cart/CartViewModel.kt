package com.example.restauratio.cart

import android.util.Log
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

    init {
        _cartItems.value = emptyList() // Upewnij się, że jest inicjalizowane
    }

    fun addToCart(dish: DishModel) {
        Log.d("CartViewModel", "Adding to cart: $dish")
        val currentItems = _cartItems.value.orEmpty().toMutableList()
        val existingDish = currentItems.find { it.id == dish.id }

        if (existingDish != null) {
            existingDish.quantity += 1
        } else {
            currentItems.add(dish.copy(quantity = 1))
        }

        _cartItems.value = currentItems

        Log.d("CartViewModel", "Updated cart items: ${_cartItems.value}")
    }

    fun removeFromCart(cartItem: DishModel) {
        Log.d("CartViewModel", "Removing from cart: $cartItem")
        val currentCartItems = _cartItems.value.orEmpty().toMutableList()
        currentCartItems.remove(cartItem)
        _cartItems.value = currentCartItems
    }

    fun clearCart() {
        Log.d("CartViewModel", "Clearing cart")
        _cartItems.value = emptyList()
    }

}