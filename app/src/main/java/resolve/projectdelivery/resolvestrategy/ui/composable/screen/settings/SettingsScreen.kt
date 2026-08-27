package resolve.projectdelivery.resolvestrategy.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    SettingsScreenContent(
        modifier = modifier,
        onOpenLegal = {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://resolveproject.courses/"),
                ),
            )
        },
        onCustomerSupport = {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://resolveproject.courses/"),
                ),
            )
        },
    )
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onOpenLegal: () -> Unit,
    onCustomerSupport: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("About", style = MaterialTheme.typography.headlineMedium)
        Card(Modifier.fillMaxWidth()) {
            SettingRow(Icons.Outlined.Business, "Company", "RESOLVE PROJECT DELIVERY LTD")
            SettingRow(Icons.Outlined.Info, "App version", "1.0.0")
        }
        Text("Legal", style = MaterialTheme.typography.titleLarge)
        Card(Modifier.fillMaxWidth()) {
            LegalRow(
                icon = Icons.Outlined.Policy,
                label = "Privacy & terms",
                onClick = onOpenLegal,
            )
            LegalRow(
                icon = Icons.Outlined.Language,
                label = "Company website",
                onClick = onOpenLegal,
            )
        }
        Text("Customer Support", style = MaterialTheme.typography.titleLarge)
        Text("Talk to our team about your consultation or using Resolve Strategy.")
        OutlinedButton(onClick = onCustomerSupport, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Outlined.SupportAgent, contentDescription = null)
            Text("  Customer Support")
        }
    }
}

@Composable
private fun LegalRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
        )
        Text("Open")
    }
}

@Composable
private fun SettingRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Column {
            Text(label, style = MaterialTheme.typography.labelLarge)
            Text(value, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
