package com.example.act2_miraveteperezdelcorraljuan

import android.text.Html
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.act2_miraveteperezdelcorraljuan.databinding.ItemproductBinding
import com.example.act2_miraveteperezdelcorraljuan.model.Products
import com.squareup.picasso.Picasso

class ProductsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemproductBinding.bind(view)

    fun bind(products: Products){
        Picasso.get().load(products.image).into(binding.ivProduct)
        binding.tvTitle.setText(products.title);
        binding.tvDescription.setText(products.description)
        binding.tvPrice.setText(Html.fromHtml("<b>" + products.price + "<b> EUR</b>", Html.FROM_HTML_MODE_LEGACY));
    }




}