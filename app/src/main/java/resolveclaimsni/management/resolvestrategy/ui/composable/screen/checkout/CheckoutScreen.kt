package resolveclaimsni.management.resolvestrategy.ui.composable.screen.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import resolveclaimsni.management.resolvestrategy.data.entity.BookingEntity
import resolveclaimsni.management.resolvestrategy.data.repository.ServiceRepository
import resolveclaimsni.management.resolvestrategy.ui.state.DataUiState
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.CheckoutViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CheckoutScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToBookingsScreen: () -> Unit,
) {
    val repository: ServiceRepository = koinInject()
    val service = remember(serviceId) { repository.getById(serviceId) }
    val bookingState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalidState by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    var phone by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val enabled = viewModel.customerFirstName.isNotBlank() &&
        viewModel.customerLastName.isNotBlank() &&
        viewModel.customerEmail.isNotBlank() &&
        phone.isNotBlank() &&
        selectedDate.isNotBlank()

    if (bookingState is DataUiState.Populated) {
        CheckoutDialog(
            booking = (bookingState as DataUiState.Populated<BookingEntity>).data,
            onConfirm = onNavigateToBookingsScreen,
        )
    }
    CheckoutContent(
        serviceName = service?.name.orEmpty(),
        servicePrice = service?.price ?: 0.0,
        customerFirstName = viewModel.customerFirstName,
        customerLastName = viewModel.customerLastName,
        customerEmail = viewModel.customerEmail,
        phone = phone,
        selectedDate = selectedDate,
        notes = notes,
        isEmailInvalid = emailInvalidState,
        modifier = modifier,
        focusManager = LocalFocusManager.current,
        isButtonEnabled = enabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPhoneChanged = { phone = it },
        onDateChanged = { selectedDate = it },
        onNotesChanged = { notes = it },
        onPlaceBookingButtonClick = { viewModel.placeBooking(serviceId) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckoutContent(
    serviceName: String,
    servicePrice: Double,
    customerFirstName: String,
    customerLastName: String,
    customerEmail: String,
    phone: String,
    selectedDate: String,
    notes: String,
    isEmailInvalid: Boolean,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit,
    onPlaceBookingButtonClick: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateChanged(SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(millis)))
                        }
                        showDatePicker = false
                    },
                ) { Text("Select") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } },
        ) { DatePicker(state = datePickerState) }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Book your consultation", style = MaterialTheme.typography.headlineMedium)
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(serviceName, style = MaterialTheme.typography.titleMedium)
                Text("From £${servicePrice.toInt()}", color = MaterialTheme.colorScheme.primary)
            }
        }
        CheckoutTextField(customerFirstName, onFirstNameChanged, "First name", Modifier.fillMaxWidth())
        CheckoutTextField(customerLastName, onLastNameChanged, "Last name", Modifier.fillMaxWidth())
        CheckoutTextField(
            customerEmail,
            onEmailChanged,
            "Email",
            Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        CheckoutTextField(
            phone,
            onPhoneChanged,
            "Phone",
            Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )
        OutlinedTextField(
            value = selectedDate,
            onValueChange = onDateChanged,
            readOnly = true,
            label = { Text("Preferred date") },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Outlined.CalendarMonth, contentDescription = "Choose date")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
        )
        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChanged,
            label = { Text("Notes or priorities") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                focusManager.clearFocus()
                onPlaceBookingButtonClick()
            },
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Confirm Booking") }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText, style = MaterialTheme.typography.titleSmall) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        ),
    )
}
