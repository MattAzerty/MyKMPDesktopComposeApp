package ui.screens.quizScreen

import data.domain.json.transformed.QuizQuestion
import kotlinx.coroutines.flow.StateFlow
import ui.theme.Localization

data class QuizUIViewState(
    val localization: Localization,
    val appBarTextFlow: StateFlow<String>,
    val formattedTimeCounterFlow: StateFlow<String?>,
    val quizQuestionFlow: StateFlow<List<QuizQuestion>?>,
    val resultListFlow: StateFlow<List<Boolean?>>,
)