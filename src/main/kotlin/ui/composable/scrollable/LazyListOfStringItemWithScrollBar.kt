package ui.composable.scrollable

import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ui.theme.DesktopBlueColor
import ui.theme.DesktopLightGreyColor
import ui.theme.DesktopRedColor
import ui.theme.DesktopYellowColor

@Composable
fun LazyListOfStringItemWithScrollBar(
    backgroundItemColor: Color = DesktopLightGreyColor,
    listOfItem:List<String> = List(10) { index -> "Item $index" },
) {

    Box(
        modifier = Modifier.wrapContentSize()
            .padding(10.dp)
    ) {

        val state = rememberLazyListState()

        LazyColumn(
            Modifier.padding(end = 12.dp),
            state
        ) {

            items(listOfItem.size) { index ->
                TextBox(
                    backgroundItemColor = backgroundItemColor,
                    text = listOfItem[index],
                )
                Spacer(modifier = Modifier.height(5.dp))
            }

        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            style = ScrollbarStyle(
                unhoverColor = DesktopYellowColor,
                shape = CircleShape,
                hoverColor = DesktopRedColor,
                thickness = 8.dp,
                minimalHeight = 8.dp,
                hoverDurationMillis = 20
            ),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )

    }
}

@Composable
fun TextBox(
    backgroundItemColor: Color,
    text: String,
) {

    Box(
        modifier = Modifier.height(32.dp)
            .wrapContentWidth()
            .background(color = backgroundItemColor)
            .padding(start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}