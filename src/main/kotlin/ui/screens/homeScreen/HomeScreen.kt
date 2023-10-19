package ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.composable.AppBar
import ui.screens.quizScreen.QuizScreen
import ui.theme.DefaultItemPadding
import ui.theme.DesktopBackgroundColor
import ui.window.MyApplicationWindowState

data class HomeScreen(
    val state: MyApplicationWindowState
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val screenModel = getScreenModel<HomeScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DesktopBackgroundColor),
            contentAlignment = Alignment.Center
        ) {



        Column(
            modifier = Modifier
                .padding(DefaultItemPadding)

                .fillMaxSize()
        ) {

            AppBar(
                appBarText = screenModel.localization.appBarHomeScreen
            )

            FeaturesSection(
                features = screenModel.features,
                onFeatureClicked = {
                    when (it) {
                        0 -> navigator.push(QuizScreen(state))
                        else -> {
                            state.sendNotificationDialog( screenModel.localization.notYetImplementedMessage)
                        }
                    }
                }
            )

        }
    }}
}