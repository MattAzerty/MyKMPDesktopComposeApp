package data.domain.json.raw

import kotlinx.serialization.Serializable

@Serializable
data class MusicDataSet(
    val tracks: List<Track>,
    val durations: List<Duration>,
    val recommendations: List<Recommendation>
)