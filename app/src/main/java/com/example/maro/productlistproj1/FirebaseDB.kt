package com.example.maro.productlistproj1

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener



object FirebaseDB {

    var db = FirebaseFirestore.getInstance()

    fun saveToFirebase(product: Product) {
        db.collection("products")
                .document(product.id)
                .set(product)
    }


    fun readListOfProducts(completion: (List<Product>) -> Unit) {

        db.collection("products")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val arrayOfProducts: MutableList<Product> = mutableListOf()
                        for (document in task.result) {
                            Log.d(TAG, document.id + " => " + document.data)
                            val product = document.toObject(Product::class.java)
                            arrayOfProducts.add(product)
                        }
                        completion(arrayOfProducts)

                    } else {
                        Log.d(TAG, "get failed with ", task.exception)
                    }
                }
    }

    fun deleteProduct(product: Product) {
        db.collection("products")
                .document(product.id)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }
}