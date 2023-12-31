package data.repository

import androidx.compose.ui.res.useResource
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.melanoxylon.lumicomapp.di.CoroutineDispatcherProvider
import data.domain.json.raw.MusicDataSet
import db.Score
import db.ScoreQueries
import di.MyDatabaseWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.theme.getCurrentLocalization
import utils.MUSIC_DATA_SET_JSON


@Single
class DataRepositoryImpl : DataRepository, KoinComponent {

    private val coroutineDispatcherProvider: CoroutineDispatcherProvider by inject()

    override val localization = getCurrentLocalization()
    override val musicDataSetFlow: Flow<MusicDataSet> =
        getMusicDataSetFromJson() //Flow because it's unidirectional information

    private val myDatabase: MyDatabaseWrapper by inject()
    private val scoreQueries: ScoreQueries? = myDatabase.instance?.scoreQueries

    private fun getMusicDataSetFromJson(): Flow<MusicDataSet> = flow {
        try {
            val jsonString = useResource("raw/$MUSIC_DATA_SET_JSON") { it.bufferedReader().readText() }
            val musicDataSet = Json.decodeFromString<MusicDataSet>(jsonString)
            emit(musicDataSet)
        } catch (e: Exception) {
            // Todo: Handle the error
            throw RuntimeException("Error reading the JSON file: ${e.message}")
        }
    }

    //Emits a new result every time the database changes for that query.
    override val scoresFlow: Flow<List<Score>> =
        scoreQueries?.SelectAll()?.asFlow()?.mapToList(coroutineDispatcherProvider.io) ?: flowOf(emptyList())

    override fun saveScore(score: Score) {
        scoreQueries?.InsertScore(
            player_name = score.player_name,
            timestamp = score.timestamp,
            score = score.score
        )
    }


}

