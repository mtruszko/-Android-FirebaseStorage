package com.example.maro.productlistproj1

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(onClickListener = { view ->
            val product = view.getTag() as Product
            //todo: handle on product click here
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
//                val values = ContentValues()
//                values.put(ToDoContract.TaskEntry.KEY_DESCRIPTION, name)
//                values.put(ToDoContract.TaskEntry.KEY_AMOUNT, count)
//                values.put(ToDoContract.TaskEntry.KEY_PRICE, price)

//                contentResolver.insert(ToDoContract.TaskEntry.CONTENT_URI, values)

                val product = Product(name,"", count!!.toInt(), price!!.toInt(), false)
                FirebaseDB.saveToFirebase(product)

                reloadProducts()
            }
        })
    }

    fun reloadProducts() {

        FirebaseDB.readListOfProducts { adapter.items = it }

//        val uri = ToDoContract.TaskEntry.CONTENT_URI
//
//        val aaa = contentResolver.query(uri, null, null, null, null)
//
//        val list = generateSequence { if (aaa.moveToNext()) aaa else null }
//                .map { Product(it.getString(ToDoContract.TaskEntry.ID_COLUMN),
//                        it.getString(ToDoContract.TaskEntry.DESCRIPTION_COLUMN),
//                        it.getInt(ToDoContract.TaskEntry.AMOUNT_COLUMN),
//                        it.getInt(ToDoContract.TaskEntry.PRICE_COLUMN),
//                        false) }
//                .toList()
//
//        adapter.items = list
    }
}
