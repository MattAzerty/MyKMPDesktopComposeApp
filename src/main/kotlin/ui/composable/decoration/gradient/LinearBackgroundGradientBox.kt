package ui.composable.decoration.gradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ui.theme.DesktopBlueColor


@Composable
fun LinearBackgroundGradientBox(
    modifier: Modifier = Modifier,
){

    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    0.0f to DesktopBlueColor.copy(alpha = 0.3f),
                    1.0f to Color.Transparent,
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
    )

}