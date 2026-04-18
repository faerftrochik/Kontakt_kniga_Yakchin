import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ContactBookScreen() {
    // Получаем контекст, так как в Composable нет 'this'
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Контактная книга", style = MaterialTheme.typography.headlineMedium)

        // 1. Позвонить (ACTION_DIAL - открывает набор номера)
        Button(onClick = {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+74951234567"))
            safeStart(context, intent)
        }) { Text("Позвонить") }

        // 2. Написать email (ACTION_SENDTO с mailto:)
        Button(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("contact@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Обращение")
            }
            safeStart(context, intent)
        }) { Text("Написать email") }

        // 3. Показать офис на карте (с меткой "Наш офис")
        Button(onClick = {
            val geoUri = Uri.parse("geo:60.0237,30.2289?q=60.0237,30.2289(Наш офис)")
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            safeStart(context, intent)
        }) { Text("Показать офис на карте") }

        // 4. Поделиться контактом (используем createChooser)
        Button(onClick = {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Контакт: +7 (495) 123-45-67, contact@example.com")
            }
            val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
            context.startActivity(chooser)
        }) { Text("Поделиться контактом") }
    }
}

// Функция для безопасного запуска Intent (Требование задания)
fun safeStart(context: Context, intent: Intent) {
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Нет подходящего приложения", Toast.LENGTH_SHORT).show()
    }
}
