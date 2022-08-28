package com.example.slim_fit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Theming : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}