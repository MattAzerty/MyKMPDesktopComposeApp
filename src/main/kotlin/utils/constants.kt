package utils

import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CompactDisc
import compose.icons.fontawesomeicons.solid.Music
import compose.icons.fontawesomeicons.solid.Question
import data.domain.Feature
import ui.theme.DesktopBackgroundColor
import ui.theme.DesktopLightGreyColor

//WINDOW SETTINGS
const val MIN_HEIGHT_WINDOWS = 768
const val MIN_WIDTH_WINDOWS = 1024
//APP SETTINGS
const val APP_VERSION = "0.1.0"
const val MUSIC_DATA_SET_JSON = "music_data_set.json"

val FEATURE = listOf(
    Feature(
        id = 0,
        title = "QUIZ",
        imageVector = FontAwesomeIcons.Solid.Question,
        imageTint = DesktopLightGreyColor,
        backgroundCardColor = DesktopBackgroundColor
    ),
    Feature(
        id = 1,
        title = "MUSIC PLAYER",
        imageVector = FontAwesomeIcons.Solid.Music,
        imageTint = DesktopLightGreyColor,
        backgroundCardColor = DesktopBackgroundColor
    ),
    Feature(
        id = 2,
        title = "MY MIX",
        imageVector = FontAwesomeIcons.Solid.CompactDisc,
        imageTint = DesktopLightGreyColor,
        backgroundCardColor = DesktopBackgroundColor
    ),
)
//QUIZ SETTINGS
enum class QuestionType {
    TITLE, ARTIST, ALBUM, YEAR, TEMPO, RHYTHM, GENRE;
}
const val MAXIMUM_QUIZ_TIME_SECONDS = 10
//DATABASE
const val DATABASE_NAME = "MyDesktopAppDatabase"