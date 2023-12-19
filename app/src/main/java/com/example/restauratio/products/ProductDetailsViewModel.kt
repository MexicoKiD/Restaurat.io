package com.example.restauratio.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restauratio.menu.DishModel

class ProductDetailsViewModel : ViewModel() {
    private val _selectedDish = MutableLiveData<DishModel>()
    val selectedDish: LiveData<DishModel> get() = _selectedDish

    fun setSelectedDish(dish: DishModel) {
        _selectedDish.value = dish
    }
}