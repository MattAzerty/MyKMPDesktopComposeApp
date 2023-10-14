package ui.theme

    // - [1] add available language here:
enum class AvailableLanguages {
    FR,
    EN;
}
    // - [2] create a new string entry here:
interface Localization {
    val appName: String
    val startButton:String
}
    // - [3] add respective translation here:

        // ### ENGLISH ###

private object EnglishLocalization : Localization {
    override val appName = "My Desktop App"
    override val startButton = "START"
}

        // ### FRENCH ###

private object FrenchLocalization : Localization {
    override val appName = "Mon Application Desktop"
    override val startButton = "DÉMARRER"
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
