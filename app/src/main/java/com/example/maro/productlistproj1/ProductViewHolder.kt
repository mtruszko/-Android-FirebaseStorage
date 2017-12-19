package com.example.maro.productlistproj1

import android.graphics.Color
import android.view.View

class ProductViewHolder(itemView: View,
                        private val onClickListener: View.OnClickListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(product: Product) {
        itemView.setTag(product)
        itemView.setOnClickListener(onClickListener)
        itemView.name.text = product.description
        itemView.amount.text = product.amount.toString()
        itemView.price.text = product.price.toString()
        if (!product.completed) {
            itemView.setBackgroundColor(Color.GRAY)
        }
    }
}