package com.tillertest.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.tiller.test.databinding.ActivitySplashBinding
import com.tillertest.BaseApp
import com.tillertest.utils.startAppActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApp).netComponent.inject(this)
        
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToHome()
    }

    private fun navigateToHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            startAppActivity(MainActivity::class.java)
        }, DELAY_TIME.toLong())
    }

    companion object {
        const val DELAY_TIME = 3000
    }
}