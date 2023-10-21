package ui.screens.quizScreen

import data.domain.json.transformed.QuizQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ui.theme.Localization

data class QuizUIViewState(
    val localization: Localization,
    val appBarTextFlow: StateFlow<String>,
    val formattedTimeCounterFlow: StateFlow<String?>,
    val progressTimeFractionFlow: StateFlow<Float>,
    val quizQuestionFlow: StateFlow<List<QuizQuestion>?>,
    val resultListFlow: StateFlow<List<Boolean?>>,
    val scoreListFlow: Flow<List<String>>,
)