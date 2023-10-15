package ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.DefaultItemPadding
import ui.theme.DesktopBlackColor
import ui.theme.DesktopFontFamily
import ui.theme.DesktopLightGreyColor

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    appBarText:String,
){

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
            text = appBarText,
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

}