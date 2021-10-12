package com.example.newshilt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var openNewsApp: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openNewsApp = findViewById(R.id.open_news_btn)

        openNewsApp.setOnClickListener {
            val intent = Intent(this,NewsActivity::class.java)
            startActivity(intent)
        }
    }
}