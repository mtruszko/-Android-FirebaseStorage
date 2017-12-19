package com.example.maro.productlistproj1

import android.content.Context
import android.graphics.Color

class SharedPreferneceManager(private val context: Context) {
    val keyColor = "keyColor"
    val keyFont = "keyFont"

    val bigFontSize = "22"
    val smallFontSize = "15"

    val defaultColor = Color.BLACK
    val defaultSize = 15

    fun saveFont(isBig: Boolean) {
        if (isBig) {
            saveString(keyFont, bigFontSize)
        } else {
            saveString(keyFont, smallFontSize)
        }
    }

    fun getCurrentFontSize(): Float {
        val strFontSize = getString(keyFont)
        if (strFontSize != null) {
            return strFontSize.toFloat()
        }

        return defaultSize.toFloat()
    }

    fun saveColor(color: Int) =
            saveString(keyColor, color.toString())

    fun getColor(): Int {
        val strColor = getString(keyColor)
        if (strColor != null) {
            return strColor.toInt()
        }
        return defaultColor
    }

    private fun saveString(key: String, value: String) =
            context.getSharedPreferences("App", Context.MODE_PRIVATE)
                    .edit()
                    .putString(key, value)
                    .apply()

    private fun getString(key: String): String? =
            context.getSharedPreferences("App", Context.MODE_PRIVATE)
                    .getString(key, null)
}