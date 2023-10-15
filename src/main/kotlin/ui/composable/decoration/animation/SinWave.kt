package ui.composable.decoration.animation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.delay
import kotlin.math.sin

//https://codepen.io/pdx4d/pen/YVMKaM
//https://medium.com/google-developer-experts/exploring-jetpack-compose-canvas-the-power-of-drawing-8cc60815babe
//https://www.answertopia.com/jetpack-compose/jetpack-compose-canvas-graphics-drawing-tutorial/
//https://medium.com/s23nyc-tech/geometric-android-animations-using-the-canvas-dd687c43f3f4
@Composable
fun SineWaveGenerator(
    modifier: Modifier = Modifier,
    color:Color,
) {

    var time by remember { mutableStateOf(0.0) }

    // Request a re-composition to update the animation
    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            time += 0.01
        }
    }

    val waves = listOf(
        WaveParams(1f, 8f, 1f, 200f, color.copy(alpha = 0.3f)),
        WaveParams(1f, 4f, 1f, 100f, color.copy(alpha = 0.9f)),
        WaveParams(1f, 5f, -1f, 50f, color.copy(alpha = 0.5f)),
        WaveParams(1f, 1f, -1f, 100f, color),
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {

        val canvasWidth = size.width
        val canvasHeight = size.height


        with(drawContext.canvas.nativeCanvas) {

            val checkPoint = saveLayer(null, null)

            for (wave in waves) {
                val path = createSineWavePath(canvasWidth, canvasHeight, time, wave)
                drawPath(path, color = wave.strokeStyle, style = Stroke(wave.lineWidth))
            }

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color.Red,
                        Color.Transparent
                    ),
                    center = Offset(size.width/2, size.height/2),
                    radius = size.width/2f
                ),
                radius = size.width/2f,
                center = Offset(size.width/2, size.height/2),
                blendMode = BlendMode.DstIn
            )

            restoreToCount(checkPoint)
        }

    }
}

data class WaveParams(
    val timeModifier: Float,
    val lineWidth: Float,
    val amplitudeRatio: Float,
    val wavelength: Float,
    val strokeStyle: Color
)

fun createSineWavePath(
    canvasWidth: Float,
    canvasHeight: Float,
    time: Double,
    waveParams: WaveParams
): Path {

    val path = Path()
    val amplitude = waveParams.amplitudeRatio * canvasHeight
    val wavelength = waveParams.wavelength
    val waveCenter = canvasHeight / 2f
    val halfCanvasWidth = canvasWidth / 2f

    for (i in 0 until canvasWidth.toInt()) {

        val x = (time * 10 + (-waveCenter + i) / wavelength).toFloat() //time to seconds - center
        val normalizedX = (i - halfCanvasWidth) / halfCanvasWidth
        val amplitudeAtX = amplitude * (1 - normalizedX * normalizedX)
        val y = (sin(x) * amplitudeAtX + waveCenter)

        if (i == 0) {
            path.moveTo(0f, y)
        } else {
            path.lineTo(i.toFloat(), y)
        }
    }

    return path
}