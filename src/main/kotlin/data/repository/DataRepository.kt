package data.repository

import data.domain.Score
import data.domain.json.raw.MusicDataSet
import kotlinx.coroutines.flow.Flow
import ui.theme.Localization

interface DataRepository {
    val localization: Localization
    val musicDataSetFlow: Flow<MusicDataSet>
    val scoresFlow:Flow<List<Score>>
    fun saveScore(score: Score)
}