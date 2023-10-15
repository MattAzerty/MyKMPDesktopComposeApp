import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScaleTransition
import com.withings.mycomposeandblepractice.ui.presentation.components.clickables.ClickableIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.WindowClose
import compose.icons.fontawesomeicons.regular.WindowMaximize
import compose.icons.fontawesomeicons.regular.WindowMinimize
import compose.icons.fontawesomeicons.regular.WindowRestore
import compose.icons.fontawesomeicons.solid.Times
import di.appModule
import org.koin.core.context.startKoin
import ui.screens.logScreen.LogScreen
import ui.theme.DesktopBlackColor
import ui.theme.DesktopLightGreyColor
import ui.theme.DesktopRedColor
import utils.MIN_HEIGHT_WINDOWS
import utils.MIN_WIDTH_WINDOWS
import java.awt.Dimension


//https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/README.md

@Composable
@Preview
fun App() {
    //https://voyager.adriel.cafe/navigation
    Navigator(LogScreen()){
        ScaleTransition(it)
    }
}

fun main() = application {

    val state = rememberWindowState(placement = WindowPlacement.Floating)
    var isAskingToClose by remember { mutableStateOf(false) }

    if (!isAskingToClose) {

        startKoin {
        modules(appModule)
        }



//https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Window_API_new
        Window(
            title = "Compose for Desktop",
            transparent = true,
            undecorated = true,
            onCloseRequest = ::exitApplication,
            state = state,

        ) {

            window.minimumSize = Dimension(MIN_WIDTH_WINDOWS,MIN_HEIGHT_WINDOWS)


            Surface(
                modifier = Modifier.fillMaxSize().padding(5.dp).shadow(3.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column {
                    AppWindowTitleBar(
                        isWindowsFloating =  state.placement == WindowPlacement.Floating,
                        onCloseWindow = { isAskingToClose = true },
                        onMinimiseWindow = { state.isMinimized = !state.isMinimized },
                        onMaximizeOrFloatingWindow = {
                            state.placement = if (state.placement == WindowPlacement.Floating) {
                                WindowPlacement.Maximized
                            } else {
                                WindowPlacement.Floating
                            }
                        }
                    )
                    App()
                }

            }
        }
    }
}

@Composable
private fun WindowScope.AppWindowTitleBar(
    isWindowsFloating:Boolean,
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

            ClickableIcon(
                modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp),
                imageVector = FontAwesomeIcons.Regular.WindowMinimize,
                colorTint = DesktopLightGreyColor,
                iconSize = 16.dp
            ) {
                onMinimiseWindow()
            }

            ClickableIcon(
                modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp),
                imageVector = if(isWindowsFloating) FontAwesomeIcons.Regular.WindowMaximize else FontAwesomeIcons.Regular.WindowRestore,
                colorTint = DesktopLightGreyColor,
                iconSize = 16.dp
            ) {
                onMaximizeOrFloatingWindow()
            }

            ClickableIcon(
                modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp),
                imageVector = FontAwesomeIcons.Solid.Times,
                colorTint = DesktopRedColor,
                iconSize = 16.dp
            ) {
                onCloseWindow()
            }

            Spacer(Modifier.width(8.dp))


        }
    }
}
