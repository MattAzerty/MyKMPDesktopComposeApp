package ui.screens.logScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.clickable.HoverButton
import ui.components.decoration.animation.SineWaveGenerator
import ui.components.Footer
import ui.screens.homeScreen.HomeScreen
import ui.theme.*
import utils.APP_VERSION


class LogScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        /*LifecycleEffect(
            onStarted = { println("Navigator: Start screen") },
            onDisposed = { println("Navigator: Dispose screen") }
        )*/


        val screenModel = getScreenModel<LogScreenModel>()
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(width = 2.dp, color = DesktopBlackColor),
                    elevation = 8.dp,
                    backgroundColor = DesktopBlackColor.copy(alpha = 0.8f)
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(DefaultItemPadding),
                        color = DesktopLightGreyColor,
                        text = screenModel.uiViewState.localization.appName.uppercase(),
                        style = TextStyle(
                            fontFamily = DesktopFontFamily,
                            fontSize = 40.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(0.3f),
                                offset = Offset(7.0f, 4.0f),
                                blurRadius = 0.5f
                            )
                        )
                    )
                }

                Spacer(Modifier.weight(0.4f))

                SineWaveGenerator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f),
                    color= DesktopBlueColor
                )

                Spacer(Modifier.weight(0.4f))
                Text(
                    modifier = Modifier
                        .padding(DefaultTextPadding)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    color = DesktopLightGreyColor,
                    text = "vers.$APP_VERSION ${screenModel.uiViewState.localization.language}, OS:${System.getProperty("os.name")}",
                    style = TextStyle(
                        fontFamily = DesktopFontSecondFamily,
                        fontSize = 12.sp,
                        textAlign = TextAlign.End
                    )
                )
                Footer(Modifier)

            }


            HoverButton(
                //modifier = Modifier.align(Alignment.Center),
                textButton = screenModel.uiViewState.localization.startButton,
                backgroundColor = DesktopRedColor,
                disabledBackgroundColor = DesktopYellowColor,
                disabledContentColor = Color.Black,
                onButtonClick = {navigator.push(HomeScreen())}
            )

        }
}}


