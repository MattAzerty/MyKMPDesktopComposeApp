import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import ui.window.MyApplicationWindowState

@Composable
fun rememberApplicationState() = remember {
    MyApplicationState().apply {
        newWindow()
    }
}

class MyApplicationState {

    private val _windows = mutableStateListOf<MyApplicationWindowState>()
    val windows: List<MyApplicationWindowState> get() = _windows

    fun newWindow() {
        _windows.add(
            MyApplicationWindowState(
                application = this,
                path = null,
                exit = _windows::remove
            )
        )
    }

    suspend fun exit() {
        val windowsCopy = windows.reversed()
        for (window in windowsCopy) {
            if (!window.exit()) {
                break
            }
        }
    }
}