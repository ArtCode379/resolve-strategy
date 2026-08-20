package resolveclaimsni.management.resolvestrategy.ui.composable.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import resolveclaimsni.management.resolvestrategy.R
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.RHDNCOnboardingVM

data class OnboardingContent(
    @field:StringRes val titleRes: Int,
    @field:StringRes val descriptionRes: Int,
    @field:DrawableRes val imageRes: Int,
)

private val onboardingPagesContent = listOf(
    OnboardingContent(R.string.rhdnc_page_1_title, R.string.rhdnc_page_1_description, R.drawable.service_1),
    OnboardingContent(R.string.rhdnc_page_2_title, R.string.rhdnc_page_2_description, R.drawable.service_2),
    OnboardingContent(R.string.rhdnc_page_3_title, R.string.rhdnc_page_3_description, R.drawable.service_3),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: RHDNCOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()
    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) onNavigateToHomeScreen()
    }
    OnboardingScreenContent(modifier, viewModel::setOnboarded)
}

@Composable
private fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    onOnboardingComplete: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPagesContent.size })
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            val item = onboardingPagesContent[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(item.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(20.dp)),
                )
                Spacer(Modifier.height(28.dp))
                Text(stringResource(item.titleRes), style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(item.descriptionRes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(onboardingPagesContent.size) { index ->
                Box(
                    Modifier
                        .size(if (index == pagerState.currentPage) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (index == pagerState.currentPage) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline),
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                if (pagerState.currentPage == onboardingPagesContent.lastIndex) {
                    onOnboardingComplete()
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (pagerState.currentPage == onboardingPagesContent.lastIndex) "Get Started" else "Next")
        }
    }
}
