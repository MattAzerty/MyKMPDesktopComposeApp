package utils

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
