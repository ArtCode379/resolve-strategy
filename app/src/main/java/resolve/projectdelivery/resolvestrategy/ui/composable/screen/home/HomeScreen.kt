package resolve.projectdelivery.resolvestrategy.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import resolve.projectdelivery.resolvestrategy.R
import resolve.projectdelivery.resolvestrategy.data.model.ServiceModel
import resolve.projectdelivery.resolvestrategy.ui.composable.shared.TGRGKContentWrapper
import resolve.projectdelivery.resolvestrategy.ui.composable.shared.TGRGKEmptyView
import resolve.projectdelivery.resolvestrategy.ui.state.DataUiState
import resolve.projectdelivery.resolvestrategy.ui.theme.GradientEnd
import resolve.projectdelivery.resolvestrategy.ui.theme.GradientStart
import resolve.projectdelivery.resolvestrategy.ui.viewmodel.ServiceViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = koinViewModel(),
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    val servicesState by viewModel.servicesState.collectAsState()
    HomeContent(servicesState, modifier, onNavigateToServiceDetails)
}

@Composable
private fun HomeContent(
    servicesState: DataUiState<List<ServiceModel>>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    TGRGKContentWrapper(
        dataState = servicesState,
        dataPopulated = {
            ServicesPopulated(
                services = (servicesState as DataUiState.Populated).data,
                modifier = modifier,
                onNavigateToServiceDetails = onNavigateToServiceDetails,
            )
        },
        dataEmpty = {
            TGRGKEmptyView(
                primaryText = stringResource(R.string.tgrgk_services_state_empty_primary_text),
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ServicesPopulated(
    services: List<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val categories = listOf(
        "Strategic Planning" to Icons.Outlined.TrackChanges,
        "People & Leadership" to Icons.Outlined.Groups,
        "Business Optimisation" to Icons.Outlined.SettingsSuggest,
        "Performance" to Icons.Outlined.Assessment,
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Text("Clarity for what comes next", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Practical strategy for stronger organisations.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToServiceDetails(12) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(18.dp),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd)))
                        .padding(20.dp),
                ) {
                    Column {
                        Text("NEXT AVAILABLE", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("Tomorrow · 9:00 AM", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleLarge)
                        Text("Executive advisory session", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                    }
                }
            }
        }
        item {
            Text("Explore by focus", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(categories) { category ->
                    CategoryCard(
                        label = category.first,
                        icon = category.second,
                        selected = selectedCategory == category.first,
                        onClick = {
                            selectedCategory = if (selectedCategory == category.first) {
                                null
                            } else {
                                category.first
                            }
                        },
                    )
                }
            }
        }
        item {
            Text(
                text = "Consulting services",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        val visibleServices = selectedCategory?.let { category ->
            services.filter { service -> service.category == category }
        } ?: services
        items(visibleServices, key = { it.id }) { service ->
            ServiceCard(service) { onNavigateToServiceDetails(service.id) }
        }
        item {
            Text("Selected outcomes", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    PortfolioCard(
                        title = "Operating model redesign",
                        outcome = "32% faster decisions",
                        imageUrl = services[1].imageUrl,
                        onClick = { onNavigateToServiceDetails(2) },
                    )
                }
                item {
                    PortfolioCard(
                        title = "Performance system rollout",
                        outcome = "One shared KPI framework",
                        imageUrl = services[3].imageUrl,
                        onClick = { onNavigateToServiceDetails(4) },
                    )
                }
                item {
                    PortfolioCard(
                        title = "Customer process transformation",
                        outcome = "24% shorter lead time",
                        imageUrl = services[10].imageUrl,
                        onClick = { onNavigateToServiceDetails(11) },
                    )
                }
            }
        }
        item {
            Text("Knowledge base", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            KnowledgeCard(
                title = "Leading through change without losing momentum",
                summary = "How leaders can create clarity, listen well and turn uncertainty into coordinated action.",
                onClick = { onNavigateToServiceDetails(6) },
            )
            Spacer(Modifier.height(8.dp))
            KnowledgeCard(
                title = "KPIs that improve decisions, not just reports",
                summary = "A practical guide to selecting measures that expose progress and prompt useful conversations.",
                onClick = { onNavigateToServiceDetails(4) },
            )
            Spacer(Modifier.height(8.dp))
            KnowledgeCard(
                title = "Five signs a core process needs redesign",
                summary = "Recognise delays, unclear handoffs and hidden rework before they become structural problems.",
                onClick = { onNavigateToServiceDetails(3) },
            )
        }
    }
}

@Composable
private fun KnowledgeCard(title: String, summary: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(summary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun CategoryCard(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            },
        ),
    ) {
        Column(
            modifier = Modifier
                .width(132.dp)
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(34.dp))
            Spacer(Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, maxLines = 2)
        }
    }
}

@Composable
private fun PortfolioCard(
    title: String,
    outcome: String,
    imageUrl: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
        )
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = outcome,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun ServiceCard(service: ServiceModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(104.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(service.category.uppercase(), color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelSmall)
                Text(service.name, style = MaterialTheme.typography.titleMedium)
                Text(service.description, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
                Text("From £${service.price.toInt()}  ·  Book now", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}
