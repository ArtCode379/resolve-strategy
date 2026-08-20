package resolveclaimsni.management.resolvestrategy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BrandColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Accent,
    onSecondary = OnSurface,
    background = Background,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = ChipBackground,
    onSurfaceVariant = Muted,
    outline = Border,
    error = Warning,
)

@Composable
fun ServiceSkeletonTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = BrandColorScheme,
        typography = AppTypography,
        content = content,
    )
}
