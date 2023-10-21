package ui.screens.quizScreen

import cafe.adriel.voyager.core.model.ScreenModel
import com.melanoxylon.lumicomapp.di.CoroutineDispatcherProvider
import data.domain.json.raw.MusicDataSet
import data.domain.json.raw.Track
import data.domain.json.transformed.QuizQuestion
import data.repository.DataRepository
import db.Score
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.*

class QuizScreenModel : ScreenModel, KoinComponent {

    private val dataRepository: DataRepository by inject()
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider by inject()

    private val coroutineScope = CoroutineScope(coroutineDispatcherProvider.io)
    private lateinit var musicDataSet: MusicDataSet

    private val appBarTextMutableStateFlow: MutableStateFlow<String> =
        MutableStateFlow(dataRepository.localization.appBarQuizScreen)
    private val formattedTimeCounterMutableStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val progressTimeFractionMutableStateFlow: MutableStateFlow<Float> = MutableStateFlow(1f)
    private val quizQuestionMutableStateFlow: MutableStateFlow<List<QuizQuestion>?> = MutableStateFlow(null)
    private val resultListMutableStateFlow: MutableStateFlow<MutableList<Boolean?>> =
        MutableStateFlow(MutableList(NUMBER_OF_QUESTIONS_BY_QUIZ) { null })

    private val quizScreenEventMutableSharedFlow = MutableSharedFlow<QuizScreenEvent>()
    val quizScreenEventSharedFlow = quizScreenEventMutableSharedFlow.asSharedFlow()

