package ui.screens.quizScreen

sealed class QuizScreenEvent {
    //data object StartQuiz: QuizScreenEvent()
    data class ShowDialogWindowMessage(val message: String) : QuizScreenEvent()
}