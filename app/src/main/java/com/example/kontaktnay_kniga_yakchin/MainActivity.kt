package com.example.kontaktnay_kniga_yakchin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Нет подходящего приложения", Toast.LENGTH_SHORT).show()
            }

            fun callPhoneNumber(phoneNumber: String) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

            callPhoneNumber("+74951234567")

            fun sendEmail(address: String, subject: String, body: String) {
                val intent = Intent(Intent.ACTION_SENDO).apply {
                    date = Uri.parse("mailto:")

                    putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, body)
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

            sendEmail("contact@example.com", "Обращение", "Добрый день, \n")

            fun showOnMap(latitude: Double, longitude: Double, label: String) {
                val geoUri = Uri.parse("geo:0,0?q=$latitude,$longitude($label)")
                val intent = Intent(Intent.ACTIVE_VIEW, geoUri)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

            showOnMap(60.0237, 30.2289, "Наш офис")


            fun shareText(text: String) {
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }

                val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
                startActivity(chooser)
            }

            shareText("Контакт: +7 (495) 123-45-67, contact@example.com")

        }
    }
}