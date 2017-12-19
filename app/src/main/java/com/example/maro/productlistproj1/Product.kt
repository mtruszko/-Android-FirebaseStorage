package com.example.maro.productlistproj1

/**
 * Created by maro on 17.10.2017.
 */
data class Product(val id: String = "",
                   val description: String = "",
                   val amount: Int = 0,
                   val price: Int = 0,
                   var completed: Boolean = false) {

}