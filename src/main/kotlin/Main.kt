import androidx.compose.ui.window.*
import di.appModule
import org.koin.core.context.startKoin

//https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/README.md

fun main() = application {

    //DI with Koin
    startKoin {
        modules(appModule)
    }

    MyApplication(rememberApplicationState())
}
