package ui.theme

    // - [1] add available language here:
enum class AvailableLanguages {
    FR,
    EN;
}
    // - [2] create a new string entry here:
interface Localization {
    val appName: String
    val language:String
    val startButtonLogScreen:String
    val appBarHomeScreen:String
    val appBarQuizScreen:String
    val title: String
    val artist: String
    val album: String
    val year: String
    val tempo: String
    val rhythm: String
    val genre: String
    val correctAnswer:String
    val wrongAnswer:String
    val score:String
    val resetButtonQuizScreen:String
    val saveScoreButtonQuizScreen:String
}
    // - [3] add respective translation here:

        // ### ENGLISH ###

private object EnglishLocalization : Localization {
    override val appName = "• MyDesktopApp"
    override val language= "[EN]"
    override val startButtonLogScreen = "START"
    override val appBarHomeScreen = "Select your activity:"
    override val appBarQuizScreen = "QUIZ"
    override val title = "Title"
    override val artist = "Artist"
    override val album = "Album"
    override val year = "Year"
    override val tempo = "Tempo"
    override val rhythm = "Rythm"
    override val genre = "Genre"
    override val correctAnswer = "CORRECT"
    override val wrongAnswer = "OH NO"
    override val score = "Your score is:"
    override val resetButtonQuizScreen = "RESET"
    override val saveScoreButtonQuizScreen = "SAVE SCORE"
}

        // ### FRENCH ###

private object FrenchLocalization : Localization {
    override val appName = "• MyDesktopApp"
    override val language="[FR]"
    override val startButtonLogScreen = "DÉMARRER"
    override val appBarHomeScreen = "Choisissez votre activité :"
    override val appBarQuizScreen = "QUIZ"
    override val title = "Titre"
    override val artist = "Artiste"
    override val album = "Album"
    override val year = "Année"
    override val tempo = "Tempo"
    override val rhythm = "Rythme"
    override val genre = "Genre"
    override val correctAnswer = "BIEN JOUÉ"
    override val wrongAnswer = "OH NON"
    override val score = "Votre score est de :"
    override val resetButtonQuizScreen = "RECOMMENCER"
    override val saveScoreButtonQuizScreen = "ENREGISTRER SCORE"
}

fun getCurrentLanguage(): AvailableLanguages =
    when (System.getProperties()["user.language"]) {
        "fr" -> AvailableLanguages.FR
        else -> AvailableLanguages.EN
    }

fun getCurrentLocalization() = when (getCurrentLanguage()) {
    AvailableLanguages.EN -> EnglishLocalization
    AvailableLanguages.FR -> FrenchLocalization
}
