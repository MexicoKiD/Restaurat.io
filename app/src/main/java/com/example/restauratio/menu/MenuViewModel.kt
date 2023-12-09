package com.example.restauratio.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _dishes = MutableLiveData<List<DishModel>>()
    val dishes: LiveData<List<DishModel>> get() = _dishes

    fun loadDishes(
        categoryId: Int?,
        name: String?,
        searchQuery: String? = null
    ) {
        viewModelScope.launch {
            try {
                val dishesResponse = authService.getDishes(DishRequest(categoryId, name))
                if (dishesResponse.isSuccessful) {
                    val allDishes = dishesResponse.body()?.dishes ?: emptyList()

                    val filteredDishes = if (!searchQuery.isNullOrBlank()) {
                        allDishes.filter { dish -> dish.name.contains(searchQuery, ignoreCase = true) }
                    } else {
                        allDishes
                    }

                    _dishes.value = filteredDishes
                } else {
                    Log.e("MenuViewModel", "Failed to fetch dishes. Error code: ${dishesResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e("MenuViewModel", "An error occurred while fetching dishes", e)
            }
        }
    }
}