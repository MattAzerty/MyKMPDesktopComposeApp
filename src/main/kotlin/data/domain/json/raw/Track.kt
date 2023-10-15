package data.domain.json.raw

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val track_id: Int,
    val title: String? = null, //https://stackoverflow.com/questions/64796913/kotlinx-serialization-missingfieldexception
    val artist: String,
    val album: String,
    val year: Int,
    val tempo: Int,
    val rhythm: String,
    val genre: String
)