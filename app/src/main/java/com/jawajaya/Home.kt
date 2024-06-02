package com.jawajaya

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Home : AppCompatActivity() {

    private lateinit var gotoTerjemah: CardView
    private lateinit var gotoKuis: CardView
    private lateinit var gotoKamus:CardView
    private lateinit var gotoCerita: CardView
    private lateinit var gotoWayang: CardView
    private lateinit var gotoMacapat: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        gotoTerjemah = findViewById(R.id.terjemahan)
        gotoKuis = findViewById(R.id.kuis)
        gotoCerita = findViewById(R.id.cerita)
        gotoWayang = findViewById(R.id.wayang)
        gotoMacapat = findViewById(R.id.macapat)
        gotoKamus = findViewById(R.id.kamus)

        gotoTerjemah.setOnClickListener{
            val intent = Intent(this, Terjemah::class.java)
            startActivity(intent)
        }

        gotoKamus.setOnClickListener{
            val intent = Intent(this, Kamus::class.java)
            startActivity(intent)
        }

        gotoKuis.setOnClickListener{
            val intent = Intent(this, Kuis::class.java)
            startActivity(intent)
        }

        gotoCerita.setOnClickListener{
            val intent = Intent(this, Cerita::class.java)
            startActivity(intent)
        }

        gotoWayang.setOnClickListener{
            val intent = Intent(this, Wayang::class.java)
            startActivity(intent)
        }

        gotoMacapat.setOnClickListener{
            val intent = Intent(this, Macapat::class.java)
            startActivity(intent)
        }
    }
}