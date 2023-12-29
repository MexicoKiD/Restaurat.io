package com.example.restauratio.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restauratio.databinding.OrderItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class OrdersAdapter : ListAdapter<Order, OrdersAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    inner class OrderViewHolder(private val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.textView47.text

            when (order.statusId) {
                15 -> {
                    binding.textView46.text = "Dostarczone"
                }

                14 -> {
                    binding.textView46.text = "W realizacji"
                }

                13 -> {
                    binding.textView46.text = "Odrzucone"
                }

                12 -> {
                    binding.textView46.text = "W trakcie dostawy"
                }

                11 -> {
                    binding.textView46.text = "Oczekujące"
                }
                else -> {
                    binding.textView46.visibility = View.GONE
                }
            }

            val innerRecyclerView = binding.recyclerView3
            val innerAdapter = InnerDishesAdapter(order.orderDetails)
            innerRecyclerView.adapter = innerAdapter
            innerRecyclerView.layoutManager = LinearLayoutManager(itemView.context)

            val originalTime = order.orderDate
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd, HH:mm", Locale.getDefault())
            val parsedTime = inputFormat.parse(originalTime)
            val formattedTime = parsedTime?.let { outputFormat.format(it) }

            binding.textView47.text = order.totalAmount.toString()
            binding.textView44.text = order.id.takeLast(5)
            binding.textView45.text = formattedTime
            binding.textView51.text = order.shippingInformation.firstName
            binding.textView70.text = order.shippingInformation.lastName
            binding.textView52.text = order.shippingInformation.email
            binding.textView53.text = order.shippingInformation.phone
        }
    }

    private class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}