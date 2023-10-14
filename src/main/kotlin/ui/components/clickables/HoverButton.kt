package ui.components.clickables

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
    textButton:String,
    backgroundColor: Color,
    onButtonClick: () -> Unit,
){

    var active by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
        ,
        enabled = active,
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor ,
        ),

        ) {
        Text(
            text = textButton,
            style = TextStyle(
                fontFamily = DesktopFontFamily,
                fontSize = 24.sp,
                /*shadow = Shadow(
                    color = LightBlue, offset = Offset(7.0f, 5.0f), blurRadius = 0.5f
                )*/
            ),
        )
    }
}