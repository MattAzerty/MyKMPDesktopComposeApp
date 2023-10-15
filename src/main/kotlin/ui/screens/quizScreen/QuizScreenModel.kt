package ui.screens.quizScreen

import cafe.adriel.voyager.core.model.ScreenModel
import com.melanoxylon.lumicomapp.di.CoroutineDispatcherProvider
import data.domain.Score
import data.domain.json.raw.MusicDataSet
import data.domain.json.raw.Track
import data.domain.json.transformed.QuizQuestion
import data.repository.DataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import utils.MAXIMUM_QUIZ_TIME_SECONDS
import utils.QuestionType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class QuizScreenModel (
    private val dataRepository: DataRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
): ScreenModel {

    private val coroutineScope = CoroutineScope(coroutineDispatcherProvider.io)
    private lateinit var musicDataSet: MusicDataSet

    private val appBarTextMutableStateFlow: MutableStateFlow<String> = MutableStateFlow(dataRepository.localization.appBarQuizScreen)
    private val formattedTimeCounterMutableStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val quizQuestionMutableStateFlow: MutableStateFlow<List<QuizQuestion>?> = MutableStateFlow(null)
    private val resultListMutableStateFlow: MutableStateFlow<MutableList<Boolean?>> = MutableStateFlow(MutableList(10) { null })

    private var lastTimestamp = System.currentTimeMillis()
    private var currentTimestamp = 0L

    val uiState = QuizUIViewState(
        localization = dataRepository.localization,
        appBarTextFlow = appBarTextMutableStateFlow.asStateFlow(),
        formattedTimeCounterFlow = formattedTimeCounterMutableStateFlow.asStateFlow(),
        quizQuestionFlow =quizQuestionMutableStateFlow.asStateFlow(),
        resultListFlow = resultListMutableStateFlow.asStateFlow()
    )


    private fun generateQuestions(tracks: List<Track>): List<QuizQuestion> {
        val questionTypes = QuestionType.entries.toTypedArray()
        val questions = mutableListOf<QuizQuestion>()

        repeat(10) {
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
                        QuestionType.TITLE -> track.copy(title = "?")
                        QuestionType.ARTIST -> track.copy(artist = "?")
                        QuestionType.ALBUM -> track.copy(album = "?")
                        QuestionType.YEAR -> track.copy(year = -1)
                        QuestionType.TEMPO -> track.copy(tempo = -1)
                        QuestionType.RHYTHM -> track.copy(rhythm = "?")
                        QuestionType.GENRE -> track.copy(genre = "?")
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
            }

            while(true) {

                delay(10L)

                currentTimestamp = System.currentTimeMillis()
                formattedTimeCounterMutableStateFlow.value = formatTime(currentTimestamp - lastTimestamp)

                if(currentTimestamp - lastTimestamp >= MAXIMUM_QUIZ_TIME_SECONDS*1000 && isSetOfQuestionNotFinish()){
                    resultListMutableStateFlow.value[getNextQuestionIndex()] = false
                    lastTimestamp = System.currentTimeMillis()
                }
            }
        }
    }

    private fun getNextQuestionIndex(): Int {
        return resultListMutableStateFlow.value.indexOfFirst { it == null }
    }

    private fun isSetOfQuestionNotFinish():Boolean {
       return resultListMutableStateFlow.value.any { it == null }
    }

    fun cancelJob() {
        coroutineScope.coroutineContext.cancel()
    }

    fun onAnswerClicked(isAnswerCorrect: Boolean) {
        resultListMutableStateFlow.value[getNextQuestionIndex()] = isAnswerCorrect
        lastTimestamp = System.currentTimeMillis()
    }

    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "mm:ss:SSS",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

    fun onResetButtonPressed() {
            quizQuestionMutableStateFlow.value = generateQuestions(musicDataSet.tracks)
            resultListMutableStateFlow.value = MutableList(10) { null }
            lastTimestamp = System.currentTimeMillis()
    }

    fun onSaveScoreButtonClicked(playerName: String) {

        val scoreToSave = Score(
            id = 0,
            player_name = playerName,
            score = resultListMutableStateFlow.value.count { it == true }.toLong(),
            timestamp = System.currentTimeMillis(),
        )

        dataRepository.saveScore(scoreToSave)

    }

}