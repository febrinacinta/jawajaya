package com.jawajaya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.jawajaya.TerjemahDetailJW

class Terjemah : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terjemah)

        // Mendapatkan referensi ke tombol dengan ID jawaindo
        val jawaIndoButton: Button = findViewById(R.id.jawaindo)

        // Menetapkan OnClickListener pada tombol
        jawaIndoButton.setOnClickListener {
            // Membuat Intent untuk berpindah ke TerjemahDetailJw
            val intent = Intent(this, TerjemahDetailJW::class.java)
            startActivity(intent)
        }
    }
}