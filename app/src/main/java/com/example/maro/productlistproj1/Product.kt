package com.example.maro.productlistproj1

/**
 * Created by maro on 17.10.2017.
 */
data class Product(val id: String,
                   val description: String,
                   val amount: Int,
                   val price: Int,
                   var completed: Boolean) {

}