package data.domain.json.raw

import kotlinx.serialization.Serializable

@Serializable
data class Recommendation(
    val track_id: Int,
    val recommendations: List<Int>
)