package ui.window

import androidx.compose.runtime.*
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import utils.DialogState
import java.nio.file.Path

class MyApplicationWindowState(
        path: Path?,
    private val exit: (MyApplicationWindowState) -> Unit
) {

    val window = WindowState()

    val openDialog = DialogState<Path?>()

    var messageNotificationDialog:String? by mutableStateOf(null)
        private set


    fun toggleFullscreen() {
        window.placement = if (window.placement == WindowPlacement.Fullscreen) {
            WindowPlacement.Floating
        } else {
            WindowPlacement.Fullscreen
        }
    }

    fun sendNotificationDialog(myMessage:String){
        messageNotificationDialog = myMessage
    }

    fun exit(): Boolean {
        exit(this)
        return true
    }

    suspend fun open() {
        val path = openDialog.awaitResult()
    }

    fun closeNotificationDialog() {
        messageNotificationDialog = null
    }

}