    val uiState = QuizUIViewState(
        localization = dataRepository.localization,
        appBarTextFlow = appBarTextMutableStateFlow.asStateFlow(),
        formattedTimeCounterFlow = formattedTimeCounterMutableStateFlow.asStateFlow(),
        progressTimeFractionFlow = progressTimeFractionMutableStateFlow.asStateFlow(),
        quizQuestionFlow = quizQuestionMutableStateFlow.asStateFlow(),
        resultListFlow = resultListMutableStateFlow.asStateFlow(),
        scoreListFlow = dataRepository.scoresFlow.map { listOfScore ->
            listOfScore.sortedByDescending { it.score }
                .map {
                    "• ${dataRepository.localization.player}: ${it.player_name} - Score:${it.score} | ${
                        timestampToDateString(
                            it.timestamp
                        )
                    }"
                }
        }
    )

    private var lastTimestamp = System.currentTimeMillis()
    private var currentTimestamp = System.currentTimeMillis()
    private var isScoreSaved = false


    private fun generateQuestions(tracks: List<Track>): List<QuizQuestion> {

        val questionTypes = QuestionType.entries.toTypedArray()
        val questions = mutableListOf<QuizQuestion>()


        repeat(NUMBER_OF_QUESTIONS_BY_QUIZ) {
            val questionType = questionTypes.random()
            val filteredTracks = when (questionType) {
                QuestionType.TITLE -> tracks.filter { it.title != null }
                else -> tracks
            }

            val track = filteredTracks.random()

            val correctAnswer = when (questionType) {
                QuestionType.TITLE -> track.title!!
                QuestionType.ARTIST -> track.artist
                QuestionType.ALBUM -> track.album
                QuestionType.YEAR -> track.year.toString()
                QuestionType.TEMPO -> track.tempo.toString()
                QuestionType.RHYTHM -> track.rhythm
                QuestionType.GENRE -> track.genre
            }

            val options = mutableListOf(correctAnswer)

            while (options.size < 4) {
                val randomOption = filteredTracks.random().let {
                    when (questionType) {
                        QuestionType.TITLE -> it.title!!
                        QuestionType.ARTIST -> it.artist
                        QuestionType.ALBUM -> it.album
                        QuestionType.YEAR -> it.year.toString()
                        QuestionType.TEMPO -> it.tempo.toString()
                        QuestionType.RHYTHM -> it.rhythm
                        QuestionType.GENRE -> it.genre
                    }
                }
                if (randomOption != correctAnswer && !options.contains(randomOption)) {
                    options.add(randomOption)
                }
            }

            questions.add(
                QuizQuestion(
                    when (questionType) {
                        QuestionType.TITLE -> track.copy(title = FIELD_PLACEHOLDER)
                        QuestionType.ARTIST -> track.copy(artist = FIELD_PLACEHOLDER)
                        QuestionType.ALBUM -> track.copy(album = FIELD_PLACEHOLDER)
                        QuestionType.YEAR -> track.copy(year = -1)
                        QuestionType.TEMPO -> track.copy(tempo = -1)
                        QuestionType.RHYTHM -> track.copy(rhythm = FIELD_PLACEHOLDER)
                        QuestionType.GENRE -> track.copy(genre = FIELD_PLACEHOLDER)
                    },
                    questionType,
                    correctAnswer,
                    options.shuffled()
                )
            )
        }

        return questions
    }

    fun startJob() {

        coroutineScope.launch {

            dataRepository.musicDataSetFlow.collect { value ->
                musicDataSet = value
                quizQuestionMutableStateFlow.value = generateQuestions(value.tracks)
                appBarTextMutableStateFlow.value = dataRepository.localization.appBarQuizScreenHintTrack
            }

            while (true) {

                delay(10L)

                currentTimestamp = System.currentTimeMillis()
                val elapsedTimeMs = currentTimestamp - lastTimestamp
                formattedTimeCounterMutableStateFlow.value = formatTime(elapsedTimeMs)
                progressTimeFractionMutableStateFlow.value = ((elapsedTimeMs/1000f) / MAXIMUM_QUIZ_TIME_SECONDS)

                if (currentTimestamp - lastTimestamp >= MAXIMUM_QUIZ_TIME_SECONDS * 1000 && isSetOfQuestionNotFinish()) {
                    resultListMutableStateFlow.value[getNextQuestionIndex()] = false
                    lastTimestamp = System.currentTimeMillis()
                }
            }
        }
    }

    private fun getNextQuestionIndex(): Int {
        return resultListMutableStateFlow.value.indexOfFirst { it == null }
    }

    private fun isSetOfQuestionNotFinish(): Boolean {
        return resultListMutableStateFlow.value.any { it == null }
    }

    fun cancelJob() {
        coroutineScope.coroutineContext.cancel()
    }

    fun onAnswerClicked(isAnswerCorrect: Boolean) {
        resultListMutableStateFlow.value[getNextQuestionIndex()] = isAnswerCorrect
        lastTimestamp = System.currentTimeMillis()
        if (!isSetOfQuestionNotFinish()) appBarTextMutableStateFlow.value =
            dataRepository.localization.appBarQuizScreenHintResult
    }


    private fun formatTime(elapsedTimeMs: Long): String {
        val deltaMs = MAXIMUM_QUIZ_TIME_SECONDS*1000 - elapsedTimeMs
        val minutes = (deltaMs / 60000) % 60
        val seconds = (deltaMs / 1000) % 60
        val milliseconds = deltaMs % 1000

        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
    }

    fun onResetButtonPressed() {
        quizQuestionMutableStateFlow.value = generateQuestions(musicDataSet.tracks)
        resultListMutableStateFlow.value = MutableList(NUMBER_OF_QUESTIONS_BY_QUIZ) { null }
        appBarTextMutableStateFlow.value = dataRepository.localization.appBarQuizScreenHintTrack
        isScoreSaved = false
        lastTimestamp = System.currentTimeMillis()
    }

    fun onSaveScoreButtonClicked(playerName: String) {

        if (!isScoreSaved) {

            val scoreToSave = Score(
                id = 0,
                player_name = playerName,
                score = resultListMutableStateFlow.value.count { it == true }.toLong(),
                timestamp = System.currentTimeMillis(),
            )

            dataRepository.saveScore(scoreToSave)
            isScoreSaved = true

        } else {

            val errorMessage = when {
                playerName.isBlank() -> dataRepository.localization.fieldIsEmptyMessageError
                isScoreSaved -> dataRepository.localization.scoreAlreadySavedMessageError
                else -> "Error"
            }

            coroutineScope.launch {
                quizScreenEventMutableSharedFlow.emit(QuizScreenEvent.ShowDialogWindowMessage(errorMessage))
            }

        }


    }

}