package com.example.maro.productlistproj1

import android.content.ContentValues
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ProductAdapter(private val onClickListener: View.OnClickListener) : RecyclerView.Adapter<ProductViewHolder>() {

    var items = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_product, parent, false), onClickListener)
//                    .listen{ pos, type ->
//                val item = items.get(pos)
//
//                var values = ContentValues()
//                        values.put(ToDoContract.TaskEntry.KEY_ID, item.id)
//                values.put(ToDoContract.TaskEntry.KEY_DESCRIPTION, item.description)
////                values.put(ToDoContract.TaskEntry.KEY_AMOUNT, count)
////                values.put(ToDoContract.TaskEntry.KEY_PRICE, price)
//                  values.put(ToDoContract.TaskEntry.KEY_COMPLETED, !item.completed)
//                        item.completed = !item.completed
//
////                parent.context.contentResolver.update(ToDoContract.TaskEntry.CONTENT_URI, values, null, null)
//                notifyDataSetChanged()
//            }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
            holder.bind(items[position])

}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(getAdapterPosition(), getItemViewType())
    }
    return this
}