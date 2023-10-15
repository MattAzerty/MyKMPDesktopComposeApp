package ui.screens.quizScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ui.theme.DefaultItemPadding

@Composable
fun AlbumItem(
    headerTextItem:String,
    itemText:String,
    animateFront: Float
) {

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = headerTextItem,
                color = Color.Gray,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = animateFront
                    }
            )
            Text(
                text = itemText,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = animateFront
                    }
            )
        }
    Spacer(Modifier.width(DefaultItemPadding))
    }

}