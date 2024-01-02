package com.example.restauratio.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.R
import com.example.restauratio.cart.CartViewModel
import com.example.restauratio.databinding.MenuItemBinding
import com.example.restauratio.products.ProductDetailsViewModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class DishAdapter(
    private val cartViewModel: CartViewModel,
    private val onItemClick: (DishModel) -> Unit
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    private var dishes: List<DishModel> = emptyList()
    private var dishImageUrls: Map<Int, String> = emptyMap()

    inner class DishViewHolder(binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dishImage: CircleImageView = binding.imageView2
        val dishName: TextView = binding.textView2
        val dishPrice: TextView = binding.textView
        val addToCartButton: ImageView = binding.imageView8

        init {
            dishImage.setOnClickListener {
                val clickedDish = dishes[adapterPosition]
                onItemClick.invoke(clickedDish)
            }

            addToCartButton.setOnClickListener {
                val clickedDish = dishes[adapterPosition]
                cartViewModel.addToCart(clickedDish)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val menuItemBinding = MenuItemBinding.inflate(inflater, parent, false)
        return DishViewHolder(menuItemBinding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]
        val dishId = dish.id

        if (dishImageUrls.containsKey(dishId)) {
            val imageUrl = dishImageUrls[dishId]
            Picasso.get().load(imageUrl).into(holder.dishImage)
        } else {
            holder.dishImage.setImageResource(R.drawable.pizzabg)
        }

        holder.dishName.text = dish.name
        holder.dishPrice.text = String.format("%.2f zł", dish.price)

    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    fun setDishes(newDishes: List<DishModel>) {
        dishes = newDishes
        notifyDataSetChanged()
    }

    fun setDishImageUrls(newDishImageUrls: Map<Int, String>) {
        dishImageUrls = newDishImageUrls
        notifyDataSetChanged()
    }
}