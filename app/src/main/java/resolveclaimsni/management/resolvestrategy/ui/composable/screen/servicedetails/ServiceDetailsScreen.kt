package resolveclaimsni.management.resolvestrategy.ui.composable.screen.servicedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import resolveclaimsni.management.resolvestrategy.R
import resolveclaimsni.management.resolvestrategy.data.model.ServiceModel
import resolveclaimsni.management.resolvestrategy.ui.composable.shared.RHDNCContentWrapper
import resolveclaimsni.management.resolvestrategy.ui.composable.shared.RHDNCEmptyView
import resolveclaimsni.management.resolvestrategy.ui.state.DataUiState
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.ServiceDetailsViewModel

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    val serviceState by viewModel.serviceState.collectAsState()
    LaunchedEffect(serviceId) { viewModel.observeServiceById(serviceId) }
    RHDNCContentWrapper(
        dataState = serviceState,
        dataPopulated = {
            ServicesDetailsPopulated(
                service = (serviceState as DataUiState.Populated).data,
                modifier = modifier,
                onNavigateToCheckout = onNavigateToCheckout,
            )
        },
        dataEmpty = {
            RHDNCEmptyView(
                primaryText = stringResource(R.string.rhdnc_service_details_state_empty_primary_text),
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ServicesDetailsPopulated(
    service: ServiceModel,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    var selectedTime by remember { mutableStateOf(service.availableTime?.firstOrNull()) }
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
            )
        }
        item {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(service.name, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(50)) {
                    Text(service.category, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(14.dp))
                Text("From £${service.price.toInt()} · ${service.durationMinutes} min", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(14.dp))
                Text(service.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(22.dp))
                Text("What’s included", style = MaterialTheme.typography.titleLarge)
                service.features.forEach { feature ->
                    Row(modifier = Modifier.padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        Spacer(Modifier.width(10.dp))
                        Text(feature)
                    }
                }
                Spacer(Modifier.height(14.dp))
                Text("Available times", style = MaterialTheme.typography.titleLarge)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(service.availableTime.orEmpty()) { time ->
                        AssistChip(
                            onClick = { selectedTime = time },
                            label = { Text(if (selectedTime == time) "✓ $time" else time.toString()) },
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { onNavigateToCheckout(service.id) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.rhdnc_button_book_consultation_text))
                }
            }
        }
    }
}
