package com.example.restauratio.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.cart.CartViewModel
import com.example.restauratio.databinding.MenuItemBinding
import de.hdodenhof.circleimageview.CircleImageView

class DishAdapter(
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    private var dishes: List<DishModel> = emptyList()

    inner class DishViewHolder(binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dishImage: CircleImageView = binding.imageView2
        val dishName: TextView = binding.textView2
        val dishPrice: TextView = binding.textView
        val addToCartButton: ImageView = binding.imageView8

        init {
            addToCartButton.setOnClickListener {
                val clickedDish = dishes[adapterPosition]
                cartViewModel.addToCart(clickedDish)
                Toast.makeText(itemView.context, "Danie dodane do koszyka", Toast.LENGTH_SHORT).show()
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
}