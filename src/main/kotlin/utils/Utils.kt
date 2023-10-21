package utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.loadImageBitmap
import ui.theme.Localization
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.abs

fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}

fun getTranslatedTypeOfQuestionString(localization: Localization, questionType: QuestionType): String {
    return when(questionType){
        QuestionType.TITLE -> localization.title
        QuestionType.ARTIST -> localization.artist
        QuestionType.ALBUM -> localization.album
        QuestionType.YEAR -> localization.year
        QuestionType.TEMPO -> localization.tempo
        QuestionType.RHYTHM -> localization.rhythm
        QuestionType.GENRE -> localization.genre
    }
}

fun timestampToDateString(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val zoneId = ZoneId.systemDefault()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zoneId)
    return formatter.format(instant)
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

//linear interpolation ex: start 10f stop 20f fraction 05f -> lerp = 15.0 when start fraction
// = 0 when stop fraction should be equal to 1
fun linearInterpolation(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}
