package ui.screens.homeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.domain.Feature
import ui.theme.*
import utils.standardQuadFromTo

@Composable
fun FeatureItem(
    feature: Feature,
    onFeatureClicked: (id: Int) -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onFeatureClicked(feature.id)
            }
            .width(32.dp)
            .height(96.dp),
        contentColor = Color.Transparent,
        border = BorderStroke(2.dp, color = DesktopLightGreyColor),
        elevation = 2.dp,
        shape = RoundedCornerShape(16.dp)
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(vertical = 11.dp))
        Icon(
            imageVector = feature.imageVector,
            contentDescription = feature.title,
            modifier = Modifier
                .size(48.dp),
            tint = feature.imageTint
        )
        Spacer(modifier = Modifier.padding(vertical = DefaultImagePadding))
        Text(
            text = "• ${feature.title} •",
            color = DesktopBackgroundColor,
            fontFamily = DesktopFontFamily,
            fontSize = 16.sp
        )
    }

}