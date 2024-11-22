package com.example.kotlin.examenmovilesmendez.frameworks.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.examenmovilesmendez.databinding.ItemBinding

class ViewHolder (
    private val binding: ItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(evento: Map<String, Any>) {
        binding.textDate.text = evento["date"]?.toString() ?: "N/A"
        binding.textDescription.text = evento["description"]?.toString() ?: "N/A"
        binding.textCategory2.text = evento["category2"]?.toString() ?: "N/A"


    }
}
