package ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import ui.components.scrollable.LazyScrollable
import ui.theme.DesktopBackgroundColor
import ui.theme.DesktopBlackColor
import ui.theme.DesktopLightGreyColor

class HomeScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        val screenModel = getScreenModel<HomeScreenModel>()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DesktopBackgroundColor),
            //contentAlignment = Alignment.Center
        ) {

            Card(
                shape = RoundedCornerShape(44.dp),
                backgroundColor = DesktopBlackColor,
                elevation = 4.dp,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .padding(32.dp)
                    .align(Alignment.TopStart)

            ) {

                LazyScrollable()

        }
    }
}}