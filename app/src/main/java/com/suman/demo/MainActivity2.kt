package com.suman.demo

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.suman.demo.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private val permission = Manifest.permission.CAMERA

    private val requestCode = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val webview = binding.webview2

        val action: String? = intent?.action
        var data: Uri? = intent?.data

        //WebViewSetup()
        if (!isPermissionGranted()) {

            askPermissions()

        }

        webview.webChromeClient = object : WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }
        }

    }


    private fun askPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(this.permission), requestCode)
    }

    private fun isPermissionGranted(): Boolean {
        /*permission.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED)
                return false
        }*/
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }

        return true

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebViewSetup() {

        val url = "https://green.tangerangkota.go.id/"
        //val url = intent.extras!!.getString("url")
        binding.webview2.webChromeClient = WebChromeClient()

        binding.webview2.apply {
            if (url != null) {
                loadUrl(url)
            }

            Log.d("callBtn", "Url ::  $url!!!! ")
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true
            }
            settings.mediaPlaybackRequiresUserGesture = false

        }
    }

}