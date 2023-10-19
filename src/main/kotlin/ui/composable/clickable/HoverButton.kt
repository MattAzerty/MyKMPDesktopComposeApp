
package ui.composable.clickable

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import ui.theme.DesktopFontFamily

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HoverButton(
    modifier: Modifier = Modifier,
    textButton:String,
    backgroundColor: Color = Color.Blue,
    disabledBackgroundColor:Color = Color.LightGray,
    disabledContentColor:Color = Color.DarkGray,
    onButtonClick: () -> Unit,
){

    var active by remember { mutableStateOf(false) }

    Button(
        modifier = modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
        ,
        enabled = active,
        onClick = onButtonClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor
        ),

        ) {
        Text(
            text = textButton,
            style = TextStyle(
                fontFamily = DesktopFontFamily,
                fontSize = 24.sp,
            ),
        )
    }
}