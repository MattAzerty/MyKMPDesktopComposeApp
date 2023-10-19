package ui.theme

// - [1] add available language here:
enum class AvailableLanguages {
    FR,
    EN;
}

// - [2] create a new string entry here:
interface Localization {
    //APP
    val appName: String
    val language: String
    val notYetImplementedMessage: String
    val scoreAlreadySavedMessageError: String
    val fieldIsEmptyMessageError: String

    //SCREENS
    val startButtonLogScreen: String
    val appBarHomeScreen: String
    val appBarQuizScreen: String
    val appBarQuizScreenHintTrack: String
    val appBarQuizScreenHintResult: String
    val correctAnswerQuizScreen: String
    val wrongAnswerQuizScreen: String
    val scoreSentenceQuizScreen: String
    val resetButtonQuizScreen: String
    val saveScoreButtonQuizScreen: String
    val trigramFieldHintQuizScreen: String
    val optionAnswerFieldHintQuizSection: String

    //GENERICS
    val title: String
    val artist: String
    val album: String
    val year: String
    val tempo: String
    val rhythm: String
    val genre: String
    val player: String


}
// - [3] add respective translation here:

// ### ENGLISH ###

private object EnglishLocalization : Localization {
    //APP
    override val appName = "• MyDesktopApp"
    override val language = "[EN]"
    override val notYetImplementedMessage = "Feature not implemented"
    override val scoreAlreadySavedMessageError = "Score already saved"
    override val fieldIsEmptyMessageError = "Field empty"

    //SCREENS
    override val startButtonLogScreen = "START"
    override val appBarHomeScreen = "Select your activity:"
    override val appBarQuizScreen = "QUIZ"
    override val appBarQuizScreenHintTrack = "Fill the missing field:"
    override val appBarQuizScreenHintResult = "FINISHED! Result:"
    override val correctAnswerQuizScreen = "CORRECT"
    override val wrongAnswerQuizScreen = "OH NO"
    override val scoreSentenceQuizScreen = "Your score is:"
    override val resetButtonQuizScreen = "RESET"
    override val saveScoreButtonQuizScreen = "SAVE SCORE"
    override val trigramFieldHintQuizScreen = "Enter your trigram..."
    override val optionAnswerFieldHintQuizSection = "FIELDS POSSIBLE:"

    //GENERICS
    override val title = "Title"
    override val artist = "Artist"
    override val album = "Album"
    override val year = "Year"
    override val tempo = "Tempo"
    override val rhythm = "Rythm"
    override val genre = "Genre"
    override val player = "Player"
}

// ### FRENCH ###

private object FrenchLocalization : Localization {
    //APP
    override val appName = "• MyDesktopApp"
    override val language = "[FR]"
    override val notYetImplementedMessage = "Fonctionnalité non implémentée"
    override val scoreAlreadySavedMessageError = "Score déjà sauvegardé"
    override val fieldIsEmptyMessageError = "Champ vide"

    //SCREEN
    override val startButtonLogScreen = "DÉMARRER"
    override val appBarHomeScreen = "Choisissez votre activité :"
    override val appBarQuizScreen = "QUIZ"
    override val appBarQuizScreenHintTrack = "Complétez le champ manquant :"
    override val appBarQuizScreenHintResult = "TERMINÉ ! Resultat :"
    override val correctAnswerQuizScreen = "BIEN JOUÉ"
    override val wrongAnswerQuizScreen = "OH NON"
    override val scoreSentenceQuizScreen = "Votre score est de :"
    override val resetButtonQuizScreen = "RECOMMENCER"
    override val saveScoreButtonQuizScreen = "ENREGISTRER SCORE"
    override val trigramFieldHintQuizScreen = "Entrez votre trigramme..."
    override val optionAnswerFieldHintQuizSection = "CHAMPS POSSIBLE :"

    //GENERICS
    override val title = "Titre"
    override val artist = "Artiste"
    override val album = "Album"
    override val year = "Année"
    override val tempo = "Tempo"
    override val rhythm = "Rythme"
    override val genre = "Genre"
    override val player = "Joueur"
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
