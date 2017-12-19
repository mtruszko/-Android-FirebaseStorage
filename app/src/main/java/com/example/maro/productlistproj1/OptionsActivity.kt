package com.example.maro.productlistproj1

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {

    val spm = SharedPreferneceManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        setup()

        switch1.setOnCheckedChangeListener {
            btnView, isChecked ->
            spm.saveFont(isChecked)
            setup()
        }

        button.setOnClickListener {
            spm.saveColor((it.background as ColorDrawable).color)
            setup()
        }

        button2.setOnClickListener {
            spm.saveColor((it.background as ColorDrawable).color)
            setup()
        }
    }

    fun setup() {
        textView.setTextColor(spm.getColor())
        textView2.setTextColor(spm.getColor())

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, spm.getCurrentFontSize())
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, spm.getCurrentFontSize())
    }
}