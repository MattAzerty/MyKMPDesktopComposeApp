package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import ui.components.clickables.HoverButton
import ui.theme.DesktopBackgroundColor
import ui.theme.DesktopBlueColor
import ui.theme.DesktopFontFamily



class LogScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val screenModel = getScreenModel<LogScreenModel>()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DesktopBackgroundColor),
            contentAlignment = Alignment.Center
        ) {

            HoverButton(
                textButton = screenModel.string,
                backgroundColor = DesktopBlueColor,
                onButtonClick = {}
            )

        }
    }

}