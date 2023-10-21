package ui.composable

import ClickableIcon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.WindowMinimize
import compose.icons.fontawesomeicons.solid.AngleLeft
import compose.icons.fontawesomeicons.solid.BorderStyle
import compose.icons.fontawesomeicons.solid.Cog
import ui.theme.*

@Composable
fun Footer(
    isBackButtonEnables:Boolean = false,
    onBackButtonClicked:() -> Unit = {},
    onGearButtonClicked:() -> Unit,
    modifier: Modifier = Modifier
){

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(DesktopBlackColor)
                .padding(vertical = MinimumPadding)
                .requiredHeight(DefaultFooterHeight)
        ) {
            Spacer(
                Modifier
                    .height(DefaultSpacerHeight)
                    .background(DesktopBackgroundColor)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = DefaultItemPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                if(isBackButtonEnables) {

                    ClickableIcon(
                        modifier = Modifier
                            .padding(horizontal = MinimumPadding, vertical = MinimumPadding),
                        imageVector = FontAwesomeIcons.Solid.AngleLeft,
                        colorTint = DesktopLightGreyColor,
                        iconSize = DefaultIconSize
                    ) {
                            onBackButtonClicked()
                    }

                }

                Spacer(Modifier.weight(1f))

                ClickableIcon(
                    modifier = Modifier
                        .padding(horizontal = MinimumPadding, vertical = MinimumPadding),
                    imageVector = FontAwesomeIcons.Solid.Cog,
                    colorTint = DesktopLightGreyColor,
                    iconSize = DefaultIconSize
                ) {
                    onGearButtonClicked()
                }
            }
        }

    }


