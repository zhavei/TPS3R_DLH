package com.suman.demo

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.suman.demo.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private val PERMISSION_REQUEST_CODE = 1
    private val permissionsMap = mapOf(
        "writeExternalStorage" to Manifest.permission.WRITE_EXTERNAL_STORAGE,
        "camera" to Manifest.permission.CAMERA,
        "accessFineLocation" to Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        sharedPreferences = getSharedPreferences("permissions", MODE_PRIVATE)

        if (allPermissionsGranted()) {
            navigateToMainActivity()
        } else {
            binding.nextButton.visibility = View.GONE
        }

        binding.writeExternalStorageSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkAndRequestPermission(permissionsMap["writeExternalStorage"]!!)
            }
        }

        binding.cameraSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkAndRequestPermission(permissionsMap["camera"]!!)
            }
        }

        binding.accessFineLocationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkAndRequestPermission(permissionsMap["accessFineLocation"]!!)
            }
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun checkAndRequestPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                sharedPreferences.edit().putBoolean("permissions_granted", true).apply()
                if (allPermissionsGranted()) {
                    binding.nextButton.visibility = View.VISIBLE
                }
            } else {
                binding.nextButton.visibility = View.GONE
            }
        }

        return super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun allPermissionsGranted(): Boolean {
        return permissionsMap.values.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}