package com.example.restauratio.cart

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restauratio.menu.DishModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    application: Application
) : ViewModel() {

    private val PREF_NAME = "cart_prefs"
    private val CART_ITEMS_KEY = "cart_items"

    private var contextRef: WeakReference<Context> = WeakReference(application.applicationContext)

    private val context: Context?
        get() = contextRef.get()


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

        Log.d("CartViewModel", "Updated cart items: ${_cartItems.value}")

        //saveCartItemsToPrefs()
    }

    fun removeFromCart(cartItem: DishModel) {
        Log.d("CartViewModel", "Removing from cart: $cartItem")
        val currentCartItems = _cartItems.value.orEmpty().toMutableList()

        val existingDishIndex = currentCartItems.indexOfFirst { it.id == cartItem.id }

        if (existingDishIndex != -1) {
            val existingDish = currentCartItems[existingDishIndex]
            if (existingDish.quantity > 1) {
                existingDish.quantity -= 1
            } else {
                currentCartItems.removeAt(existingDishIndex)
            }
            _cartItems.value = currentCartItems
            //saveCartItemsToPrefs()
        }
    }

    fun clearCart() {
        Log.d("CartViewModel", "Clearing cart")
        _cartItems.value = emptyList()
        //saveCartItemsToPrefs()
    }

//    fun loadCartItemsFromPrefs() {
//        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val json = sharedPreferences.getString(CART_ITEMS_KEY, null)
//        val type = object : TypeToken<List<DishModel>>() {}.type
//        val cartItems: List<DishModel> = Gson().fromJson(json, type) ?: emptyList()
//        _cartItems.value = cartItems
//    }
//
//    private fun saveCartItemsToPrefs() {
//        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        val json = Gson().toJson(_cartItems.value)
//        editor.putString(CART_ITEMS_KEY, json)
//        editor.apply()
//    }

}