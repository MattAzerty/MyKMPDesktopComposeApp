package data.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Feature(
    val id:Int,
    val title: String,
    val imageVector: ImageVector,
    val imageTint: Color,
    val backgroundCardColor:Color,
)