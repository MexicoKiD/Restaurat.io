package com.example.restauratio.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.R
import com.example.restauratio.databinding.CartItemBinding
import com.example.restauratio.menu.DishModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CartAdapter(
    private val cartViewModel: CartViewModel,
    private val onItemClick: (DishModel) -> Unit,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems: List<DishModel> = emptyList()
    private var dishImageUrls: Map<Int, String> = emptyMap()

    inner class CartViewHolder(binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dishImage: CircleImageView = binding.imageView2
        val dishName: TextView = binding.textView2
        val dishPrice: TextView = binding.textView
        val quantityTextView: TextView = binding.textView55
        val removeFromCartButton: ImageView = binding.imageView8
        val addButton: ImageView = binding.imageView39

        init {
            dishImage.setOnClickListener {
                val clickedDish = cartItems[adapterPosition]
                onItemClick.invoke(clickedDish)
            }
            removeFromCartButton.setOnClickListener {
                val removedDish = cartItems[adapterPosition]
                cartViewModel.removeFromCart(removedDish)
            }
            addButton.setOnClickListener {
                val addDish = cartItems[adapterPosition]
                cartViewModel.addToCart(addDish)
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
        val dishId = cartItem.id

        if (dishImageUrls.containsKey(dishId)) {
            val imageUrl = dishImageUrls[dishId]
            Picasso.get().load(imageUrl).into(holder.dishImage)
        } else {
            holder.dishImage.setImageResource(R.drawable.pizzabg)
        }

        holder.dishName.text = cartItem.name
        holder.dishPrice.text = String.format("%.2f zł", cartItem.price)
        holder.quantityTextView.text = cartItem.quantity.toString()
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun setCartItems(newCartItems: List<DishModel>) {
        Log.d("CartAdapter", "Setting cart items: $cartItems")
        cartItems = newCartItems
        notifyDataSetChanged()
    }

    fun setDishImageUrls(newDishImageUrls: Map<Int, String>) {
        dishImageUrls = newDishImageUrls
        notifyDataSetChanged()
    }

}