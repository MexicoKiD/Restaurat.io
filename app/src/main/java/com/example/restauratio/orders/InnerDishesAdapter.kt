package com.example.restauratio.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.databinding.OrderItemInnerRvBinding

class InnerDishesAdapter(private val orderDetails: List<OrderDetail>) :
    RecyclerView.Adapter<InnerDishesAdapter.InnerDishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerDishViewHolder {
        val binding = OrderItemInnerRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InnerDishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerDishViewHolder, position: Int) {
        val orderDetail = orderDetails[position]
        holder.bind(orderDetail)
    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }

    inner class InnerDishViewHolder(private val binding: OrderItemInnerRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetail: OrderDetail) {
            binding.textView48.text = orderDetail.dishName
            binding.textView49.text = orderDetail.quantity.toString()
        }
    }
}