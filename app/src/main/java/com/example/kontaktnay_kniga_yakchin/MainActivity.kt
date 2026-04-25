package com.example.kontaktnay_kniga_yakchin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCall: Button = findViewById(R.id.buttonCALL)
        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+74951234567"))
            safeStart(intent)
        }

//        intent.setDataAndType(Uri.parse("mailto:"), "text/plain")

        val btnEmail: Button = findViewById(R.id.buttonEMAIL)
        btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                  data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("contact@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Обращение")
            }
            safeStart(intent)
        }

        val btnMap: Button = findViewById(R.id.buttonMAP)
        btnMap.setOnClickListener {
            val geoUri = Uri.parse("geo:60.0237,30.2289?q=60.0237,30.2289(Наш офис)")
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            safeStart(intent)
        }

        val btnShare: Button = findViewById(R.id.buttonKONTAKT)
        btnShare.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Контакт: +7 (495) 123-45-67, contact@example.com")
            }
            val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
            startActivity(chooser)
        }
    }

    private fun safeStart(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Нет подходящего приложения", Toast.LENGTH_SHORT).show()
        }
    }
}
