package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.theme.*

@Composable
fun Footer(
    modifier: Modifier = Modifier
){

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(DesktopBlackColor)
                .padding(vertical = DefaultSpacerPadding)
                .defaultMinSize(minHeight = DefaultFooterHeight)
        ) {
            Spacer(
                Modifier
                    .height(DefaultSpacerHeight)
                    .background(DesktopBackgroundColor)
                    .fillMaxWidth()
            )
        }

    }


