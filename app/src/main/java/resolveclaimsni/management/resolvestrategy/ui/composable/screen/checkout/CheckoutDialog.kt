package resolveclaimsni.management.resolvestrategy.ui.composable.screen.checkout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import resolveclaimsni.management.resolvestrategy.data.entity.BookingEntity

@Composable
fun CheckoutDialog(booking: BookingEntity, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text("Consultation confirmed") },
        text = {
            Text(
                text = "Booking #${booking.bookingNumber}\n\n" +
                    "Your consultant will be waiting in the online conference or at the office " +
                    "at the appointed time. Joining details will be sent to ${booking.customerEmail}.",
            )
        },
        confirmButton = { TextButton(onClick = onConfirm) { Text("View bookings") } },
    )
}
