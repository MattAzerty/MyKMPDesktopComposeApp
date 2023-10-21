import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.theme.DesktopLightGreyColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ClickableIcon(
    modifier: Modifier,
    imageVector: ImageVector,
    colorTint: Color,
    iconSize: Dp,
    onIconClicked: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var active by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .background(if(active) DesktopLightGreyColor.copy(alpha = 0.2f) else Color.Transparent)
            .clickable(
                onClick = onIconClicked,
                enabled = true,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            ),
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Settings",
            modifier = Modifier
                .padding(4.dp)
                .size(iconSize),
            tint = colorTint
        )
    }
}