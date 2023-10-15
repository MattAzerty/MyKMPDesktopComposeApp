package ui.window

import MyApplicationState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.DialogState
import java.nio.file.Path

class MyApplicationWindowState(
    private val application: MyApplicationState,
    path: Path?,
    private val exit: (MyApplicationWindowState) -> Unit
) {

    val window = WindowState()

    val openDialog = DialogState<Path?>()

    var isInit by mutableStateOf(false)
        private set

    var path by mutableStateOf(path)
        private set

    var isChanged by mutableStateOf(false)
        private set

    private var _text by mutableStateOf("")

    var text: String
        get() = _text
        set(value) {
            check(isInit)
            _text = value
            isChanged = true
        }

    suspend fun exit(): Boolean {
        exit(this)
        return true
        /*return if (askToSave()) {
            exit(this)
            true
        } else {
            false
        }*/
    }

    suspend fun open() {
        val path = openDialog.awaitResult()
        if (path != null) {
            open(path)
        }
        /*if (askToSave()) {
            val path = openDialog.awaitResult()
            if (path != null) {
                open(path)
            }
        }*/

    }

    private suspend fun open(path: Path) {
        isInit = false
        isChanged = false
        this.path = path
        try {
            _text = path.readTextAsync()
            isInit = true
        } catch (e: Exception) {
            e.printStackTrace()
            text = "Cannot read $path"
        }
    }

    private suspend fun Path.readTextAsync() = withContext(Dispatchers.IO) {
        toFile().readText()
    }

}