package ui.screens.homeScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.domain.Feature
import ui.theme.*
import utils.noRippleClickable
import utils.standardQuadFromTo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FeatureItem(
    feature: Feature,
    onFeatureClicked: (id: Int) -> Unit,
) {
//GraphicLayer
    val density = LocalDensity.current
    val translationYAnimateForIconToPx = with(density) { -8.dp.roundToPx() }.toFloat()
    val translationYAnimateForTextToPx = with(density) { 14.dp.roundToPx() }.toFloat()
    var active by remember { mutableStateOf(false) }
    val rotationXAnimateForCard: Float by animateFloatAsState(if (active) 50f else 0f)
    val translationYAnimateForIcon: Float by animateFloatAsState(if(active) translationYAnimateForIconToPx else 0f)
    val translationYAnimateForText: Float by animateFloatAsState(if(active) translationYAnimateForTextToPx else 0f)
    val scaleAnimateForIcon: Float by animateFloatAsState(if (active) 1.2f else 1f)

    Box(
        modifier = Modifier
            .padding(DefaultItemPadding)
            .requiredWidth(120.dp)
            .requiredHeight(108.dp)
    ) {

    Card(
        modifier = Modifier
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .noRippleClickable {
                onFeatureClicked(feature.id)
            }
            .graphicsLayer {
                this.rotationX = rotationXAnimateForCard
            },
        contentColor = Color.Transparent,
        border = BorderStroke(2.dp, color = if(active) DesktopBlueColor else feature.imageTint),
        elevation = 2.dp,
        shape = RoundedCornerShape(16.dp)
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .background(feature.backgroundCardColor)
        ) {

            val width = constraints.maxWidth
            val height = constraints.maxHeight

            // Medium colored path
            val mediumColoredPoint1 = Offset(0f, -height.toFloat() / 10f)
            val mediumColoredPoint2 = Offset(width * 0f, height * 0.8f)
            val mediumColoredPoint3 = Offset(width * 0.1f, height * 0.55f)
            val mediumColoredPoint4 = Offset(width * 0.85f, height.toFloat()*0.88f)
            val mediumColoredPoint5 = Offset(width * 1.5f, -height.toFloat() / 3f)

            val mediumColoredPath = Path().apply {
                moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
                standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
                standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
                standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
                standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
                lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                lineTo(-100f, height.toFloat() + 100f)
                close()
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawPath(
                    path = mediumColoredPath,
                    color = DesktopBlueColor
                )}

        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = feature.imageVector,
            contentDescription = feature.title,
            modifier = Modifier
                .graphicsLayer {
                    this.translationY = translationYAnimateForIcon
                    this.scaleX = scaleAnimateForIcon
                    this.scaleY = scaleAnimateForIcon
                }
                .size(48.dp),
            tint = feature.imageTint
        )
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        Text(
            modifier = Modifier.graphicsLayer {
                this.translationY = translationYAnimateForText
                this.scaleX = scaleAnimateForIcon
                this.scaleY = scaleAnimateForIcon
            },
            text = "• ${feature.title} •",
            //color = if(active) DesktopYellowColor else DesktopBackgroundColor,
            //fontFamily = DesktopFontFamily,
            //fontSize = 16.sp
            style = TextStyle(
                color = if(active) DesktopLightGreyColor else DesktopBackgroundColor,
                fontFamily = DesktopFontFamily,
                fontSize = 16.sp,
                shadow = if(active) Shadow(
                    color = Color.Black.copy(0.3f),
                    offset = Offset(7.0f, 4.0f),
                    blurRadius = 0.5f
                ) else null
            )
        )
        Spacer(modifier = Modifier.weight(0.7f))
    }

}}