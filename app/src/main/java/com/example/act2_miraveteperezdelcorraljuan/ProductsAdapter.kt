package com.example.act2_miraveteperezdelcorraljuan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.act2_miraveteperezdelcorraljuan.model.Products

class ProductsAdapter(private var products: List<Products>) : RecyclerView.Adapter<ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(layoutInflater.inflate(R.layout.itemproduct, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item: Products = products[position]
        holder.bind(item)
    }

    fun updateProducts(newProducts: List<Products>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
