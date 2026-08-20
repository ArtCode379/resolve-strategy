package resolveclaimsni.management.resolvestrategy.ui.composable.screen.bookings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import resolveclaimsni.management.resolvestrategy.R
import resolveclaimsni.management.resolvestrategy.ui.composable.shared.RHDNCContentWrapper
import resolveclaimsni.management.resolvestrategy.ui.composable.shared.RHDNCEmptyView
import resolveclaimsni.management.resolvestrategy.ui.state.BookingUiState
import resolveclaimsni.management.resolvestrategy.ui.state.DataUiState
import resolveclaimsni.management.resolvestrategy.ui.theme.Success
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.BookingViewModel

@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = koinViewModel(),
) {
    val bookingsState by viewModel.bookingsState.collectAsState()
    var canceledBookingNumber by remember { mutableStateOf<String?>(null) }
    BookingsContent(bookingsState, modifier) { canceledBookingNumber = it }
    canceledBookingNumber?.let { number ->
        AlertDialog(
            onDismissRequest = { canceledBookingNumber = null },
            title = { Text("Cancel this booking?") },
            text = { Text("This consultation will be removed from your schedule.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.cancelBooking(number)
                        canceledBookingNumber = null
                    },
                ) { Text("Cancel booking", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton(onClick = { canceledBookingNumber = null }) { Text("Keep booking") } },
        )
    }
}

@Composable
private fun BookingsContent(
    bookingsState: DataUiState<List<BookingUiState>>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (String) -> Unit,
) {
    RHDNCContentWrapper(
        dataState = bookingsState,
        dataPopulated = {
            BookingsPopulated(
                bookings = (bookingsState as DataUiState.Populated).data,
                modifier = modifier,
                onCancelBookingButtonClick = onCancelBookingButtonClick,
            )
        },
        dataEmpty = {
            RHDNCEmptyView(
                primaryText = stringResource(R.string.rhdnc_bookings_state_empty_primary_text),
                modifier = modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun BookingsPopulated(
    bookings: List<BookingUiState>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Text("Your consultations", style = MaterialTheme.typography.headlineMedium) }
        items(bookings, key = { it.bookingNumber }) { booking ->
            Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(booking.serviceName, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                        Surface(color = Success.copy(alpha = 0.12f), shape = RoundedCornerShape(50)) {
                            Text("Confirmed", color = Success, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                        }
                    }
                    Text("Booking #${booking.bookingNumber}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(booking.timestamp, style = MaterialTheme.typography.bodyLarge)
                    Text("Online conference · joining details sent by email", style = MaterialTheme.typography.bodyMedium)
                    TextButton(
                        onClick = { onCancelBookingButtonClick(booking.bookingNumber) },
                        modifier = Modifier.align(Alignment.End),
                    ) { Text("Cancel", color = MaterialTheme.colorScheme.error) }
                }
            }
        }
    }
}
