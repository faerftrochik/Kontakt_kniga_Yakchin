import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactBookScreen()
        }
    }
}

@Composable
fun ContactBookScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Контактная книга", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // 1. Позвонить
        ContactButton("Позвонить") {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+74951234567"))
            safeStartActivity(context, intent)
        }

        // 2. Написать email
        ContactButton("Написать email") {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("contact@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Обращение")
            }
            safeStartActivity(context, intent)
        }

        // 3. Показать офис на карте
        ContactButton("Показать офис на карте") {
            val geoUri = Uri.parse("geo:60.0237,30.2289?q=60.0237,30.2289(Наш офис)")
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            safeStartActivity(context, intent)
        }

        // 4. Поделиться контактом
        ContactButton("Поделиться контактом") {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Контакт: +7 (495) 123-45-67, contact@example.com")
            }
            val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
            context.startActivity(chooser)
        }
    }
}

@Composable
fun ContactButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text)
    }
}

// Функция для безопасного запуска Intent с проверкой наличия приложения
fun safeStartActivity(context: Context, intent: Intent) {
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет подходящего приложения", Toast.LENGTH_SHORT).show()
    }
}
