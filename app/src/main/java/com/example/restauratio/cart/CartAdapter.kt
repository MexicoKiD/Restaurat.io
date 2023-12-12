package com.example.restauratio.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.databinding.CartItemBinding
import com.example.restauratio.menu.DishModel
import de.hdodenhof.circleimageview.CircleImageView

class CartAdapter(
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems: List<DishModel> = emptyList()

    inner class CartViewHolder(binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dishImage: CircleImageView = binding.imageView2
        val dishName: TextView = binding.textView2
        val dishPrice: TextView = binding.textView
        val removeFromCartButton: ImageView = binding.imageView8

        init {
            removeFromCartButton.setOnClickListener {
                val removedDish = cartItems[adapterPosition]
                cartViewModel.removeFromCart(removedDish)
                Toast.makeText(itemView.context, "Danie usunięte z koszyka", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cartItemBinding = CartItemBinding.inflate(inflater, parent, false)
        return CartViewHolder(cartItemBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.dishName.text = cartItem.name
        holder.dishPrice.text = String.format("%.2f zł", cartItem.price)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun setCartItems(newCartItems: List<DishModel>) {
        Log.d("CartAdapter", "Setting cart items: $newCartItems")
        cartItems = newCartItems
        notifyDataSetChanged()
    }

}