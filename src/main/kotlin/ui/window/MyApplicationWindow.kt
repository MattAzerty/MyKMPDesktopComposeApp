package ui.window

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.window.WindowPlacement
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.ClickableIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.WindowMaximize
import compose.icons.fontawesomeicons.regular.WindowMinimize
import compose.icons.fontawesomeicons.regular.WindowRestore
import compose.icons.fontawesomeicons.solid.Times
import di.appModule
import org.koin.core.context.startKoin
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

        //DI with Koin
        startKoin {
            modules(appModule)
        }

//https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Window_API_new
    Window(
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

            //Window appearance properties
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
                        onMaximizeOrFloatingWindow = {
                            state.window.placement = if (state.window.placement == WindowPlacement.Floating) {
                                WindowPlacement.Maximized
                            } else {
                                WindowPlacement.Floating
                            }
                        }
                    )
                    // [2] - APP CONTENT
                    App(state)
                }
            }

        }
    }

@Composable
private fun WindowScope.AppWindowTitleBar(
    isWindowFloating:Boolean,
    onCloseWindow: () -> Unit,
    onMinimiseWindow: () -> Unit,
    onMaximizeOrFloatingWindow:() -> Unit,
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
            //MAXIMISE (OR OPPOSITE) WINDOW
            ClickableIcon(
                modifier = Modifier.padding(horizontal = MinimumPadding, vertical = MinimumPadding),
                imageVector = if(isWindowFloating) FontAwesomeIcons.Regular.WindowMaximize else FontAwesomeIcons.Regular.WindowRestore,
                colorTint = DesktopLightGreyColor,
                iconSize = MinimumIconSize
            ) {
                onMaximizeOrFloatingWindow()
            }
            //CLOSE WINDOW todo: here it's also closing the application since no multiple window are required
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
