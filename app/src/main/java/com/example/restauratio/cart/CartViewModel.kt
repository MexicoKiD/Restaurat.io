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
        setCartItems(currentItems)
    }

    fun removeFromCart(cartItem: DishModel) {
        val currentCartItems = _cartItems.value.orEmpty().toMutableList()
        currentCartItems.remove(cartItem)
        _cartItems.value = currentCartItems
    }

    private fun setCartItems(cartItems: List<DishModel>) {
        _cartItems.value = cartItems
        Log.d("CartViewModel", "Current cart items: $cartItems")
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

}