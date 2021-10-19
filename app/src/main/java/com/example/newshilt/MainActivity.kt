package com.example.newshilt

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val STORAGE_STORAGE_REQUEST_CODE: Int = 101
    lateinit var openNewsApp: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openNewsApp = findViewById(R.id.open_news_btn)

        openNewsApp.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }
        askForPermissions()
    }

    fun askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    ),
                    STORAGE_STORAGE_REQUEST_CODE
                )
            } else {
                // handle granted permission
            }
        } // no need to ask permiss
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_STORAGE_REQUEST_CODE -> {
                // When request is cancelled, the results array are empty
                if (
                    (grantResults.size > 0) &&
                    (grantResults[0]
                            + grantResults[1]
                            + grantResults[2]
                            == PackageManager.PERMISSION_GRANTED
                            )
                ) {
                    // Permissions are granted
                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}