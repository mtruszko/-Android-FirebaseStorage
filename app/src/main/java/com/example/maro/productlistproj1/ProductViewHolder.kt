package com.example.maro.productlistproj1

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.viewholder_product.view.*

class ProductViewHolder(itemView: View,
                        private val onClickListener: View.OnClickListener,
                        private val btnDeleteAction: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {

    fun bind(product: Product) {
        itemView.setTag(product)
        itemView.setOnClickListener(onClickListener)
        itemView.name.text = product.description
        itemView.amount.text = product.amount.toString()
        itemView.price.text = product.price.toString()
        if (!product.completed) {
            itemView.setBackgroundColor(Color.GRAY)
        } else {
            itemView.setBackgroundColor(Color.WHITE)
        }

        itemView.btnDel.setOnClickListener {
            btnDeleteAction(product)
        }
    }
}