package com.jawajaya

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jawajaya.Database.CeritaData
import com.jawajaya.R

class DetailCerita : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_cerita)

        val judulTextView: TextView = findViewById(R.id.text_judul)
        val ceritaTextView: TextView = findViewById(R.id.text_cerita)

        // Mengambil data dari intent
        val ceritaData: CeritaData? = intent.getParcelableExtra("cerita_data")

        // Menampilkan judul dan cerita
        ceritaData?.let {
            judulTextView.text = it.judul
            ceritaTextView.text = it.text_cerita
        }
    }
}
