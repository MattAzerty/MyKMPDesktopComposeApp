package ui.screens.quizScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ui.theme.DefaultSpacerHeight
import ui.theme.DesktopLightGreyColor

@Composable
fun TimerDisplay(
    modifier: Modifier = Modifier,
    formattedTime: String,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formattedTime,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(DefaultSpacerHeight).background(DesktopLightGreyColor))
        }

    }
