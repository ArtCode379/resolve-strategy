package resolveclaimsni.management.resolvestrategy.ui.composable.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import resolveclaimsni.management.resolvestrategy.R
import resolveclaimsni.management.resolvestrategy.ui.theme.GradientEnd
import resolveclaimsni.management.resolvestrategy.ui.theme.GradientStart
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.RHDNCSplashVM
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: RHDNCSplashVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboardedState by viewModel.onboardedState.collectAsStateWithLifecycle()
    var ready by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        ready = true
    }
    LaunchedEffect(ready, onboardedState) {
        if (ready) {
            if (onboardedState) onNavigateToHomeScreen() else onNavigateToOnboarding()
        }
    }
    SplashScreenContent(modifier = modifier)
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(800),
        label = "splash",
    )
    LaunchedEffect(Unit) { visible = true }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.rhdnc_ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(132.dp)
                .scale(0.8f + progress * 0.2f)
                .alpha(progress),
        )
        Text(
            text = stringResource(R.string.rhdnc_app_name),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.alpha(progress),
        )
    }
}
