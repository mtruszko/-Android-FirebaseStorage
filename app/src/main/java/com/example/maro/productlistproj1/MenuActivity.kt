package com.example.maro.productlistproj1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    val spm = SharedPreferneceManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btnList.setOnClickListener { openListActivity() }
    }

    override fun onStart() {
        super.onStart()
        setup()
    }

    fun setup() {
        btnList.setTextSize(TypedValue.COMPLEX_UNIT_SP, spm.getCurrentFontSize())
        btnOptions.setTextSize(TypedValue.COMPLEX_UNIT_SP, spm.getCurrentFontSize())

        btnList.setTextColor(spm.getColor())
        btnOptions.setTextColor(spm.getColor())
    }

    fun openListActivity() {
        val intent = Intent(this, ProductListActivity::class.java)
        startActivity(intent)
    }

    fun optionsClick(view: View) {
        val intent = Intent(this, OptionsActivity::class.java)
        startActivity(intent)
    }
}
