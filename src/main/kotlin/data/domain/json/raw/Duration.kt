package data.domain.json.raw

import kotlinx.serialization.Serializable

@Serializable
data class Duration(
    val track_id: Int,
    val duration: Int
)