package ui.screens.logScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.composable.AppBar
import ui.composable.clickable.HoverButton
import ui.composable.decoration.animation.SineWaveGenerator
import ui.composable.Footer
import ui.screens.homeScreen.HomeScreen
import ui.theme.*
import ui.window.MyApplicationWindowState
import utils.APP_VERSION


data class LogScreen(val state: MyApplicationWindowState) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val screenModel = getScreenModel<LogScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DesktopBackgroundColor),
            contentAlignment = Alignment.Center
        ) {

        // [A] - LOGSCREEN
            Column(
                modifier = Modifier
                    .padding(DefaultItemPadding)
                    .fillMaxSize()
            ) {

                // [1] - HEADER APP BAR
                AppBar(appBarText = screenModel.uiViewState.localization.appName.uppercase())

                Spacer(Modifier.weight(0.4f))

                // [2] - SINUS WAVES ANIMATION
                SineWaveGenerator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f),
                    color= DesktopBlueColor
                )

                Spacer(Modifier.weight(0.4f))

                // [3] - INFORMATION'S ABOUT THE APP
                Text(
                    modifier = Modifier
                        .padding(DefaultTextPadding)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    color = DesktopLightGreyColor,
                    text = "vers.$APP_VERSION ${screenModel.uiViewState.localization.language}, OS:${System.getProperty("os.name")}",
                    style = TextStyle(
                        fontFamily = DesktopFontSecondFamily,
                        fontSize = SmallTextSize,
                        textAlign = TextAlign.End
                    )
                )

                // [4] - BOTTOM BAR
                Footer(
                    onGearButtonClicked = { state.sendNotificationDialog(screenModel.uiViewState.localization.notYetImplementedMessage) }
                )

            }

        // [B] (in front of A) - START BUTTON
            HoverButton(
                textButton = screenModel.uiViewState.localization.startButtonLogScreen,
                backgroundColor = DesktopBlueColor,
                disabledBackgroundColor = DesktopYellowColor,
                disabledContentColor = Color.Black,
                onButtonClick = {navigator.push(HomeScreen(state))}
            )
        }
}}


