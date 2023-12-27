package com.example.restauratio.delivery.summary

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.databinding.SummaryItemBinding
import com.example.restauratio.menu.DishModel
import de.hdodenhof.circleimageview.CircleImageView

class SummaryAdapter(private val cartItemsLiveData: LiveData<List<DishModel>>) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    private var summaryItems: List<DishModel> = emptyList()

    inner class SummaryViewHolder(binding: SummaryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dishImage: CircleImageView = binding.imageView2
        val dishName: TextView = binding.textView2
        val dishPrice: TextView = binding.textView
        val quantityTextView: TextView = binding.textView55
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val summaryItemBinding = SummaryItemBinding.inflate(inflater, parent, false)
        return SummaryViewHolder(summaryItemBinding)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val summaryItem = summaryItems[position]

        holder.dishName.text = summaryItem.name
        holder.dishPrice.text = String.format("%.2f zł", summaryItem.price)
        holder.quantityTextView.text = summaryItem.quantity.toString()
    }

    override fun getItemCount(): Int {
        return summaryItems.size
    }

    fun setCartItems(newCartItems: List<DishModel>) {
        Log.d("CartAdapter", "Setting cart items: $summaryItems")
        summaryItems = newCartItems
        notifyDataSetChanged()
    }
}