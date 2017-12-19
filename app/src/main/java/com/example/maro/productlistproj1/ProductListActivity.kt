package com.example.maro.productlistproj1

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(onClickListener = View.OnClickListener { view ->
            val product = view.getTag() as Product
            product.completed = !product.completed
            FirebaseDB.saveToFirebase(product)
            reloadProducts()
        }, deleteAction = { product ->
            FirebaseDB.deleteProduct(product)
            reloadProducts()
        })

        recyclerView.adapter = adapter

        reloadProducts()

        btnDodaj.setOnClickListener({ view ->
            val name = txtName.text.toString()
            val count = txtCount.text.toString().toIntOrNull()
            val price = txtPrice.text.toString().toIntOrNull()

            if (name.isEmpty() or (count == null) or (price == null)) {
                val inputEmpty = getString(R.string.error_empty_field)

                Toast.makeText(applicationContext, inputEmpty, Toast.LENGTH_LONG).show()
            } else {
                val product = Product(name, name, count!!.toInt(), price!!.toInt(), false)
                FirebaseDB.saveToFirebase(product)

                reloadProducts()
            }
        })
    }

    fun reloadProducts() {
        FirebaseDB.readListOfProducts { adapter.items = it }
    }
}
