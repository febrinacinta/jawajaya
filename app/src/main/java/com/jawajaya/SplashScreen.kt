package com.jawajaya

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 5000

    private lateinit var gotoPanduanApp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Initialize gotoPanduanApp after it's found in the layout
        gotoPanduanApp = findViewById(R.id.panduanapp)

        gotoPanduanApp?.setOnClickListener { view ->
            val intent = Intent(this, PanduanApp::class.java)
            startActivity(intent)
            finish()
        }

        // Delay for splash screen
        Handler().postDelayed({
            val registerIntent = Intent(this, Register::class.java)
            startActivity(registerIntent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
