package ui.screens.homeScreen

import androidx.compose.runtime.Composable
import data.domain.Feature
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.theme.DefaultItemPadding

@Composable
fun FeaturesSection(
    modifier: Modifier = Modifier,
    features: List<Feature>,
    onFeatureClicked: (id: Int) -> Unit,
) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(6),//Adaptive(128.dp),
        contentPadding = PaddingValues(DefaultItemPadding),
        content = {
            items(features.size) { index ->
                FeatureItem(
                    feature = features[index],
                    onFeatureClicked = onFeatureClicked)
            }
        }
    )


}