import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import ui.window.MyApplicationWindow

@Composable
fun MyApplication(state: MyApplicationState) {

    for (window in state.windows) {
        key(window) {
            MyApplicationWindow(window)
        }
    }

}