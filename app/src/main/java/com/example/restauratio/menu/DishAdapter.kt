package com.example.restauratio.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.R
import com.example.restauratio.databinding.MenuItemBinding
import de.hdodenhof.circleimageview.CircleImageView

class DishAdapter : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    private var dishes: List<DishModel> = emptyList()

    inner class DishViewHolder(binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dishImage: CircleImageView = binding.imageView2
        val dishName: TextView = binding.textView2
        val dishPrice: TextView = binding.textView
        val addToCartButton: ImageView = binding.imageView8
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

        // Jeżeli masz linki do obrazków w danych, to możesz użyć biblioteki do ładowania obrazków, np. Glide.
        // Glide.with(holder.itemView.context).load(dish.imageUrl).into(holder.dishImage)


        holder.addToCartButton.setOnClickListener {
            // Tu dodaj kod obsługujący dodanie dania do koszyka
            // Na przykład możesz wywołać funkcję w ViewModelu, która dodaje danie do koszyka.
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    fun setDishes(newDishes: List<DishModel>) {
        dishes = newDishes
        notifyDataSetChanged()
    }
}