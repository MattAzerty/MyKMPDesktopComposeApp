package ui.window

import ClickableIcon
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.window.WindowPlacement
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.WindowMaximize
import compose.icons.fontawesomeicons.regular.WindowMinimize
import compose.icons.fontawesomeicons.regular.WindowRestore
import compose.icons.fontawesomeicons.solid.Times
import ui.screens.logScreen.LogScreen
import ui.theme.*
import utils.FileDialog
import utils.MIN_HEIGHT_WINDOWS
import utils.MIN_WIDTH_WINDOWS
import java.awt.Dimension

@Composable
fun MyApplicationWindow(
    state: MyApplicationWindowState
) {

    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }

//https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Window_API_new
    Window(
            icon = painterResource("icon_quiz_linux.png"),
            transparent = true,
            undecorated = true,
            onCloseRequest = {exit()},
            state = state.window,

            ) {

        //minimum window size: 640*480
            window.minimumSize = Dimension(MIN_WIDTH_WINDOWS,MIN_HEIGHT_WINDOWS)

            //to launch a FileExplorerDialogWindow if needed
            if (state.openDialog.isAwaiting) {
                state.window.placement = WindowPlacement.Floating
                FileDialog(
                    title = "FileExplorer",
                    isLoad = true,
                    onResult = {
                        state.openDialog.onResult(it)
                    }
                )
            }

        //to launch a DialogWindow if needed
        if (state.messageNotificationDialog != null) {
            DialogWindow(
                icon = painterResource("image/notif_icon.png"),
                title = "Notification:",
                onCloseRequest = { state.closeNotificationDialog() },
                onPreviewKeyEvent = {
                    if (it.key == Key.Escape && it.type == KeyEventType.KeyDown) {
                        state.closeNotificationDialog()
                        true
                    } else {
                        false
                    }
                }) {
                window.setSize(500,200)
                window.minimumSize = Dimension(500,200)
                Box(Modifier.fillMaxSize().background(DesktopLightGreyColor)){
                    Text(
                        modifier = Modifier.align(Alignment.Center).padding(DefaultItemPadding),
                        text = state.messageNotificationDialog ?: "Error",
                        style = TextStyle(
                            fontFamily = DesktopFontFamily,
                            fontSize = 40.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(0.3f),
                                offset = Offset(7.0f, 4.0f),
                                blurRadius = 0.5f
                            )
                        )
                    )
                }

            }
        }

            //Main Window appearance properties
            Surface(
                modifier = Modifier.fillMaxSize().padding(DefaultTextPadding).shadow(DefaultShadowElevation, RoundedCornerShape(WindowRoundedCornerSize)),
                shape = RoundedCornerShape(WindowRoundedCornerSize)
            ) {

                Column {
                    // [1] - TITLE APPBAR
                    AppWindowTitleBar(
                        isWindowFloating =  state.window.placement == WindowPlacement.Floating,
                        onCloseWindow = { exit() },
                        onMinimiseWindow = { state.window.isMinimized = !state.window.isMinimized },
                        onToggleFullscreen = state::toggleFullscreen
                    )
                    // [2] - APP CONTENT
                    App(state)
                }
            }

        }
    }
//Custom titleBar
@Composable
private fun WindowScope.AppWindowTitleBar(
    isWindowFloating:Boolean,
    onCloseWindow: () -> Unit,
    onMinimiseWindow: () -> Unit,
    onToggleFullscreen:() -> Unit,
) = WindowDraggableArea {

    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(DesktopBlackColor)
    ){

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            //MINIMISE WINDOW
            ClickableIcon(
                modifier = Modifier.padding(horizontal = MinimumPadding, vertical = MinimumPadding),
                imageVector = FontAwesomeIcons.Regular.WindowMinimize,
                colorTint = DesktopLightGreyColor,
                iconSize = MinimumIconSize
            ) {
                onMinimiseWindow()
            }
            //TOGGLE FULLSCREEN
            ClickableIcon(
                modifier = Modifier.padding(horizontal = MinimumPadding, vertical = MinimumPadding),
                imageVector = if(isWindowFloating) FontAwesomeIcons.Regular.WindowMaximize else FontAwesomeIcons.Regular.WindowRestore,
                colorTint = DesktopLightGreyColor,
                iconSize = MinimumIconSize
            ) {
                onToggleFullscreen()
            }
            //CLOSE WINDOW
            ClickableIcon(
                modifier = Modifier.padding(horizontal = MinimumPadding, vertical = MinimumPadding),
                imageVector = FontAwesomeIcons.Solid.Times,
                colorTint = DesktopRedColor,
                iconSize = MinimumIconSize
            ) {
                onCloseWindow()
            }

            Spacer(Modifier.width(8.dp))

        }
    }
}

@Composable
@Preview
fun App(state: MyApplicationWindowState) {
    //https://voyager.adriel.cafe/navigation
    Navigator(LogScreen(state)){
        ScaleTransition(it)
    }
}
