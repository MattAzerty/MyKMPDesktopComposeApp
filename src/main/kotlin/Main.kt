import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import di.appModule
import org.koin.core.context.startKoin
import ui.screens.LogScreen
import ui.theme.Localization
import ui.theme.getCurrentLocalization

//https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/README.md

@Composable
@Preview
fun App() {
    //https://voyager.adriel.cafe/navigation
    Navigator(LogScreen())
}

fun main() = application {


    startKoin {
        modules(appModule)
    }


    //DataApplication().sayHello()
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
