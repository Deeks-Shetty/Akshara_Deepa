package com.example.aksharadeepa

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aksharadeepa.databinding.ActivitySplashBinding
import com.example.aksharadeepa.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            // Simulate loading and ensure DB is initialized
            delay(2000) 
            
            // Check if DB is initialized (handled inside ViewModel/Repository init)
            // Navigate to Home
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
